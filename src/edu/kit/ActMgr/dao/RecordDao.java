package edu.kit.ActMgr.dao;

import java.util.Date;
import java.util.List;

import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Classification;
import edu.kit.ActMgr.domain.Record;
import edu.kit.ActMgr.domain.Shop;
import edu.kit.ActMgr.domain.User;

public interface RecordDao 
{
	Record get(Integer rid);
	Integer save(Record record);
	void update(Record record);
	void delete(Record record);
	void delete(Integer rid);
	List<Record> findAll();

	
	/**
	 * 查询记账记录
	 * @param start
	 * @param end
	 * @param classification
	 * @param account
	 * @param shop
	 * @param remark
	 * @param accountBook
	 * @return
	 */
	List<Record> find(Date start,Date end,Classification classification,Account account,Shop shop,String remark,AccountBook accountBook);
	
	/**
	 * 查找某个账本一天内的收支记录
	 * @param date 日期
	 * @param accountBook 账本
	 * @return 某个账本一天内的收支记录
	 */
	List<Record> findDay(Date date,AccountBook accountBook);
	
	/**
	 * 查找某个账户一天内的收支记录
	 * @param date 日期
	 * @param account 账户
	 * @return 某个账户一天内的收支记录
	 */
	List<Record> findDay(Date date,Account account);
	
	/**
	 * 查询指定用户未同步的所有收支记录
	 * @param user
	 * @return
	 */
	List<Record> findNotSyncByUser(User user);
	
	/**
	 * 查询记账账目条数
	 * @param start
	 * @param end
	 * @param accountBook
	 * @return
	 */
	int getRecordSize(Date start,Date end,AccountBook accountBook);
	
	/**
	 * 按账本查找所有收支记录，供iOS导入使用
	 * @param accountBook
	 * @return
	 */
	List<Record> findByAccountBook(AccountBook accountBook);
}
