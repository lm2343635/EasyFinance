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
import edu.kit.ActMgr.bean.ClassificationBean;
import edu.kit.ActMgr.data.iOS.iOSClassificationData;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Classification;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.common.hibernate3.support.SystemInit;

@WebServlet("/iOSClassificationServlet")
public class iOSClassificationServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private DaoManager dao;
	private String task;
       
    public iOSClassificationServlet() 
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
		switch (task) 
		{
		//iOS客户端首次导入数据使用
		case "getClassifications":
			getClassifications(request,response);
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

	private void getClassifications(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int abid=Integer.parseInt(request.getParameter("abid"));
		AccountBook accountBook=dao.getAccountBookDao().get(abid);
		List<ClassificationBean> classifications=new ArrayList<ClassificationBean>();
		for(Classification classification:dao.getClassificationDao().findByAccountBook(accountBook))
		{
			//iOS客户端导入数据时要将服务器端实体的sync属性设为1，表示已同步
			classification.setSync(SystemInit.SYNCED);
			dao.getClassificationDao().update(classification);
			classifications.add(new ClassificationBean(classification));
		}
		JSONArray jsonArray=JSONArray.fromObject(classifications);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}

	@SuppressWarnings("unchecked")
	private void push(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String arrayString=request.getParameter("array");
		if(iOSSynchronizeServlet.DEBUG==true)
			System.out.println("Get classification message from client: "+arrayString);
		JSONArray jsonArray=JSONArray.fromObject(arrayString);
		List<iOSClassificationData> datas=JSONArray.toList(jsonArray, new iOSClassificationData(), new JsonConfig());
		for(iOSClassificationData data:datas)
		{
			Classification classification=dao.getClassificationDao().get(data.getCid());
			//如果服务中不存在iOS客户端中传过来的实体，就要在服务器中新建该实体
			if(classification==null)
			{
				classification=new Classification();
				classification.setCname(data.getCname());
				classification.setCin(data.getCin());
				classification.setCout(data.getCout());
				classification.setCicon(dao.getIconDao().get(data.getIid()));
				classification.setAccountBook(dao.getAccountBookDao().get(data.getAbid()));
				//设置同步属性为已同步
				classification.setSync(SystemInit.SYNCED);
				int cid=dao.getClassificationDao().save(classification);
				data.setSync(SystemInit.SYNCED);
				data.setCid(cid);
			}
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
		List<iOSClassificationData> classificationDatas=new ArrayList<iOSClassificationData>();
		for(Classification classification:dao.getClassificationDao().findNotSyncByUser(user))
		{
			classification.setSync(SystemInit.SYNCED);
			dao.getClassificationDao().update(classification);
			classificationDatas.add(new iOSClassificationData(classification));
		}
		JSONArray jsonArray=JSONArray.fromObject(classificationDatas);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}
}
