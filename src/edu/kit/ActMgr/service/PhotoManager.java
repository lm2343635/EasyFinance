package edu.kit.ActMgr.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.PhotoBean;

public interface PhotoManager 
{
	/**
	 * 根据照片id得到照片
	 * @param pid 照片id
	 * @param session HttpSession
	 * @return 照片
	 */
	PhotoBean getPhotoById(int pid,HttpSession session);
	
	/**
	 * 得到账本相册
	 * @param session
	 * @return
	 */
	List<PhotoBean> getAccountBookAlbum(HttpSession session);
}
