package edu.kit.ActMgr.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.TemplateBean;

public interface TemplateManager 
{
	/**
	 * 新增模板
	 * @param tpname 模板名称
	 * @param cid 分类id
	 * @param aid 账户id
	 * @param sid 商家id
	 * @param session
	 * @return
	 */
	int addTemplate(String tpname,int cid,int aid,int sid,HttpSession session);
	
	/**
	 * 删除模板
	 * @param tpid 模板id
	 */
	void deleteTemplate(int tpid,HttpSession session);
	
	/**
	 * 得到当前账户模板
	 * @param session
	 * @return
	 */
	List<TemplateBean> getTemplate(HttpSession session);
	
	/**
	 * 修改模板
	 * @param tpid 模板id
	 * @param tpname 模板名称
	 * @param cid 分类id
	 * @param aid 账户id
	 * @param sid 商家id
	 * @param session
	 */
	void modifyTemplate(int tpid,String tpname,int cid,int aid,int sid,HttpSession session);
	
	/**
	 * 根据模板id得到模板
	 * @param tpid 模板id
	 * @return 模板
	 */
	TemplateBean getTemplateById(int tpid,HttpSession session);
}
