package edu.kit.ActMgr.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.AccountBookBean;

/**
 * 账本管理器
 * @author limeng
 * 所有访问业务逻辑层的访问源必须具有HttpSession，HttpSession就是访问用户的一张身份证，没有身份证，凭什么让你访问并操作数据。
 * 即使操作不涉及到HttpSession，出于安全的考虑，只有当前用户在HttpSession有效地情况下，才能访问业务逻辑组件。
 * 所以，业务逻辑组件只能被DWR框架在js中被访问，在Java代码中用户没有权限访问业务逻辑组件！！
 */
public interface AccountBookManager 
{
	/**
	 * 新增账本
	 * @param abname 账本名称
	 * @param abiid 图标id
	 * @param session
	 * @return 账本id
	 */
	int addAccountBook(String abname,int abiid,HttpSession session);
	
	/**
	 * 在未登录时新增账本
	 * @param uid 用户id
	 * @param abname
	 * @param abiid
	 * @param session
	 * @return
	 */
	int addAccountBookWithUid(int uid,String abname,int abiid,HttpSession session);
	
	/**
	 * 删除账本
	 * @param abid 账本id
	 */
	void deleteAccountBook(int abid,HttpSession session);
	
	/**
	 * 修改账本
	 * @param abid
	 * @param abname
	 * @param abiid
	 */
	void modifyAccountBook(int abid,String abname,int abiid,HttpSession session);
	
	/**
	 * 得到所有账本
	 * @param session
	 * @return 所有账本
	 */
	List<AccountBookBean> getAccountBook(HttpSession session);
	
	/**
	 * 分页得到账本
	 * @param uid 用户id
	 * @param pageNumber 页码
	 * @param pageSize 页面大小
	 * @return 账本列表
	 */
	List<AccountBookBean> getAccountBook(int pageNumber,int pageSize,HttpSession session);
	
	/**
	 * @param abid 账本id
	 * @param session
	 * @return 账本
	 */
	AccountBookBean getAccountBookById(int abid,HttpSession session);
	
	/**
	 * 得到账本数量
	 * @param session
	 * @return 账本数量
	 */
	int getAccountBookSize(HttpSession session);
	
	/**
	 * 设置正在使用的账本
	 * @param abid 账本id
	 * @param session
	 * @return 设置是否成功
	 */
	boolean setUsingAccountBook(int abid,HttpSession session);
	
	/**
	 * 得到正在使用的账本
	 * @param session
	 * @return
	 */
	AccountBookBean getUsingAccountBook(HttpSession session);
	
	/**
	 * 得到收支信息
	 * @param session
	 */
	int [] getAccountBookEarnSpendInfo(HttpSession session);
}
