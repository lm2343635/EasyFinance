package edu.kit.ActMgr.dao;

import java.util.List;

import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Classification;
import edu.kit.ActMgr.domain.User;

public interface ClassificationDao 
{
	Classification get(Integer cid);
	Integer save(Classification classification);
	void update(Classification classification);
	void delete(Classification classification);
	void delete(Integer cid);
	List<Classification> findAll();
	
	/**
	 * 根据账本查找分类
	 * @param accountBook
	 * @return
	 */
	List<Classification> findByAccountBook(AccountBook accountBook);
	
	/**
	 * 查询指定用户未同步的分类
	 * @param user
	 * @return
	 */
	List<Classification> findNotSyncByUser(User user);
}
