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
import edu.kit.ActMgr.bean.TemplateBean;
import edu.kit.ActMgr.data.iOS.iOSTemplateData;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Template;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.common.hibernate3.support.SystemInit;

@WebServlet("/iOSTemplateServlet")
public class iOSTemplateServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private DaoManager dao;
	private String task;
	
    public iOSTemplateServlet() 
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
		case "getTemplates":
			getTemplates(request,response);
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

	private void getTemplates(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int abid=Integer.parseInt(request.getParameter("abid"));
		AccountBook accountBook=dao.getAccountBookDao().get(abid);
		List<TemplateBean> templates=new ArrayList<TemplateBean>();
		for(Template template:dao.getTemplateDao().findByAccountBook(accountBook))
		{
			template.setSync(SystemInit.SYNCED);
			dao.getTemplateDao().update(template);
			templates.add(new TemplateBean(template));
		}
		JSONArray jsonArray=JSONArray.fromObject(templates);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());			
	}


	@SuppressWarnings("unchecked")
	private void push(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String arrayString=request.getParameter("array");
		if(iOSSynchronizeServlet.DEBUG==true)
			System.out.println("Get template message from client: "+arrayString);
		JSONArray jsonArray=JSONArray.fromObject(arrayString);
		List<iOSTemplateData> datas=JSONArray.toList(jsonArray, new iOSTemplateData(), new JsonConfig());
		for(iOSTemplateData data: datas)
		{
			Template template=dao.getTemplateDao().get(data.getTpid());
			//如果服务中不存在iOS客户端中传过来的实体，就要在服务器中新建该实体
			if(template==null)
			{
				template=new Template();
				template.setTpname(data.getTpname());
				template.setClassification(dao.getClassificationDao().get(data.getCid()));
				template.setAccount(dao.getAccountDao().get(data.getAid()));
				template.setShop(dao.getShopDao().get(data.getSid()));
				template.setAccountBook(dao.getAccountBookDao().get(data.getAbid()));
				//设置同步属性为已同步
				template.setSync(SystemInit.SYNCED);
				int tpid=dao.getTemplateDao().save(template);
				data.setSync(SystemInit.SYNCED);
				data.setTpid(tpid);
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
		List<iOSTemplateData> templateDatas=new ArrayList<iOSTemplateData>();
		for(Template template: dao.getTemplateDao().findNotSyncByUser(user))
		{
			template.setSync(SystemInit.SYNCED);
			dao.getTemplateDao().update(template);
			templateDatas.add(new iOSTemplateData(template));
		}
		JSONArray jsonArray=JSONArray.fromObject(templateDatas);
		response.setContentType("json/text");
		response.getWriter().print(jsonArray.toString());
	}

}
