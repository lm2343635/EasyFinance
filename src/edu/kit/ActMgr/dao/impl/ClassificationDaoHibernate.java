package edu.kit.ActMgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ActMgr.dao.ClassificationDao;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Classification;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;
import edu.kit.common.hibernate3.support.SystemInit;

public class ClassificationDaoHibernate extends AccountHibernateDaoSupport implements ClassificationDao 
{

	@Override
	public Classification get(Integer cid) 
	{
		return getHibernateTemplate().get(Classification.class, cid);
	}

	@Override
	public Integer save(Classification classification) 
	{
		return (Integer)getHibernateTemplate().save(classification);
	}

	@Override
	public void update(Classification classification) 
	{
		getHibernateTemplate().update(classification);
	}

	@Override
	public void delete(Classification classification) 
	{
		getHibernateTemplate().delete(classification);
	}

	@Override
	public void delete(Integer cid) 
	{
		getHibernateTemplate().delete(get(cid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Classification> findAll() 
	{
		return (List<Classification>)getHibernateTemplate().find("from Classification");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Classification> findByAccountBook(AccountBook accountBook) 
	{
		return (List<Classification>)getHibernateTemplate().find("from Classification where accountBook=?",accountBook);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Classification> findNotSyncByUser(User user) 
	{
		List<Classification> notSyncClassifications=new ArrayList<Classification>();
		for(AccountBook accountBook: user.getAccountBooks())
		{
			List<Classification> classifications=getHibernateTemplate().
					find("from Classification where accountBook=? and sync=? ",accountBook,SystemInit.NOT_SYNC);
			notSyncClassifications.addAll(classifications);
		}
		return notSyncClassifications;
	}

}
