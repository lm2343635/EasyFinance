package edu.kit.ActMgr.dao.impl;

import java.util.List;

import edu.kit.ActMgr.dao.UserDao;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;

public class UserDaoHibernate extends AccountHibernateDaoSupport implements UserDao 
{

	@Override
	public User get(Integer uid) 
	{
		return getHibernateTemplate().get(User.class, uid);
	}

	@Override
	public Integer save(User user) 
	{
		return (Integer)getHibernateTemplate().save(user);
	}

	@Override
	public void update(User user) 
	{
		getHibernateTemplate().merge(user);
		//getHibernateTemplate().update(user);
	}

	@Override
	public void delete(User user) 
	{
		getHibernateTemplate().delete(user);
	}

	@Override
	public void delete(Integer uid) 
	{
		getHibernateTemplate().delete(get(uid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() 
	{
		return (List<User>)getHibernateTemplate().find("from User");
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findByEmail(String email) 
	{
		List<User> users=(List<User>)getHibernateTemplate().find("from User where email=?",email);
		if(users!=null&&users.size()>0)
			return users.get(0);
		return null;
	}

}
