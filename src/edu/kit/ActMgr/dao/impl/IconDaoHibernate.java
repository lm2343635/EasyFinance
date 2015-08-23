package edu.kit.ActMgr.dao.impl;

import java.util.List;

import edu.kit.ActMgr.dao.IconDao;
import edu.kit.ActMgr.domain.Icon;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;

public class IconDaoHibernate extends AccountHibernateDaoSupport implements IconDao
{

	@Override
	public Icon get(Integer iid) 
	{
		return getHibernateTemplate().get(Icon.class, iid);
	}

	@Override
	public Integer save(Icon icon) 
	{
		return (Integer)getHibernateTemplate().save(icon);
	}

	@Override
	public void update(Icon icon) 
	{
		getHibernateTemplate().update(icon);
	}

	@Override
	public void delete(Icon icon) 
	{
		getHibernateTemplate().delete(icon);
	}

	@Override
	public void delete(Integer iid) 
	{
		getHibernateTemplate().delete(get(iid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Icon> findAll() 
	{
		return (List<Icon>)getHibernateTemplate().find("from Icon");
	}

	@SuppressWarnings("unchecked")
	public List<Icon> findByType(User user,Integer type,int pageNumber,int pageSize)
	{
		return (List<Icon>)findByPage("from Icon where user=? and type=?"
				, new Object[]{user,type}, pageNumber*pageSize, pageSize);
	}

	@Override
	public Integer getIconSize(User user, Integer type) 
	{
		return (getHibernateTemplate().find(" from Icon where user=? and type=?",user,type)).size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Icon> findByUser(User user)
	{
		return getHibernateTemplate().find(" from Icon where user=?",user);
	}
}
