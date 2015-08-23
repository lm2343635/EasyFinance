package edu.kit.ActMgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.PhotoBean;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Photo;
import edu.kit.ActMgr.service.PhotoManager;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.ActMgr.servlet.UserServlet;
import edu.kit.common.hibernate3.support.SystemInit;

public class PhotoManagerImpl extends ManagerTemplate implements PhotoManager 
{

	@Override
	public PhotoBean getPhotoById(int pid, HttpSession session) 
	{
		Photo photo=photoDao.get(pid);
		//当用户调用系统照片或用户调用自身的用户照片或者调用该账本中收支记录照片，给予返回
		if(getUsingAccountBookFromSession(session).getAbid()==photo.getAccountBook().getAbid()
				||pid==userDao.get(UserServlet.getUser(session).getUid()).getPhoto().getPid()
				||pid==SystemInit.SYS_NULL_ID||pid==SystemInit.SYS_RECORD_PHOTO_NULL)
			return new PhotoBean(photo);
		//否则说明用户无权限访问，返回空
		return null;
	}

	@Override
	public List<PhotoBean> getAccountBookAlbum(HttpSession session) 
	{
		AccountBook usingaAccountBook=getUsingAccountBookFromSession(session);
		List<PhotoBean> photos=new ArrayList<PhotoBean>();
		for(Photo photo:photoDao.find(usingaAccountBook, null, null, null))
			photos.add(new PhotoBean(photo));
		return photos;
	}

}
