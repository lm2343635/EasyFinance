package edu.kit.ActMgr.service.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DaoManager extends ManagerTemplate
{
	public static DaoManager get(ServletContext servletContext)
	{
		ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return (DaoManager)ctx.getBean("daoManager");
	}
}
