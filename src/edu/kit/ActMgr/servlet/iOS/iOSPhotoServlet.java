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
import net.sf.json.JsonConfig;
import edu.kit.ActMgr.data.iOS.iOSPhotoData;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Photo;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.ActMgr.servlet.PhotoServlet;
import edu.kit.common.hibernate3.support.SystemInit;

@WebServlet("/iOSPhotoServlet")
public class iOSPhotoServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private DaoManager dao;
	private String task;
       
    public iOSPhotoServlet() 
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
		case "getPhotoFilePath":
			getPhotoFilePath(request,response);
			break;
		case "getPhotos":
			getPhotos(request,response);
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

	private void getPhotoFilePath(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int pid=Integer.parseInt(request.getParameter("pid"));
		Photo photo=dao.getPhotoDao().get(pid);
		String path=PhotoServlet.UPLOAD_FOLDER+"/"+photo.getAccountBook().getUser().getUid()+"/"+PhotoServlet.PHOTO_NO+"/"+photo.getFilename();
		response.getWriter().print(path);
	}

	private void getPhotos(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int abid=Integer.parseInt(request.getParameter("abid"));
		AccountBook accountBook=dao.getAccountBookDao().get(abid);
		List<iOSPhotoData> photos=new ArrayList<iOSPhotoData>();
		for(Photo photo:dao.getPhotoDao().find(accountBook, null, null, null))
		{
			photo.setSync(SystemInit.SYNCED);
			dao.getPhotoDao().update(photo);
			photos.add(new iOSPhotoData(photo));
		}
		JSONArray jsonArray=JSONArray.fromObject(photos);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}
	
	@SuppressWarnings("unchecked")
	private void push(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String arrayString=request.getParameter("array");
		if(iOSSynchronizeServlet.DEBUG==true)
			System.out.println("Get photo message from client: "+arrayString);
		JSONArray jsonArray=JSONArray.fromObject(arrayString);
		List<iOSPhotoData> datas=JSONArray.toList(jsonArray, new iOSPhotoData(), new JsonConfig());
		for(iOSPhotoData data: datas)
		{
			Photo photo=dao.getPhotoDao().get(data.getPid());
			//如果服务中不存在iOS客户端中传过来的实体，就要在服务器中新建该实体
			if(photo==null)
			{
				photo=new Photo();
				photo.setUpload(new Date(data.getTimeInterval()));
				photo.setAccountBook(dao.getAccountBookDao().get(data.getAbid()));
				//使用uuid先随机设置一个文件名，在后面的文件上传中，这个文件名将用来保存图片文件
				photo.setFilename(UUID.randomUUID().toString()+".jpg");
				photo.setPname("iOS Client Upload");
				//设置同步属性为已同步
				photo.setSync(SystemInit.SYNCED);
				int pid=dao.getPhotoDao().save(photo);
				data.setSync(SystemInit.SYNCED);
				data.setPid(pid);
			}
		}
		jsonArray=JSONArray.fromObject(datas);
		response.setContentType("json/text");
		response.getWriter().print(jsonArray.toString());
	}
	
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int uid=Integer.parseInt(request.getParameter("uid"));
		User user=dao.getUserDao().get(uid);
		List<iOSPhotoData> photoDatas=new ArrayList<iOSPhotoData>();
		for(Photo photo: dao.getPhotoDao().findNotSyncPhotoByUser(user))
		{
			photo.setSync(SystemInit.SYNCED);
			dao.getPhotoDao().update(photo);
			photoDatas.add(new iOSPhotoData(photo));
		}
		JSONArray jsonArray=JSONArray.fromObject(photoDatas);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}
}
