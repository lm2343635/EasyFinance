package edu.kit.ActMgr.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.ClassificationBean;

public interface ClassificationManager 
{
	/**
	 * 新增分类
	 * @param cname 分类名称
	 * @param ciid 图标id
	 * @return 分类id
	 */
	int addClassification(String cname,int ciid,HttpSession session);
	
	/**
	 * 删除分类
	 * @param cid 分类id
	 */
	void deleteClassification(int cid,HttpSession session);
	
	/**
	 * 修改分类
	 * @param cid 分类id
	 * @param cname 分类名称
	 * @param ciid 图标id
	 */
	void modifyClassification(int cid,String cname,int ciid,HttpSession session);
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	int getClassificationSize(HttpSession session);
	
	/**
	 * 获得当前账本分类
	 * @param session
	 * @return
	 */
	List<ClassificationBean> getClassification(HttpSession session);
	
	/**
	 * 根据分类id获取分类
	 * @param cid 分类id
	 * @return
	 */
	ClassificationBean getClassificationById(int cid,HttpSession session);
}
