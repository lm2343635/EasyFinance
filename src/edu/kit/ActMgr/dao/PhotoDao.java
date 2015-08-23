package edu.kit.ActMgr.dao;

import java.util.Date;
import java.util.List;

import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Photo;
import edu.kit.ActMgr.domain.User;

public interface PhotoDao 
{
	Photo get(Integer pid);
	Integer save(Photo photo);
	void update(Photo photo);
	void delete(Photo photo);
	void delete(Integer pid);
	List<Photo> findAll();

	/**
	 * 搜索照片
	 * @param accountBook 账本，必选搜索条件
	 * @param start 起始日期，可选
	 * @param end 结束日期，可选
	 * @param pname 照片名称，可选
	 * @return
	 */
	List<Photo> find(AccountBook accountBook,Date start,Date end,String pname);
	
	/**
	 * 查询指定用户的所有未同步照片
	 * @param user
	 * @return
	 */
	List<Photo> findNotSyncPhotoByUser(User user);
}
