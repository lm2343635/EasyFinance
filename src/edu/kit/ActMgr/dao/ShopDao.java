package edu.kit.ActMgr.dao;

import java.util.List;

import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Shop;
import edu.kit.ActMgr.domain.User;

public interface ShopDao 
{
	Shop get(Integer sid);
	Integer save(Shop shop);
	void update(Shop shop);
	void delete(Shop shop);
	void delete(Integer sid);
	List<Shop> findAll();
	
	/**
	 * 根据账本查找商家
	 * @param accountBook
	 * @return
	 */
	List<Shop> findByAccountBook(AccountBook accountBook);
	
	/**
	 * 查询指定用户未同步的商家
	 * @param user
	 * @return
	 */
	List<Shop> findNotSyncByUser(User user);
}
