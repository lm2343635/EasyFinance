package edu.kit.ActMgr.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.AccountBean;

public interface AccountManager 
{
	public static final int YEAR_TYPE=2;
	public static final int MONTH_TYPE=1;
	public static final int DAY_TYPE=0;
	
	/**
	 * 新增账户
	 * @param aname 账户名称
	 * @param siid 图标id
	 * @param assets 账户净资产
	 * @return 账户id
	 */
	int addAccount(String aname,int siid,double assets,HttpSession session);
	
	/**
	 * 删除账户
	 * @param aid 账户id
	 */
	void deleteAccount(int aid,HttpSession session);
	
	/**
	 * 
	 * @param aid 账户id
	 * @param aname 账户名称
	 * @param aiid 图标id
	 * @param asset 账户净资产
	 */
	void modifyAccount(int aid,String aname,int aiid,double asset,HttpSession session);
	
	/**
	 * 获得当前账本所有账户
	 * @param session
	 * @return
	 */
	List<AccountBean> getAccount(HttpSession session);
	
	/**
	 * 根据账户id获取账户
	 * @param aid 账户id
	 * @return
	 */
	AccountBean getAccountById(int aid,HttpSession session);
	
	/**
	 * 获得账户中心显示信息
	 * @return 总资产 总负债 净资产
	 */
	double [] getAccountCenterInfo(HttpSession session);
	
}
