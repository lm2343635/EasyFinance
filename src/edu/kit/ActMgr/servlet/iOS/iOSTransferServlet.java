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
import edu.kit.ActMgr.data.iOS.iOSTransferData;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Transfer;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.ActMgr.service.util.Service;
import edu.kit.common.hibernate3.support.SystemInit;
import edu.kit.common.util.DateTool;

@WebServlet("/iOSTransferServlet")
public class iOSTransferServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
	private DaoManager dao;
	private Service manager;
       
    public iOSTransferServlet() 
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
		case "getTransfers":
			getTransfers(request,response);
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

	private void getTransfers(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int abid=Integer.parseInt(request.getParameter("abid"));
		AccountBook accountBook=dao.getAccountBookDao().get(abid);
		List<iOSTransferData> transfers=new ArrayList<iOSTransferData>();
		for(Transfer transfer:dao.getTransferDao().findByAccountBook(accountBook))
		{
			transfer.setSync(SystemInit.SYNCED);
			dao.getTransferDao().update(transfer);
			transfers.add(new iOSTransferData(transfer));
		}
		JSONArray jsonArray=JSONArray.fromObject(transfers);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}
	

	@SuppressWarnings("unchecked")
	private void push(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String arrayString=request.getParameter("array");
		if(iOSSynchronizeServlet.DEBUG==true)
			System.out.println("Get transfer message from client: "+arrayString);
		JSONArray jsonArray=JSONArray.fromObject(arrayString);
		List<iOSTransferData> datas=JSONArray.toList(jsonArray,new iOSTransferData(),new JsonConfig());
		for(iOSTransferData data: datas)
		{
			Transfer transfer=dao.getTransferDao().get(data.getTfid());
			//如果服务中不存在iOS客户端中传过来的实体，就要在服务器中新建该实体
			if(transfer==null)
			{
				double money=data.getMoney();
				Account tfin=dao.getAccountDao().get(data.getTfinid());
				Account tfout=dao.getAccountDao().get(data.getTfoutid());
				Date time=new Date(data.getTimeInterval());
				
				transfer=new Transfer();
				transfer.setTfin(tfin);
				transfer.setTfout(tfout);
				transfer.setMoney(money);
				transfer.setRemark(data.getRemark());
				transfer.setTime(time);
				transfer.setAccountBook(dao.getAccountBookDao().get(data.getAbid()));
				//设置同步属性为已同步
				transfer.setSync(SystemInit.SYNCED);
				int tfid=dao.getTransferDao().save(transfer);
				data.setSync(SystemInit.SYNCED);
				data.setTfid(tfid);
				
				//更新转入转出账户的流入和流出
				tfin.setAin(tfin.getAin()+money);
				tfout.setAout(tfout.getAout()+money);
				dao.getAccountDao().update(tfin);
				dao.getAccountDao().update(tfout);
				//更新对账历史记录
				String timeStr=DateTool.formatDate(time, DateTool.YEAR_MONTH_DATE_FORMAT);
				Date date=DateTool.transferDate(timeStr,DateTool.YEAR_MONTH_DATE_FORMAT);
				manager.getAccountHistoryManager().update(tfin, date, money);
				manager.getAccountHistoryManager().update(tfout, date, -money);
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
		List<iOSTransferData> transferDatas=new ArrayList<iOSTransferData>();
		for(Transfer transfer: dao.getTransferDao().findNotSyncByUser(user))
		{
			transfer.setSync(SystemInit.SYNCED);
			dao.getTransferDao().update(transfer);
			transferDatas.add(new iOSTransferData(transfer));
		}
		JSONArray jsonArray=JSONArray.fromObject(transferDatas);
		response.setContentType("json/text");
		response.getWriter().print(jsonArray.toString());
	}

}
