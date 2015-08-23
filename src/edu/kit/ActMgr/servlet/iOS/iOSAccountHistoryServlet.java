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
import edu.kit.ActMgr.data.iOS.iOSAccountData;
import edu.kit.ActMgr.data.iOS.iOSAccountHistoryData;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountHistory;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.common.hibernate3.support.SystemInit;

@WebServlet("/iOSAccountHistoryServlet")
public class iOSAccountHistoryServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private DaoManager dao;
	private String task;
	
    public iOSAccountHistoryServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		dao=DaoManager.get(getServletContext());
		task=request.getParameter("task");
		switch (task)
		{
		case "getAccountHistories":
			getAccountHistories(request,response);
			break;
		case "push"://暂时作废的方法
			push(request,response);
			break;
		case "upload"://暂时作废的方法
			upload(request,response);
			break;
		default:
			break;
		}
	}

	private void getAccountHistories(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int aid=Integer.parseInt(request.getParameter("aid"));
		Account account=dao.getAccountDao().get(aid);
		List<iOSAccountHistoryData> histories=new ArrayList<iOSAccountHistoryData>();
		for(AccountHistory history:dao.getAccountHistoryDao().findByAccount(account))
		{
			history.setSync(SystemInit.SYNCED);
			dao.getAccountHistoryDao().update(history);
			histories.add(new iOSAccountHistoryData(history));
		}
		JSONArray jsonArray=JSONArray.fromObject(histories);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}

	@SuppressWarnings("unchecked")
	private void push(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String arrayString=request.getParameter("array");
		if(iOSSynchronizeServlet.DEBUG==true)
			System.out.println("Get account history message from client: "+arrayString);
		JSONArray jsonArray=JSONArray.fromObject(arrayString);
		List<iOSAccountHistoryData> datas=JSONArray.toList(jsonArray,new iOSAccountData(),new JsonConfig());
		for(iOSAccountHistoryData data: datas)
		{
			AccountHistory accountHistory=dao.getAccountHistoryDao().get(data.getAhid());
			//如果服务中不存在iOS客户端中传过来的实体，就要在服务器中新建该实体
			if(accountHistory==null)
			{
				accountHistory=new AccountHistory();
				accountHistory.setAin(data.getAin());
				accountHistory.setAout(data.getAout());
				accountHistory.setDate(new Date(data.getTimeInterval()));
				accountHistory.setAccount(dao.getAccountDao().get(data.getAid()));
				//设置同步属性为已同步
				accountHistory.setSync(SystemInit.SYNCED);
				int ahid=dao.getAccountHistoryDao().save(accountHistory);
				data.setSync(SystemInit.SYNCED);
				data.setAhid(ahid);
			}
			else
			{
				
			}
			jsonArray=JSONArray.fromObject(datas);
			response.setContentType("text/json");
			response.getWriter().print(jsonArray.toString());
		}
	}

	private void upload(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int uid=Integer.parseInt(request.getParameter("uid"));
		User user=dao.getUserDao().get(uid);
		List<iOSAccountHistoryData> accountHistoryDatas=new ArrayList<iOSAccountHistoryData>();
		for(AccountHistory accountHistory: dao.getAccountHistoryDao().findNotSyncByUser(user))
		{
			accountHistory.setSync(SystemInit.SYNCED);
			dao.getAccountHistoryDao().update(accountHistory);
			accountHistoryDatas.add(new iOSAccountHistoryData(accountHistory));
		}
		JSONArray jsonArray=JSONArray.fromObject(accountHistoryDatas);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}

}
