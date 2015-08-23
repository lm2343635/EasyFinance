package edu.kit.ActMgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ActMgr.dao.AccountDao;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;
import edu.kit.common.hibernate3.support.SystemInit;

public class AccountDaoHibernate extends AccountHibernateDaoSupport implements AccountDao 
{

	@Override
	public Account get(Integer aid) 
	{
		return getHibernateTemplate().get(Account.class, aid);
	}

	@Override
	public Integer save(Account account) 
	{
		return (Integer)getHibernateTemplate().save(account);
	}

	@Override
	public void update(Account account) 
	{
		getHibernateTemplate().update(account);
	}

	@Override
	public void delete(Account account) 
	{
		getHibernateTemplate().delete(account);
	}

	@Override
	public void delete(Integer aid) 
	{
		getHibernateTemplate().delete(get(aid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> findAll() 
	{
		return (List<Account>)getHibernateTemplate().find("from Account");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> findByAccountBook(AccountBook accountBook) 
	{
		return (List<Account>)getHibernateTemplate().find("from Account where accountBook=?",accountBook);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> finNotSyncByUser(User user) 
	{
		List<Account> notSyncAccounts=new ArrayList<Account>();
		for(AccountBook accountBook:user.getAccountBooks())
		{
			List<Account> accounts=getHibernateTemplate().
					find("from Account where accountBook=? and sync=?",accountBook,SystemInit.NOT_SYNC);
			notSyncAccounts.addAll(accounts);
		}
		return notSyncAccounts;
	}

}
