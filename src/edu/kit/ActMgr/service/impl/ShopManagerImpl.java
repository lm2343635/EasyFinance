package edu.kit.ActMgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.ShopBean;
import edu.kit.ActMgr.bean.UserBean;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Icon;
import edu.kit.ActMgr.domain.Shop;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.ShopManager;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.ActMgr.servlet.UserServlet;
import edu.kit.common.hibernate3.support.SystemInit;

public class ShopManagerImpl extends ManagerTemplate implements ShopManager
{
	@Override
	public int addShop(String sname, int siid,HttpSession session) 
	{
		UserBean userBean=UserServlet.getUser(session);
		if(userBean!=null)
		{
			User user=userDao.get(userBean.getUid());
			Icon sicon=iconDao.get(siid);
			Shop shop=new Shop();
			shop.setSync(SystemInit.NOT_SYNC);
			shop.setSname(sname);
			shop.setSicon(sicon);
			shop.setAccountBook(user.getUsingAccountBook());
			shop.setSin(0.0);
			shop.setSout(0.0);
			return shopDao.save(shop);
		}
		return -1;
	}

	@Override
	public void deleteShop(int sid,HttpSession session) 
	{
		if(getUsingAccountBookFromSession(session).getAbid()==shopDao.get(sid).getAccountBook().getAbid())
			shopDao.delete(sid);
	}

	@Override
	public void modifyShop(int sid, String sname, int siid,HttpSession session) 
	{
		if(getUsingAccountBookFromSession(session).getAbid()==shopDao.get(sid).getAccountBook().getAbid())
		{
			
		}
	}

	@Override
	public List<ShopBean> getShop(HttpSession session) 
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		List<ShopBean> shops=new ArrayList<ShopBean>();
		for(Shop shop:shopDao.findByAccountBook(usingAccountBook))
			shops.add(new ShopBean(shop));
		return shops;
	}

	@Override
	public ShopBean getShopById(int sid,HttpSession session) 
	{
		Shop shop=shopDao.get(sid);
		if(getUsingAccountBookFromSession(session).getAbid()==shop.getAccountBook().getAbid())
			return new ShopBean(shop);
		return null;
	}
}
