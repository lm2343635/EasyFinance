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
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import edu.kit.ActMgr.bean.AccountBookBean;
import edu.kit.ActMgr.data.iOS.iOSAccountBookData;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Icon;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.common.hibernate3.support.SystemInit;

/**
 * Servlet implementation class iOSAccountBookServlet
 */
@WebServlet("/iOSAccountBookServlet")
public class iOSAccountBookServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private DaoManager dao;
	private String task;
       
    public iOSAccountBookServlet() 
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
		case "add":
			add(request,response);
			break;
		case "getUserAccountBooks":
			getUserAccountBooks(request,response);
			break;
		case "getUsingAccountBook":
			getUsingAccountBook(request,response);
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

	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int iid=Integer.parseInt(request.getParameter("iid"));
		Icon abicon=dao.getIconDao().get(iid);
		int uid=Integer.parseInt(request.getParameter("uid"));
		User user=dao.getUserDao().get(uid);
		//新建账本
		AccountBook accountBook=new AccountBook();
		accountBook.setAbname(request.getParameter("abname"));
		accountBook.setAbicon(abicon);
		accountBook.setUser(user);
		int abid=dao.getAccountBookDao().save(accountBook);
		//设置当前用户的默认账本为该账本
		user.setUsingAccountBook(accountBook);
		dao.getUserDao().update(user);
		AccountBookBean accountBookBean=new AccountBookBean(accountBook);
		if(abid>0)
		{
			JSONObject jsonObject=JSONObject.fromObject(accountBookBean);
			response.setContentType("text/json");
			response.getWriter().print(jsonObject.toString());
		}
	}

	private void getUserAccountBooks(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int uid=Integer.parseInt(request.getParameter("uid"));
		User user=dao.getUserDao().get(uid);
		List<AccountBookBean> accountBooks=new ArrayList<AccountBookBean>();
		for(AccountBook accountBook:dao.getAccountBookDao().findByUser(user))
		{
			//iOS客户端导入数据时要将服务器端实体的sync属性设为1，表示已同步
			accountBook.setSync(SystemInit.SYNCED);
			dao.getAccountBookDao().update(accountBook);
			accountBooks.add(new AccountBookBean(accountBook));
		}

		JSONArray jsonArray=JSONArray.fromObject(accountBooks);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}

	private void getUsingAccountBook(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int uid=Integer.parseInt(request.getParameter("uid"));
		User user=dao.getUserDao().get(uid);
		AccountBookBean accountBook=new AccountBookBean(user.getUsingAccountBook());
		JSONObject jsonObject=JSONObject.fromObject(accountBook);
		response.setContentType("text/json");
		response.getWriter().print(jsonObject.toString());
	}

	@SuppressWarnings("unchecked")
	private void push(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String arrayString=request.getParameter("array");
		if(iOSSynchronizeServlet.DEBUG==true)
			System.out.println("Get account book message from client: "+arrayString);
		JSONArray jsonArray=JSONArray.fromObject(arrayString);
		List<iOSAccountBookData>  datas=JSONArray.toList(jsonArray, new iOSAccountBookData(), new JsonConfig());
		for(iOSAccountBookData data:datas) 
		{
			AccountBook accountBook=dao.getAccountBookDao().get(data.getAbid());
			//如果服务中不存在iOS客户端中传过来的实体，就要在服务器中新建该实体
			if(accountBook==null)
			{
				accountBook=new AccountBook();
				accountBook.setAbname(data.getAbname());
				accountBook.setAbicon(dao.getIconDao().get(data.getIid()));
				accountBook.setUser(dao.getUserDao().get(data.getUid()));
				//设置同步属性为已同步
				accountBook.setSync(SystemInit.SYNCED);
				int abid=dao.getAccountBookDao().save(accountBook);
				//保存完成后将abid返回给客户端
				data.setSync(SystemInit.SYNCED);
				data.setAbid(abid);
			}
			//如果服务器中存在，客户端传过来的实体，就要在服务器中修改该实体
			else
			{
				
			}
			
		}
		jsonArray=JSONArray.fromObject(datas);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());	
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int uid=Integer.parseInt(request.getParameter("uid"));
		User user=dao.getUserDao().get(uid);
		List<iOSAccountBookData> accountBookDatas=new ArrayList<iOSAccountBookData>();
		for(AccountBook accountBook:dao.getAccountBookDao().findNotSyncByUser(user))
		{
			//设置这些账本为已同步状态
			accountBook.setSync(SystemInit.SYNCED);
			dao.getAccountBookDao().update(accountBook);
			accountBookDatas.add(new iOSAccountBookData(accountBook));
		}
		JSONArray jsonArray=JSONArray.fromObject(accountBookDatas);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}
}
