package edu.kit.ActMgr.servlet.iOS;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import edu.kit.ActMgr.bean.UserBean;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.ActMgr.service.util.Service;

/**
 * Servlet implementation class iOSUserServlet
 */
@WebServlet("/iOSUserServlet")
public class iOSUserServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;	
	private String task;
	private DaoManager dao;
	private Service service;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public iOSUserServlet() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		switch (task)
		{
		case "getUser":
			getUser(request,response);
			break;
		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		dao=DaoManager.get(getServletContext());
		service=Service.get(getServletContext());
		switch (task)
		{
		case "login":
			login(request,response);
			break;
		case "getUser":
			getUser(request,response);
			break;
		case "register":
			register(request,response);
			break;
		case "isEmailExsit":
			isEmailExsit(request,response);
			break;
		default:
			break;
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		int status=service.getUserManager().iOSLogin(email, password);
		response.getWriter().print(status); 
	}
	

	private void getUser(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		String email=request.getParameter("email");
		User user=dao.getUserDao().findByEmail(email);
		UserBean userBean=new UserBean(user);
		JSONObject jsonObject=JSONObject.fromObject(userBean);
		response.setContentType("text/json");
		response.getWriter().print(jsonObject.toString());
	}

	private void register(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		String uname=request.getParameter("uname");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		int uid=service.getUserManager().register(uname, email, password);
		UserBean user=new UserBean(dao.getUserDao().get(uid));
		JSONObject jsonObject=JSONObject.fromObject(user);
		response.setContentType("text/json");
		response.getWriter().print(jsonObject.toString());
	}
	
	private void isEmailExsit(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		String email=request.getParameter("email");
		boolean exsit=service.getUserManager().isEmailExist(email);
		response.getWriter().print(exsit);
	}
}
