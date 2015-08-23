package edu.kit.ActMgr.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import edu.kit.ActMgr.domain.Photo;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.common.hibernate3.support.SystemInit;
import edu.kit.common.util.FileTool;
import edu.kit.common.util.ImageTool;

@WebServlet("/PhotoServlet")
public class PhotoServlet extends HttpServlet 
{
	public static final int PHOTO_NO=4;
	public static final int USER_PHOTO_WITDH=250;
	public static final int USER_PHOTO_HEIGTH=156;
	public static final int RECORD_PHOTO_WIDTH=150;
	public static final int RECORD_PHOTO_HEIGHT=200;
	public static final String UPLOAD_FOLDER="upload";
	public static final String THUMBNAIL_SUFFIX="thumbnail.jpg";
	public static final int THUMBNAIL_WIDTH=140;
	public static final int THUMBNAIL_HEIGHT=105;
	
	private static final int FILE_MAX_SIZE=512*1024*1024;
	private static final long serialVersionUID = 1L;
	private String task;
       
    public PhotoServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "download":
			download(request,response);
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
		case "uploadUserPhoto": 
			uploadUserPhoto(request,response);
			break;
		case "uploadRecordPhoto":
			uploadRecordPhoto(request,response);
			break;
		default:
			break;
		}
	}

	private void uploadRecordPhoto(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("UTF-8");
		int uid=UserServlet.getUser(request.getSession()).getUid();	
		String filepath=createUploadPhotoDirectory(uid,getServletConfig());
		String fileName=upload(request, filepath);
		String fileFormat=FileTool.getFormat(fileName);
		String uuid=UUID.randomUUID().toString();
		String newFileName=uuid+"."+fileFormat;
		FileTool.modifyFileName(filepath, fileName, newFileName);
		//复制一份文件用来创建缩略图
		String thumbnail=uuid+"."+THUMBNAIL_SUFFIX;
		FileTool.copyFile(filepath+"/"+newFileName, filepath+"/"+thumbnail);
		//缩小图片
		ImageTool.createThumbnail(filepath+"/"+thumbnail,THUMBNAIL_WIDTH,THUMBNAIL_HEIGHT);
		ManagerTemplate manager=InitServlet.getServiceManager(getServletConfig());
		Photo photo=new Photo();
		photo.setPname(FileTool.getName(fileName));
		photo.setFilename(newFileName);
		photo.setUpload(new Date());
		photo.setAccountBook(manager.getUsingAccountBookFromSession(request.getSession()));
		photo.setSync(SystemInit.NOT_SYNC);
		int pid=manager.getPhotoDao().save(photo);
		String data="{\"filename\":\""+newFileName+"\",\"uid\":\""+uid+"\",\"pid\":\""+pid+"\"}";
		response.getWriter().print(data);
	}

	private void uploadUserPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		request.setCharacterEncoding("UTF-8");
		int uid=UserServlet.getUser(request.getSession()).getUid();
		//若文件夹不存在则创建
		String filepath=createUploadPhotoDirectory(uid,getServletConfig());
		//开始接收文件
		String fileName=upload(request, filepath);
		//使用UUID作为存储文件名
		String newFileName=UUID.randomUUID().toString()+"."+FileTool.getFormat(fileName);
		//更改文件名称
		FileTool.modifyFileName(filepath, fileName, newFileName);
		ImageTool.createThumbnail(filepath+"/"+newFileName,USER_PHOTO_WITDH,USER_PHOTO_HEIGTH);
		//开始写数据库
		ManagerTemplate manager=InitServlet.getServiceManager(getServletConfig());
		Photo photo=manager.getUserDao().get(uid).getPhoto();
		boolean usingSysNullUserPhoto=false;
		//如果用户的照片为系统用户空照片，新建一张照片数据记录
		if(photo.getPid()==SystemInit.SYS_NULL_ID)
		{
			photo=new Photo();
			usingSysNullUserPhoto=true;
		}
		//否则说明用户之前上传过照片，用户的照片文件夹肯定不会不是空，且这个照片文件夹中肯定有一张照片
		else
		{
			File oldPhoto=new File(filepath+"/"+photo.getFilename());
			if(oldPhoto.exists())
				oldPhoto.delete();
		}
		//写数据库
		photo.setPname(FileTool.getName(fileName));
		photo.setFilename(newFileName);
		photo.setUpload(new Date());
		//如果当前用户正在使用系统空用户照片，新建photo对象，并更新用户
		if(usingSysNullUserPhoto)
		{
			photo.setSync(SystemInit.NOT_SYNC);
			photo.setAccountBook(manager.getAccountBookDao().get(SystemInit.SYS_NULL_ID));
			int pid=manager.getPhotoDao().save(photo);
			User user=manager.getUserDao().get(uid);
			user.setPhoto(manager.getPhotoDao().get(pid));
			manager.getUserDao().update(user);
		}
		//否则只更新照片就好了
		else
			manager.getPhotoDao().update(photo);
		String data="{\"filename\":\""+newFileName+"\",\"uid\":\""+uid+"\"}";
		response.getWriter().print(data);
	}
	
	/**
	 * 如果需要的照片上传路径不存在，则创建
	 * @param uid 用户id
	 * @return 穿件完成后的上传路径
	 */
	public static String createUploadPhotoDirectory(int uid,ServletConfig config)
	{
		String rootPath=config.getServletContext().getRealPath("/");
		String filepath=rootPath+UPLOAD_FOLDER+"/"+uid;
		//如果用户不存文件夹，新建用户文件夹
		FileTool.createDirectoryIfNotExsit(filepath);
		//如果照片不存文件夹，新建照片文件夹
		filepath+="/"+PHOTO_NO;
		FileTool.createDirectoryIfNotExsit(filepath);
		return filepath;
	}
	
	/**
	 * 指定路径上传文件
	 * @param request HttpServletRequest
	 * @param filepath 文件路径
	 * @return 文件名
	 */
	@SuppressWarnings("unchecked")
	private String upload(HttpServletRequest request,String filepath)
	{
		String fileName=null;
		DiskFileItemFactory factory = new DiskFileItemFactory();//为文件对象产生工厂对象。
		factory.setSizeThreshold(1024*4); //设置缓冲区的大小，此处为4kb
		factory.setRepository(new File(filepath)); //设置上传文件的目的地
		ServletFileUpload upload = new ServletFileUpload(factory);//产生servlet上传对象
		upload.setSizeMax(FILE_MAX_SIZE);  //设置上传文件的大小
		try 
		{
			List<FileItem> list=upload.parseRequest(request); //取得所有的上传文件信息
			Iterator<FileItem> it=list.iterator();
			while(it.hasNext())
			{
			    FileItem item=it.next();
			    if(item.isFormField()==false)
			    { 
				    fileName=item.getName();   //文件名
				    //取文件名  
				    fileName=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());               
				    if(!fileName.equals("")&&!(fileName==null))
				    {
				    	//如果fileName为null，即没有上传文件  
				    	File uploadedFile=new File(filepath,fileName);  
				        try 
				        {
				        	item.write(uploadedFile);
				        } 
				        catch (Exception e)
				        {
				        	e.printStackTrace();
				        }  
				    }            
			    }
			}
		} 
		catch (FileUploadException e) 
		{
			e.printStackTrace();
		}
		return fileName;
	}

	private void download(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		int did=Integer.parseInt(request.getParameter("did"));
	
		String rootPath=getServletConfig().getServletContext().getRealPath("/");
		String filePath=rootPath+"document\\"+did;
		String fileName="";//此处设置文件名
		download(filePath, fileName, response);
	}
	
	/**
	 * 下载指定文件
	 * @param filePath 文件路径
	 * @param fileName
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	private void download(String filePath,String fileName,HttpServletResponse response) throws UnsupportedEncodingException 
	{
		FileInputStream in=null;
		ServletOutputStream out=null;
		response.setContentType("application/x-msdownload; charset=UTF-8");
		response.setHeader("Content-disposition","attachment; filename="+new String(fileName.getBytes("UTF-8"),"iso8859-1"));
		try
		{
			in=new FileInputStream(filePath+"\\"+fileName);
			out=response.getOutputStream();
			out.flush();
			int aRead=0;
			while((aRead=in.read())!=-1&in!=null)
				out.write(aRead);
			out.flush();
			in.close();
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
