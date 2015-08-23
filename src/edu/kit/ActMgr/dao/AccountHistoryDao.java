package edu.kit.ActMgr.dao;

import java.util.Date;
import java.util.List;

import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountHistory;
import edu.kit.ActMgr.domain.User;

public interface AccountHistoryDao 
{
	AccountHistory get(Integer ahid);
	Integer save(AccountHistory accountHistory);
	void update(AccountHistory accountHistory);
	void delete(AccountHistory accountHistory);
	void delete(Integer ahid);
	List<AccountHistory> findAll();
	
	/**
	 * 根据账户查找历史记录
	 * @param account 账户
	 * @return
	 */
	List<AccountHistory> findByAccount(Account account);
	
	/**
	 * 查询记录
	 * @param account 账户
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 账户历史记录
	 */
	List<AccountHistory> find(Account account,Date start,Date end);
	
	/**
	 * 通过起始日期查询账户历史记录
	 * @param account 账户
	 * @param start 起始日期
	 * @return 账户历史记录
	 */
	List<AccountHistory> findByStart(Account account,Date start);
	
	/**
	 * 通过截止日期查询账户历史记录
	 * @param account 账户
	 * @param start 起始日期
	 * @return 账户历史记录
	 */
	List<AccountHistory> findByEnd(Account account,Date end);
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	List<AccountHistory> findNotSyncByUser(User user);
	
	/**
	 * 查询记录
	 * @param account 账户
	 * @param date 日期
	 * @return 账户历史记录
	 */
	AccountHistory find(Account account,Date date);
	
	/**
	 * 强制查询记录，即当天没有记录时沿用之前最近一天的记录
	 * @param account 账户
	 * @param date 日期
	 * @return 账户历史记录
	 */
	AccountHistory forcedFind(Account account,Date date);
	
	/**
	 * 查找指定日期之前最近的历史记录
	 * @param account 账户
	 * @param date 指定日期
	 * @return
	 */
	AccountHistory findLatestBefore(Account account,Date date);
	
	/**
	 * 查找指定日期之后最近的历史记录
	 * @param account 账户
	 * @param date 指定日期
	 * @return 账户历史记录
	 */
	AccountHistory findLatestAfter(Account account,Date date);
	
	/**
	 * 得到长度
	 * @param hql
	 * @param objects
	 * @return
	 */
	int getSize(String hql,Object [] objects);
	
	/**
	 * 当前记录是否为第一条记录
	 * @param accountHistory 当前记录
	 * @return
	 */
	boolean isFirstHistory(AccountHistory accountHistory);
	
	/**
	 * 当前记录是否为最后一条记录
	 * @param accountHistory 当前记录
	 * @return 
	 */
	boolean isLastHistory(AccountHistory accountHistory);
}
