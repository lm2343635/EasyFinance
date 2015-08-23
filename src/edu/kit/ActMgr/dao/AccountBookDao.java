package edu.kit.ActMgr.dao;

import java.util.List;

import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.User;

public interface AccountBookDao 
{
	AccountBook get(Integer abid);
	Integer save(AccountBook accountBook);
	void update(AccountBook accountBook);
	void delete(AccountBook accountBook);
	void delete(Integer abid);
	List<AccountBook> findAll();
	
	/**
	 * 分页查询账本
	 * @param user 用户
	 * @param PageNumber 页码
	 * @param PageSize 页面大小
	 * @return 查询结果
	 */
	List<AccountBook> findByPage(User user,int pageNumber,int pageSize);
	
	/**
	 * 获得用户的所有账本
	 * @param user
	 * @return
	 */
	List<AccountBook> findByUser(User user);
	
	/**
	 * 获得用户的所有未同步账本
	 * @param user
	 * @return
	 */
	List<AccountBook> findNotSyncByUser(User user);
}
