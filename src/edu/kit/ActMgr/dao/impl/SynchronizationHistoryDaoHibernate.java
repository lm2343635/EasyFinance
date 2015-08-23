package edu.kit.ActMgr.dao.impl;

import java.util.List;

import edu.kit.ActMgr.dao.SynchronizationHistoryDao;
import edu.kit.ActMgr.domain.SynchronizationHistory;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;

public class SynchronizationHistoryDaoHibernate extends AccountHibernateDaoSupport implements SynchronizationHistoryDao 
{

	@Override
	public SynchronizationHistory get(Integer shid) 
	{
		return getHibernateTemplate().get(SynchronizationHistory.class, shid);
	}

	@Override
	public Integer save(SynchronizationHistory synchronizationHistory) 
	{
		return (Integer)getHibernateTemplate().save(synchronizationHistory);
	}

	@Override
	public void update(SynchronizationHistory synchronizationHistory) 
	{
		getHibernateTemplate().update(synchronizationHistory);
	}

	@Override
	public void delete(SynchronizationHistory synchronizationHistory) 
	{
		getHibernateTemplate().delete(synchronizationHistory);
	}

	@Override
	public void delete(Integer shid) 
	{
		delete(get(shid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SynchronizationHistory> findAll() 
	{
		return getHibernateTemplate().find("from SynchronizationHistory");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SynchronizationHistory> findByUser(User user) 
	{
		String hql="from SynchronizationHistory where user=? order by time desc";
		return getHibernateTemplate().find(hql, user);
	}

}
