package edu.kit.ActMgr.dao;

import java.util.List;

import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.User;

/**
 * @author limeng
 *
 */
public interface AccountDao 
{
	Account get(Integer aid);
	Integer save(Account account);
	void update(Account account);
	void delete(Account account);
	void delete(Integer aid);
	List<Account> findAll();
	
	/**
	 * 根据账本查找账户
	 * @param accountBook 账本
	 * @return 账户
	 */
	List<Account> findByAccountBook(AccountBook accountBook);
	
	/**
	 * 查询指定用户未同步的账户
	 * @param user
	 * @return
	 */
	List<Account> finNotSyncByUser(User user);
}
