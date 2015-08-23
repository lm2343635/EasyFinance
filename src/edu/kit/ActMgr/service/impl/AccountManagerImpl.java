package edu.kit.ActMgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.AccountBean;
import edu.kit.ActMgr.bean.UserBean;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Icon;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.AccountManager;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.ActMgr.servlet.UserServlet;
import edu.kit.common.hibernate3.support.SystemInit;

public class AccountManagerImpl extends ManagerTemplate implements AccountManager 
{
	@Override
	public int addAccount(String aname, int siid, double assets,HttpSession session) 
	{
		UserBean userBean=UserServlet.getUser(session);
		if(userBean!=null)
		{
			User user=userDao.get(userBean.getUid());
			Icon aicon=iconDao.get(siid);
			Account account=new Account();
			account.setSync(SystemInit.NOT_SYNC);
			account.setAname(aname);
			account.setAicon(aicon);
			account.setAccountBook(user.getUsingAccountBook());
			account.setAout(0.0);
			account.setAin(assets);
			return accountDao.save(account);
		}
		return -1;
	}

	@Override
	public void deleteAccount(int aid,HttpSession session) 
	{
		if(getUsingAccountBookFromSession(session).getAbid()==accountDao.get(aid).getAccountBook().getAbid())
			accountDao.delete(aid);
	}

	@Override
	public void modifyAccount(int aid, String aname, int aiid,double asset,HttpSession session) 
	{
		if(getUsingAccountBookFromSession(session).getAbid()==accountDao.get(aid).getAccountBook().getAbid())
		{
			
		}
	}

	@Override
	public List<AccountBean> getAccount(HttpSession session) 
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		List<AccountBean> accounts=new ArrayList<AccountBean>();
		for(Account account:accountDao.findByAccountBook(usingAccountBook))
			accounts.add(new AccountBean(account));
		return accounts;
	}

	@Override
	public AccountBean getAccountById(int aid,HttpSession session) 
	{
		Account account=accountDao.get(aid);
		if(getUsingAccountBookFromSession(session).getAbid()==account.getAccountBook().getAbid())
			return new AccountBean(account);
		return null;
	}

	@Override
	public double[] getAccountCenterInfo(HttpSession session) 
	{
		double totalAssets=0;
		double totalLiabitities=0;
		double netAssets=0;
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		for(Account account:accountDao.findByAccountBook(usingAccountBook))
		{
			double surplus=account.getAin()-account.getAout();
			if(surplus<0)
				totalLiabitities+=surplus;
			else
				netAssets+=surplus;
			totalAssets+=surplus;
		}
		return new double[]{totalAssets,totalLiabitities,netAssets};
	}
}
