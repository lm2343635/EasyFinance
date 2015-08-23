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
import edu.kit.ActMgr.bean.IconBean;
import edu.kit.ActMgr.domain.Icon;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.common.hibernate3.support.SystemInit;

/**
 * Servlet implementation class iOSIconServlet
 */
@WebServlet("/iOSIconServlet")
public class iOSIconServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
	private DaoManager daoManager;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public iOSIconServlet() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		daoManager=DaoManager.get(getServletContext());
		task=request.getParameter("task");
		switch (task)
		{
		case "loadSystemIcons":
			loadSystemIcons(request,response);
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
		// TODO Auto-generated method stub
	}
	

	private void loadSystemIcons(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		List<IconBean> icons=new ArrayList<IconBean>();
		User system=daoManager.getUserDao().get(SystemInit.SYS_NULL_ID);
		for(Icon icon:daoManager.getIconDao().findByUser(system))
			icons.add(new IconBean(icon));
		//跳过第一个图标，因为第一个图标是系统空图标，此处将其移除。
		icons.remove(0);
		JSONArray jsonArray=JSONArray.fromObject(icons);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}

}
