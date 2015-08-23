package edu.kit.ActMgr.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import edu.kit.ActMgr.dao.TransferDao;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Transfer;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;
import edu.kit.common.hibernate3.support.SystemInit;
import edu.kit.common.util.DateTool;

public class TransferDaoHibernate extends AccountHibernateDaoSupport implements TransferDao
{

	@Override
	public Transfer get(Integer tfid) 
	{
		return (Transfer)getHibernateTemplate().get(Transfer.class, tfid);
	}

	@Override
	public Integer save(Transfer transfer) 
	{
		return (Integer)getHibernateTemplate().save(transfer);
	}

	@Override
	public void update(Transfer transfer) 
	{
		getHibernateTemplate().update(transfer);
	}

	@Override
	public void delete(Transfer transfer) 
	{
		getHibernateTemplate().delete(transfer);
	}

	@Override
	public void delete(Integer tfid) 
	{
		getHibernateTemplate().delete(get(tfid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transfer> findAll() 
	{
		return (List<Transfer>)getHibernateTemplate().find("from Transfer");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transfer> find(Date start, Date end, Account tfin,Account tfout, String remark,AccountBook accountBook)
	{
		List<Object> values=new ArrayList<Object>();
		String hql="from Transfer where accountBook=? ";
		values.add(accountBook);
		if(tfin!=null)
		{
			hql+=" and tfin=? ";
			values.add(tfin);
		}
		if(tfout!=null)
		{
			hql+=" and tfout=? ";
			values.add(tfout);
		}
		if(remark!=null)
		{
			hql+=" and remark like ? ";
			values.add("%"+remark+"%");
		}
		hql+=" and time between ? and ? order by time desc";
		values.add(start);
		values.add(end);
		Object [] args=new Object[values.size()];
		for(int i=0;i<args.length;i++)
			args[i]=values.get(i);
		return (List<Transfer>)getHibernateTemplate().find(hql,args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transfer> findByAccountBook(AccountBook accountBook) 
	{
		String hql="from Transfer where accountBook=?";
		return getHibernateTemplate().find(hql, accountBook);
	}
	
	@Override
	public int getTransferSize(final Date start, final Date end,final AccountBook accountBook) 
	{
		final String hql="select count(*) from Transfer where accountBook=? and time between ? and ? order by time desc";
		int size=getHibernateTemplate().execute(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException,SQLException 
			{
				Query query=session.createQuery(hql);
				query.setParameter(0, accountBook);
				query.setParameter(1, start);
				query.setParameter(2, end);
				return (long)query.uniqueResult();
			}
		}).intValue();
		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transfer> findIn(Account account, Date date) 
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String day=df.format(date);
		Date start=DateTool.transferDate(day+" 00:00:00","yyyy-MM-dd hh:mm");
		Date end=DateTool.transferDate(day+" 23:59:59","yyyy-MM-dd hh:mm");
		String hql="from Transfer where tfin=? and time between ? and ? order by time desc";
		return (List<Transfer>)getHibernateTemplate().find(hql,account,start,end);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transfer> findOut(Account account, Date date) 
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String day=df.format(date);
		Date start=DateTool.transferDate(day+" 00:00:00","yyyy-MM-dd hh:mm");
		Date end=DateTool.transferDate(day+" 23:59:59","yyyy-MM-dd hh:mm");
		String hql="from Transfer where tfout=? and time between ? and ? order by time desc";
		return (List<Transfer>)getHibernateTemplate().find(hql,account,start,end);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transfer> findNotSyncByUser(User user) 
	{
		List<Transfer> notSyncTransfers=new ArrayList<Transfer>();
		for(AccountBook accountBook: user.getAccountBooks())
		{
			List<Transfer> transfers=getHibernateTemplate().
					find("from Transfer where accountBook=? and sync=?",accountBook,SystemInit.NOT_SYNC);
			notSyncTransfers.addAll(transfers);
		}
		return notSyncTransfers;
	}

}
