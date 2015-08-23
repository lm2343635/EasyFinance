package edu.kit.ActMgr.servlet.iOS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import edu.kit.ActMgr.data.iOS.iOSRecordData;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Classification;
import edu.kit.ActMgr.domain.Photo;
import edu.kit.ActMgr.domain.Record;
import edu.kit.ActMgr.domain.Shop;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.ActMgr.service.util.Service;
import edu.kit.common.hibernate3.support.SystemInit;
import edu.kit.common.util.DateTool;

@WebServlet("/iOSRecordServlet")
public class iOSRecordServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
	private DaoManager dao;
	private Service manager;

    public iOSRecordServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		task=request.getParameter("task");
		dao=DaoManager.get(getServletContext());
		manager=Service.get(getServletContext());
		switch (task) {
		case "getRecords":
			getRecords(request,response);
			break;
		case "push":
			push(request,response);
			break;
		case "update":
			update(request,response);
			break;
		default:
			break;
		}
	}

	private void getRecords(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int abid=Integer.parseInt(request.getParameter("abid"));
		AccountBook accountBook=dao.getAccountBookDao().get(abid);
		List<iOSRecordData> records=new ArrayList<iOSRecordData>();
		for(Record record:dao.getRecordDao().findByAccountBook(accountBook))
		{
			record.setSync(SystemInit.SYNCED);
			dao.getRecordDao().update(record);
			records.add(new iOSRecordData(record));
		}
		JSONArray jsonArray=JSONArray.fromObject(records);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}

	@SuppressWarnings("unchecked")
	private void push(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String arrayString=request.getParameter("array");
		if(iOSSynchronizeServlet.DEBUG==true)
			System.out.println("Get record message from client: "+arrayString);
		JSONArray jsonArray=JSONArray.fromObject(arrayString);
		List<iOSRecordData> datas=JSONArray.toList(jsonArray,new iOSRecordData(),new JsonConfig());
		for(iOSRecordData data: datas)
		{
			Record record=dao.getRecordDao().get(data.getRid());
			//如果服务中不存在iOS客户端中传过来的实体，就要在服务器中新建该实体
			if(record==null)
			{
				double money=data.getMoney();
				Classification classification=dao.getClassificationDao().get(data.getCid());
				Account account=dao.getAccountDao().get(data.getAid());
				Shop shop=dao.getShopDao().get(data.getSid());
				Date time=new Date(data.getTimeInterval());
				
				record=new Record();
				record.setMoney(money);
				record.setRemark(data.getRemark());
				record.setTime(time);
				record.setClassification(classification);
				record.setAccount(account);
				record.setShop(shop);
				Photo photo=dao.getPhotoDao().get(data.getPid());
				if(photo==null)
				{
					photo=dao.getPhotoDao().get(SystemInit.SYS_RECORD_PHOTO_NULL);
					record.setPhoto(photo);
					record.setAccountBook(dao.getAccountBookDao().get(data.getAbid()));
				}
				else
				{
					record.setPhoto(photo);
					record.setAccountBook(record.getPhoto().getAccountBook());
				}
				//设置同步属性为已同步
				record.setSync(SystemInit.SYNCED);
				int rid=dao.getRecordDao().save(record);
				data.setSync(SystemInit.SYNCED);
				data.setRid(rid);
				
				//更新分类、账户和商家的流入和流出
				if(money>0)
				{
					classification.setCin(classification.getCin()+money);
					account.setAin(account.getAin()+money);
					shop.setSin(shop.getSin()+money);
				}
				else
				{
					classification.setCout(classification.getCout()-money);
					account.setAout(account.getAout()-money);
					shop.setSout(shop.getSout()-money);
				}
				dao.getClassificationDao().update(classification);
				dao.getAccountDao().update(account);
				dao.getShopDao().update(shop);
				//更新对账历史记录
				String timeStr=DateTool.formatDate(time, DateTool.YEAR_MONTH_DATE_FORMAT);
				Date date=DateTool.transferDate(timeStr,DateTool.YEAR_MONTH_DATE_FORMAT);
				manager.getAccountHistoryManager().update(account, date, money);
			}
			else
			{
				
			}
			jsonArray=JSONArray.fromObject(datas);
			response.setContentType("json/text");
			response.getWriter().print(jsonArray.toString());
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int uid=Integer.parseInt(request.getParameter("uid"));
		User user=dao.getUserDao().get(uid);
		List<iOSRecordData> recordDatas=new ArrayList<iOSRecordData>();
		for(Record record: dao.getRecordDao().findNotSyncByUser(user))
		{
			record.setSync(SystemInit.SYNCED);
			dao.getRecordDao().update(record);
			recordDatas.add(new iOSRecordData(record));
		}
		JSONArray jsonArray=JSONArray.fromObject(recordDatas);
		response.setContentType("json/text");
		response.getWriter().print(jsonArray.toString());
	}
}
