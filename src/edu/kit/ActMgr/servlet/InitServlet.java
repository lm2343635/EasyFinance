package edu.kit.ActMgr.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.common.hibernate3.support.SystemInit;

/**
 * 初始化Servlet，初始化数据库
 */
@WebServlet("/InitServlet")
public class InitServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public InitServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ManagerTemplate manager=getServiceManager(getServletConfig());
		String rootPath=getServletConfig().getServletContext().getRealPath("/");
		SystemInit init=new SystemInit(rootPath,manager);
		init.createSystemNullItems();
		init.readSystemIcons();
		response.getWriter().print("SystemInit Success !");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	}

	/**
	 * 得到服务管理器
	 * @param config ServletConfig
	 * @return 服务管理器
	 */
	public static ManagerTemplate getServiceManager(ServletConfig config)
	{
		WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		ManagerTemplate manager=(ManagerTemplate)ctx.getBean("managerTemplate");
		return manager;
	}
	
}
