package edu.kit.ActMgr.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.AccountHistoryBean;
import edu.kit.ActMgr.domain.Account;

public interface AccountHistoryManager 
{
	public static final int YEAR_TYPE=2;
	public static final int MONTH_TYPE=1;
	public static final int DAY_TYPE=0;
	
	public static final String YEAR_FORMAT="yyyy";
	public static final String MONTH_FORMAT="yyyy-MM";
	public static final String DATE_FORMAT="yyyy-MM-dd";
	
	/**
	 * 更新资产
	 * @param account
	 * @param date
	 * @param money
	 * @return
	 */
	int update(Account account,Date date,double money);
	
	/**
	 * 获取对账报表
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param type 时间类型
	 * @param aid 账户id
	 * @param session
	 * @return
	 */
	public List<AccountHistoryBean> getAccountHistories(String start,String end,int type,int aid,HttpSession session);
}
