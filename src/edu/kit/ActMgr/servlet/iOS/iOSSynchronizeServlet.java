package edu.kit.ActMgr.servlet.iOS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import edu.kit.ActMgr.data.iOS.iOSSynchronizationHistoryData;
import edu.kit.ActMgr.domain.SynchronizationHistory;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;

@WebServlet("/iOSSynchronizeServlet")
public class iOSSynchronizeServlet extends HttpServlet 
{
	public static final Boolean DEBUG=true;
	
	private static final long serialVersionUID = 1L;
	private DaoManager dao;
	private String task;

    public iOSSynchronizeServlet() 
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
		case "resgistSyncKey":
			resgistSyncKey(request,response);
			break;
		case "checkSyncKey":
			checkSyncKey(request,response);
			break;
		case "receiveSynchronizationHistory":
			receiveSynchronizationHistory(request,response);
			break;
		case "getSynchronizationHistories":
			getSynchronizationHistories(request,response);
			break;
		default:
			break;
		}
	}

	private void resgistSyncKey(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int uid=Integer.parseInt(request.getParameter("uid"));
		String iOSDeviceInfo=request.getParameter("iOSDeviceInfo");
		String syncKey=UUID.randomUUID().toString();
		User user=dao.getUserDao().get(uid);
		user.setSyncKey(syncKey);
		user.setiOSDeviceInfo(iOSDeviceInfo);
		dao.getUserDao().update(user);
		response.getWriter().print(syncKey);
	}

	private void checkSyncKey(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int uid=Integer.parseInt(request.getParameter("uid"));
		String key=request.getParameter("key");
		User user=dao.getUserDao().get(uid);
		//只有同步密钥匹配才能进行同步，密钥使用一次后就要更新密钥，上一个密钥即废弃
		if(user.getSyncKey().equals(key))
		{
			key=UUID.randomUUID().toString();
			user.setSyncKey(key);
			dao.getUserDao().update(user);
			response.getWriter().print(key);
		}
	}
	
	private void receiveSynchronizationHistory(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String objectString=request.getParameter("object");
		if(iOSSynchronizeServlet.DEBUG==true)
			System.out.println("Get synchronization history message from client: "+objectString);
		String ip=request.getRemoteAddr();
		JSONObject jsonObject=JSONObject.fromObject(objectString);
		iOSSynchronizationHistoryData data=(iOSSynchronizationHistoryData)JSONObject.
				toBean(jsonObject, new iOSSynchronizationHistoryData(), new JsonConfig());
		SynchronizationHistory history=new SynchronizationHistory();
		history.setTime(new Date(data.getTimeInterval()));
		history.setDevice(data.getDevice());
		history.setIp(ip);
		history.setUser(dao.getUserDao().get(data.getUid()));
		int shid=dao.getSynchronizationHistoryDao().save(history);
		//把shid和客户端ip返回给客户端
		data.setIp(ip);
		data.setShid(shid);
		jsonObject=JSONObject.fromObject(data);
		response.setContentType("text/json");
		response.getWriter().print(jsonObject.toString());
	}

	private void getSynchronizationHistories(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int uid=Integer.parseInt(request.getParameter("uid"));
		User user=dao.getUserDao().get(uid);
		List<iOSSynchronizationHistoryData> historyDatas=new ArrayList<iOSSynchronizationHistoryData>();
		for(SynchronizationHistory history: dao.getSynchronizationHistoryDao().findByUser(user))
			historyDatas.add(new iOSSynchronizationHistoryData(history));
		JSONArray jsonObject=JSONArray.fromObject(historyDatas);
		response.setContentType("text/json");
		response.getWriter().print(jsonObject.toString());
	}

}
