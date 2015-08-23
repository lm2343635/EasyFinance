package edu.kit.ActMgr.servlet.iOS;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import edu.kit.ActMgr.domain.Photo;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.ActMgr.servlet.PhotoServlet;
import edu.kit.common.util.FileTool;
import edu.kit.common.util.ImageTool;

@WebServlet("/iOSPhotoUploadServlet")
@MultipartConfig
public class iOSPhotoUploadServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private DaoManager dao;
	
	public void service(HttpServletRequest request ,HttpServletResponse response) throws IOException , ServletException
	{
		dao=DaoManager.get(getServletContext());
		// 获取普通请求参数
		int pid=Integer.parseInt(request.getParameter("pid"));
		// 获取文件上传域
		Part part = request.getPart("iOSClentUpload");
		Photo photo=dao.getPhotoDao().get(pid);
		String filepath=PhotoServlet.createUploadPhotoDirectory(photo.getAccountBook().getUser().getUid(), getServletConfig());
		String fileName=photo.getFilename();
		//接收文件，并保存在指定目录
		part.write(filepath+"/"+fileName);
		//复制一份文件用来创建缩略图
		String thumbnail=FileTool.getName(fileName)+"."+PhotoServlet.THUMBNAIL_SUFFIX;
		FileTool.copyFile(filepath+"/"+fileName, filepath+"/"+thumbnail);
		//缩小图片
		ImageTool.createThumbnail(filepath+"/"+thumbnail,PhotoServlet.THUMBNAIL_WIDTH,PhotoServlet.THUMBNAIL_HEIGHT);
	}
}