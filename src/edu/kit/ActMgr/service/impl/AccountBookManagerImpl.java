package edu.kit.ActMgr.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.AccountBookBean;
import edu.kit.ActMgr.bean.UserBean;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Icon;
import edu.kit.ActMgr.domain.Record;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.AccountBookManager;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.ActMgr.servlet.UserServlet;
import edu.kit.common.hibernate3.support.SystemInit;
import edu.kit.common.util.DateTool;

public class AccountBookManagerImpl extends ManagerTemplate implements AccountBookManager
{

	@Override
	public int addAccountBook(String abname, int abiid,HttpSession session) 
	{
		UserBean userBean=UserServlet.getUser(session);
		if(userBean!=null)
		{
			User user=userDao.get(userBean.getUid());
			Icon abicon=iconDao.get(abiid);
			AccountBook accountBook=new AccountBook();
			accountBook.setSync(SystemInit.NOT_SYNC);
			accountBook.setUser(user);
			accountBook.setAbname(abname);
			accountBook.setAbicon(abicon);
			return accountBookDao.save(accountBook);
		}
		return -1;
	}
	
	@Override
	public int addAccountBookWithUid(int uid, String abname, int abiid,HttpSession session) 
	{
		User user=userDao.get(uid);
		Icon abicon=iconDao.get(abiid);
		AccountBook accountBook=new AccountBook();
		accountBook.setSync(SystemInit.NOT_SYNC);
		accountBook.setUser(user);
		accountBook.setAbname(abname);
		accountBook.setAbicon(abicon);
		int abid=accountBookDao.save(accountBook);
		if(abid>0)
		{
			accountBook=accountBookDao.get(abid);
			user.setUsingAccountBook(accountBook);
			userDao.update(user);
		}
		return abid;
	}
	
	@Override
	public void modifyAccountBook(int abid, String abname, int abiid,HttpSession session) 
	{
		if(UserServlet.getUser(session).getUid()==accountBookDao.get(abid).getUser().getUid())
		{
			
		}
	}
	
	@Override
	public void deleteAccountBook(int abid,HttpSession session) 
	{
		if(UserServlet.getUser(session).getUid()==accountBookDao.get(abid).getUser().getUid())
			accountBookDao.delete(abid);
	}

	@Override
	public List<AccountBookBean> getAccountBook( int pageNumber,int pageSize, HttpSession session)
	{
		User user=userDao.get(UserServlet.getUser(session).getUid());
		List<AccountBook> accountBooks=accountBookDao.findByPage(user, pageNumber, pageSize);
		List<AccountBookBean> accountBookBeans=new ArrayList<AccountBookBean>();
		for(AccountBook accountBook:accountBooks)
			accountBookBeans.add(new AccountBookBean(accountBook));
		return accountBookBeans;
	}

	@Override
	public boolean setUsingAccountBook(int abid,HttpSession session) 
	{
		int uid=UserServlet.getUser(session).getUid();
		User user=userDao.get(uid);
		AccountBook usingAccountBook=accountBookDao.get(abid);
		user.setUsingAccountBook(usingAccountBook);
		userDao.update(user);
		user=userDao.get(uid);
		if(user.getUsingAccountBook().getAbid()==abid)
			return true;
		else
			 return false;
	}

	@Override
	public int getAccountBookSize(HttpSession session) 
	{
		User user=userDao.get(UserServlet.getUser(session).getUid());
		return user.getAccountBooks().size();
	}

	@Override
	public AccountBookBean getUsingAccountBook(HttpSession session) 
	{
		AccountBook accountBook=getUsingAccountBookFromSession(session);
		return new AccountBookBean(accountBook);
	}

	@Override
	public List<AccountBookBean> getAccountBook(HttpSession session) 
	{
		User user=userDao.get(UserServlet.getUser(session).getUid());
		List<AccountBookBean> accountBooks=new ArrayList<AccountBookBean>();
		for(AccountBook accountBook:user.getAccountBooks())
			accountBooks.add(new AccountBookBean(accountBook));
		return accountBooks;
	}

	public int [] getAccountBookEarnSpendInfo(HttpSession session)
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		int [] info=new int[6];
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		List<Record> records=recordDao.find(DateTool.thisWeekStart(), DateTool.thisWeekEnd(), null, null, null, null, usingAccountBook);
		for(Record record:records)
		{
			if(record.getMoney()>0)
				info[0]+=record.getMoney();
			else
				info[1]-=record.getMoney();
		}
		Date monthStart=DateTool.getStart(year, month);
		Date monthEnd=DateTool.getEnd(year, month);
		records=recordDao.find(monthStart, monthEnd,null,null,null,null,usingAccountBook);
		for(Record record:records)
		{
			if(record.getMoney()>0)
				info[2]+=record.getMoney();
			else
				info[3]-=record.getMoney();
		}
		Date yearStart=DateTool.getStart(year);
		Date yearEnd=DateTool.getEnd(year);
		records=recordDao.find(yearStart, yearEnd,null,null,null,null,usingAccountBook);
		for(Record record:records)
		{
			if(record.getMoney()>0)
				info[4]+=record.getMoney();
			else
				info[5]-=record.getMoney();
		}
		return info;
	}

	@Override
	public AccountBookBean getAccountBookById(int abid, HttpSession session) 
	{
		AccountBook accountBook=accountBookDao.get(abid);
		if(UserServlet.getUser(session).getUid()==accountBook.getUser().getUid())
			return new AccountBookBean(accountBook);
		return null;
	}
}
