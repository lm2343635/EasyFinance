package edu.kit.ActMgr.dao.impl;

import java.util.List;

import edu.kit.ActMgr.dao.AccountBookDao;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;
import edu.kit.common.hibernate3.support.SystemInit;

public class AccountBookDaoHibernate extends AccountHibernateDaoSupport implements AccountBookDao
{

	@Override
	public AccountBook get(Integer abid) 
	{
		return getHibernateTemplate().get(AccountBook.class, abid);
	}

	@Override
	public Integer save(AccountBook accountBook) 
	{
		return (Integer)getHibernateTemplate().save(accountBook);
	}

	@Override
	public void update(AccountBook accountBook) 
	{
		getHibernateTemplate().update(accountBook);
	}

	@Override
	public void delete(AccountBook accountBook) 
	{
		getHibernateTemplate().delete(accountBook);
	}

	@Override
	public void delete(Integer abid) 
	{
		getHibernateTemplate().delete(get(abid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountBook> findAll() 
	{
		return (List<AccountBook>)getHibernateTemplate().find("from AccountBook");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountBook> findByPage(User user, int pageNumber, int pageSize) 
	{
		return findByPage("from AccountBook where user=?", user, pageNumber*pageSize, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountBook> findByUser(User user) 
	{
		return getHibernateTemplate().find("from AccountBook where user=?",user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountBook> findNotSyncByUser(User user) 
	{
		return getHibernateTemplate().find("from AccountBook where sync="+SystemInit.NOT_SYNC+" and user=?",user);
	}
}
