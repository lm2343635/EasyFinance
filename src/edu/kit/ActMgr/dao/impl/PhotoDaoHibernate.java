package edu.kit.ActMgr.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.kit.ActMgr.dao.PhotoDao;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Photo;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;
import edu.kit.common.hibernate3.support.SystemInit;

public class PhotoDaoHibernate extends AccountHibernateDaoSupport implements PhotoDao
{

	@Override
	public Photo get(Integer pid) 
	{
		return getHibernateTemplate().get(Photo.class, pid);
	}

	@Override
	public Integer save(Photo photo) 
	{
		return (Integer)getHibernateTemplate().save(photo);
	}

	@Override
	public void update(Photo photo) 
	{
		getHibernateTemplate().update(photo);
	}

	@Override
	public void delete(Photo photo) 
	{
		getHibernateTemplate().delete(photo);
	}

	@Override
	public void delete(Integer pid) 
	{
		getHibernateTemplate().delete(get(pid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> findAll() 
	{
		return (List<Photo>)getHibernateTemplate().find("from Photo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> find(AccountBook accountBook, Date start, Date end,String pname) 
	{
		String hql="from Photo where accountBook=? ";
		List<Object> values=new ArrayList<Object>();
		values.add(accountBook);
		if(start!=null)
		{
			hql+=" and upload>=? ";
			values.add(start);
		}
		if(end!=null)
		{
			hql+=" and upload<=? ";
			values.add(end);
		}
		if(pname!=null)
		{
			hql+=" and pname like ?";
			values.add("%"+pname+"%");
		}
		Object [] args=new Object[values.size()];
		for(int i=0;i<values.size();i++)
			args[i]=values.get(i);
		return (List<Photo>)getHibernateTemplate().find(hql,args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> findNotSyncPhotoByUser(User user) 
	{
		List<Photo> notSyncPhotos=new ArrayList<Photo>();
		for(AccountBook accountBook: user.getAccountBooks())
		{
			List<Photo> photos=getHibernateTemplate().
					find("from Photo where accountBook=? and sync=?",accountBook,SystemInit.NOT_SYNC);
			notSyncPhotos.addAll(photos);
		}
		return notSyncPhotos;
	}

}
