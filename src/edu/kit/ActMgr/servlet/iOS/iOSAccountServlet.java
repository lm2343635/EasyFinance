package edu.kit.ActMgr.servlet.iOS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import edu.kit.ActMgr.bean.AccountBean;
import edu.kit.ActMgr.data.iOS.iOSAccountData;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.common.hibernate3.support.SystemInit;

/**
 * Servlet implementation class iOSAccountServlet
 */
@WebServlet("/iOSAccountServlet")
public class iOSAccountServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private DaoManager dao;
	private String task;
       
    public iOSAccountServlet() 
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//在doGet中调用doPost，提供测试使用
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		task=request.getParameter("task");
		dao=DaoManager.get(getServletContext());
		switch (task)
		{
		case "getAccounts":
			getAccounts(request,response);
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

	private void getAccounts(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int abid=Integer.parseInt(request.getParameter("abid"));
		AccountBook accountBook=dao.getAccountBookDao().get(abid);
		List<AccountBean> accounts=new ArrayList<AccountBean>();
		for(Account account:dao.getAccountDao().findByAccountBook(accountBook))
		{
			account.setSync(SystemInit.SYNCED);
			dao.getAccountDao().update(account);
			accounts.add(new AccountBean(account));
		}
		JSONArray jsonArray=JSONArray.fromObject(accounts);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());		
	}
	
	@SuppressWarnings("unchecked")
	private void push(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String arrayString=request.getParameter("array");
		if(iOSSynchronizeServlet.DEBUG==true)
			System.out.println("Get account message from client: "+arrayString);
		JSONArray jsonArray=JSONArray.fromObject(arrayString);
		List<iOSAccountData> datas=JSONArray.toList(jsonArray,new iOSAccountData(),new JsonConfig());
		for(iOSAccountData data: datas)
		{
			Account account=dao.getAccountDao().get(data.getAid());
			//如果服务中不存在iOS客户端中传过来的实体，就要在服务器中新建该实体
			if(account==null)
			{
				account=new Account();
				account.setAname(data.getAname());
				account.setAin(data.getAin());
				account.setAout(data.getAout());
				account.setAicon(dao.getIconDao().get(data.getIid()));
				account.setAccountBook(dao.getAccountBookDao().get(data.getAbid()));
				//设置同步属性为已同步
				account.setSync(SystemInit.SYNCED);
				int aid=dao.getAccountDao().save(account);
				data.setSync(SystemInit.SYNCED);
				data.setAid(aid);
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
		List<iOSAccountData> accountDatas=new ArrayList<iOSAccountData>();
		for(Account account: dao.getAccountDao().finNotSyncByUser(user))
		{
			account.setSync(SystemInit.SYNCED);
			dao.getAccountDao().update(account);
			accountDatas.add(new iOSAccountData(account));
		}
		JSONArray jsonArray=JSONArray.fromObject(accountDatas);
		response.setContentType("json/text");
		response.getWriter().print(jsonArray.toString());
	}
}
