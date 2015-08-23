package edu.kit.ActMgr.dao;

import java.util.Date;
import java.util.List;

import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Transfer;
import edu.kit.ActMgr.domain.User;

public interface TransferDao 
{
	Transfer get(Integer tfid);
	Integer save(Transfer transfer);
	void update(Transfer transfer);
	void delete(Transfer transfer);
	void delete(Integer tfid);
	List<Transfer> findAll();
	
	/**
	 * 查询转账记录
	 * @param start
	 * @param end
	 * @param tfin
	 * @param tfout
	 * @param remark
	 * @param accountBook
	 * @return
	 */
	List<Transfer> find(Date start,Date end,Account tfin,Account tfout,String remark,AccountBook accountBook);
	
	/**
	 * 根据账本查询转账记录
	 * @param accountBook
	 * @return
	 */
	List<Transfer> findByAccountBook(AccountBook accountBook);
	
	/**
	 * 查询指定用户的所有转账记录
	 * @param user
	 * @return
	 */
	List<Transfer> findNotSyncByUser(User user);
	
	/**
	 * 查询转账条数
	 * @param start
	 * @param end
	 * @param accountBook
	 * @return
	 */
	int getTransferSize(Date start,Date end,AccountBook accountBook);
	
	/**
	 * 查询某一天所有转入账户为指定账户的转账记录
	 * @param account 转入账户
	 * @param date 日期
	 * @return
	 */
	List<Transfer> findIn(Account account,Date date);
	
	/**
	 * 查询某一天所有转出账户为指定账户的转账记录
	 * @param account 转出账户
	 * @param date 日期
	 * @return
	 */
	List<Transfer> findOut(Account account,Date date);
}
