package edu.kit.ActMgr.dao;

import java.util.List;

import edu.kit.ActMgr.domain.Icon;
import edu.kit.ActMgr.domain.User;

public interface IconDao 
{
	Icon get(Integer iid);
	Integer save(Icon icon);
	void update(Icon icon);
	void delete(Icon icon);
	void delete(Integer iid);
	List<Icon> findAll();
	
	/**
	 * 按用户查找
	 * @param user
	 * @return
	 */
	List<Icon> findByUser(User user);
	
	/**
	 * 根据用户和图标类别分页查询图标
	 * @param user 用户
	 * @param type 类别
	 * @param pageNumber 页数
	 * @param pageSize 页长
	 * @return
	 */
	public List<Icon> findByType(User user,Integer type,int pageNumber,int pageSize);
	
	public Integer getIconSize(User user,Integer type);
}
