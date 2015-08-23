package edu.kit.ActMgr.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.UserBean;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    public UserServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		switch (task) 
		{
		case "exit":
			exit(request,response);
			break;
		case "get":
			get(request,response);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		task=request.getParameter("task");
		switch (task)
		{


		default:
			break;
		}
	}

	/**
	 * 处理用户退出
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void exit(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		removeUserSession(request.getSession());
		response.sendRedirect("index.html");
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void get(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		UserBean user=getUser(request.getSession());
		String print=user.getUid()+",";
		print+=user.getUname()+",";
		print+=user.getEmail()+",";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(print);
	}

	/**
	 * 增加用户Session
	 * @param session
	 * @param user
	 */
	public static void addUserSession(HttpSession session,UserBean user)
	{
		session.removeAttribute("user");
		session.setAttribute("user", user);
	}
	
	/**
	 * 删除用户Session
	 * @param session
	 */
	public static void removeUserSession(HttpSession session)
	{
		session.removeAttribute("user");
	}
	
	/**
	 * 替换用户Session
	 * @param session
	 * @param user
	 */
	public static void replaceUserSession(HttpSession session,UserBean user)
	{
		removeUserSession(session);
		addUserSession(session, user);
	}
	
	/**
	 * 得到用户Session
	 * @param session
	 * @return
	 */
	public static UserBean getUser(HttpSession session)
	{
		UserBean user=null;
		if(session.getAttribute("user")!=null)
			user=(UserBean)session.getAttribute("user");
		return user;
	}
}
