package edu.kit.ActMgr.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import edu.kit.ActMgr.dao.AccountHistoryDao;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.AccountHistory;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;
import edu.kit.common.hibernate3.support.SystemInit;

public class AccountHistoryDaoHibernate extends AccountHibernateDaoSupport implements AccountHistoryDao
{
	
	@Override
	public AccountHistory get(Integer ahid) 
	{
		return getHibernateTemplate().get(AccountHistory.class, ahid);
	}

	@Override
	public Integer save(AccountHistory accountHistory) 
	{
		return (Integer)getHibernateTemplate().save(accountHistory);
	}

	@Override
	public void update(AccountHistory accountHistory) 
	{
		getHibernateTemplate().update(accountHistory);
	}

	@Override
	public void delete(AccountHistory accountHistory) 
	{
		getHibernateTemplate().delete(accountHistory);
	}

	@Override
	public void delete(Integer ahid) 
	{
		getHibernateTemplate().delete(get(ahid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountHistory> findAll() 
	{
		return (List<AccountHistory>)getHibernateTemplate().find("from AccountHistory");
	}

	@Override
	public AccountHistory find(Account account, Date date) 
	{
		String hql="from AccountHistory where account=? and date=?";
		@SuppressWarnings("unchecked")
		List<AccountHistory> histories=(List<AccountHistory>)getHibernateTemplate().find(hql,account,date);
		if(histories.size()==0)
			return null;
		return histories.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountHistory> findByStart(Account account, Date start) 
	{
		String hql="from AccountHistory where account=? and date>=? order by date";
		return (List<AccountHistory>)getHibernateTemplate().find(hql,account,start);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountHistory> findByEnd(Account account, Date end) 
	{
		String hql="from AccountHistory where account=? and date<=? order by date";
		return (List<AccountHistory>)getHibernateTemplate().find(hql,account,end);
	}

	@Override
	public boolean isLastHistory(AccountHistory accountHistory) 
	{
		final String hql="select count(*) from AccountHistory where account=? and date>?";
		int size=getSize(hql, new Object[]{accountHistory.getAccount(),accountHistory.getDate()});
		if(size==0)
			return true;
		return false;
	}

	@Override
	public boolean isFirstHistory(AccountHistory accountHistory) 
	{
		final String hql="select count(*) from AccountHistory where account=? and date<?";
		int size=getSize(hql, new Object[]{accountHistory.getAccount(),accountHistory.getDate()});
		if(size==0)
			return true;
		return false;
	}

	@Override
	public int getSize(final String hql, final Object[] objects) 
	{
		int size=getHibernateTemplate().execute(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException,SQLException 
			{
				Query query=session.createQuery(hql);
				for(int i=0;i<objects.length;i++)
				query.setParameter(i, objects[i]);
				return (long)query.uniqueResult();
			}
		}).intValue();
		return size;
	}

	@Override
	public AccountHistory findLatestBefore(final Account account, final Date date) 
	{
		final String hql="from AccountHistory where account=? and date<? order by date desc";
		return getHibernateTemplate().execute(new HibernateCallback<AccountHistory>() {
			@Override
			public AccountHistory doInHibernate(Session session) throws HibernateException, SQLException 
			{
				Query query=session.createQuery(hql);
				query.setParameter(0, account);
				query.setParameter(1, date);
				query.setMaxResults(1);
				if(query.list().size()==0)
					return null;
				return (AccountHistory)(query.list().get(0));
			}
		});
	}

	@Override
	public AccountHistory findLatestAfter(final Account account, final Date date) 
	{
		final String hql="from AccountHistory where account=? and date>? order by date";
		return getHibernateTemplate().execute(new HibernateCallback<AccountHistory>() {
			@Override
			public AccountHistory doInHibernate(Session session)throws HibernateException, SQLException 
			{
				Query query=session.createQuery(hql);
				query.setParameter(0, account);
				query.setParameter(1, date);
				query.setMaxResults(1);
				if(query.list().size()==0)
					return null;
				return (AccountHistory)(query.list().get(0));
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountHistory> find(Account account, Date start, Date end) 
	{
		String hql="from AccountHistory where account=? and date between ? and ? order by date";
		return (List<AccountHistory>)getHibernateTemplate().find(hql,account,start,end);
	}

	@Override
	public AccountHistory forcedFind(Account account, Date date) 
	{
		AccountHistory history=find(account, date);
		if(history==null)
			history=findLatestBefore(account, date);
		return history;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountHistory> findByAccount(Account account) {
		String hql="from AccountHistory where account=? order by date";
		return getHibernateTemplate().find(hql, account);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountHistory> findNotSyncByUser(User user) 
	{
		List<AccountHistory> notSyncAccountHistories=new ArrayList<AccountHistory>();
		for(AccountBook accountBook: user.getAccountBooks())
		{
			for(Account account: accountBook.getAccounts())
			{
				List<AccountHistory> accountHistories=getHibernateTemplate().
						find("from AccountHistory where account=? and sync=?",account,SystemInit.NOT_SYNC);
				notSyncAccountHistories.addAll(accountHistories);
			}
		}
		return notSyncAccountHistories;
	}
}
