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

import edu.kit.ActMgr.dao.RecordDao;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Classification;
import edu.kit.ActMgr.domain.Record;
import edu.kit.ActMgr.domain.Shop;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;
import edu.kit.common.hibernate3.support.SystemInit;
import edu.kit.common.util.DateTool;

public class RecordDaoHibernate extends AccountHibernateDaoSupport implements RecordDao
{

	@Override
	public Record get(Integer rid) 
	{
		return getHibernateTemplate().get(Record.class, rid);
	}

	@Override
	public Integer save(Record record) 
	{
		return (Integer)getHibernateTemplate().save(record);
	}

	@Override
	public void update(Record record) 
	{
		getHibernateTemplate().update(record);
	}

	@Override
	public void delete(Record record) 
	{
		getHibernateTemplate().delete(record);
	}

	@Override
	public void delete(Integer rid) 
	{
		getHibernateTemplate().delete(get(rid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Record> findAll() 
	{
		return (List<Record>)getHibernateTemplate().find("from Record");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Record> find(Date start,Date end,Classification classification, Account account,
			Shop shop, String remark, AccountBook accountBook) 
	{
		List<Object> values=new ArrayList<Object>();
		String hql="from Record where accountBook=? ";
		values.add(accountBook);
		if(classification!=null)
		{
			hql+=" and classification=? ";
			values.add(classification);
		}
		if(account!=null)
		{
			hql+=" and account=? ";
			values.add(account);
		}
		if(shop!=null)
		{
			hql+=" and shop=? ";
			values.add(shop);
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
		return (List<Record>)getHibernateTemplate().find(hql,args);
	}

	@Override
	public int getRecordSize(final Date start, final Date end, final AccountBook accountBook) 
	{
		final String hql="select count(*) from Record where accountBook=? and time between ? and ? order by time desc";
		int size=getHibernateTemplate().execute(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException
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
	public List<Record> findDay(Date date, AccountBook accountBook) 
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String day=df.format(date);
		Date start=DateTool.transferDate(day+" 00:00:00","yyyy-MM-dd hh:mm");
		Date end=DateTool.transferDate(day+" 23:59:59","yyyy-MM-dd hh:mm");
		String hql="from Record where accountBook=? and time between ? and ? order by time desc";
		return (List<Record>)getHibernateTemplate().find(hql,accountBook,start,end);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Record> findDay(Date date, Account account) 
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String day=df.format(date);
		Date start=DateTool.transferDate(day+" 00:00:00","yyyy-MM-dd hh:mm");
		Date end=DateTool.transferDate(day+" 23:59:59","yyyy-MM-dd hh:mm");
		String hql="from Record where account=? and time between ? and ? order by time desc";
		return (List<Record>)getHibernateTemplate().find(hql,account,start,end);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Record> findByAccountBook(AccountBook accountBook) 
	{
		String hql="from Record where accountBook=?";
		return getHibernateTemplate().find(hql, accountBook);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Record> findNotSyncByUser(User user) 
	{
		List<Record> notSyncRecords=new ArrayList<Record>();
		for(AccountBook accountBook: user.getAccountBooks())
		{
			List<Record> records=getHibernateTemplate().
					find("from Record where accountBook=? and sync=?",accountBook,SystemInit.NOT_SYNC);
			notSyncRecords.addAll(records);
		}
		return notSyncRecords;
	}

}
