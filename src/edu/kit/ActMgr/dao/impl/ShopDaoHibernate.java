package edu.kit.ActMgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ActMgr.dao.ShopDao;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Shop;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;
import edu.kit.common.hibernate3.support.SystemInit;

public class ShopDaoHibernate extends AccountHibernateDaoSupport implements ShopDao 
{

	@Override
	public Shop get(Integer sid) 
	{
		return getHibernateTemplate().get(Shop.class, sid);
	}

	@Override
	public Integer save(Shop shop) 
	{
		return (Integer)getHibernateTemplate().save(shop);
	}

	@Override
	public void update(Shop shop) 
	{
		getHibernateTemplate().update(shop);
	}

	@Override
	public void delete(Shop shop) 
	{
		getHibernateTemplate().delete(shop);
	}

	@Override
	public void delete(Integer sid) 
	{
		getHibernateTemplate().delete(get(sid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Shop> findAll() 
	{
		return (List<Shop>)getHibernateTemplate().find("from Shop");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Shop> findByAccountBook(AccountBook accountBook) 
	{
		return (List<Shop>)getHibernateTemplate().find("from Shop where accountBook=?",accountBook);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Shop> findNotSyncByUser(User user) 
	{
		List<Shop> notSyncShops=new ArrayList<Shop>();
		for(AccountBook accountBook:user.getAccountBooks())
		{
			List<Shop> shops=getHibernateTemplate().
					find("from Shop where accountBook=? and sync=?",accountBook,SystemInit.NOT_SYNC);
			notSyncShops.addAll(shops);
		}
		return notSyncShops;
	}

}
