package edu.kit.ActMgr.service.impl;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.UserBean;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.UserManager;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.ActMgr.servlet.UserServlet;
import edu.kit.common.hibernate3.support.SystemInit;

public class UserManagerImpl extends ManagerTemplate implements UserManager 
{
	@Override
	public int register(String uname, String email, String password) 
	{
		User user=new User();
		user.setUname(uname);
		user.setEmail(email);
		user.setPassword(password);
		user.setPhoto(photoDao.get(SystemInit.SYS_NULL_ID));
		return userDao.save(user);
	}

	@Override
	public boolean isEmailExist(String email) 
	{
		User user=userDao.findByEmail(email);
		if(user!=null)
			return true;
		return false;
	}

	@Override
	public int login(String email, String password,HttpSession session) 
	{
		User user=userDao.findByEmail(email);
		if(user!=null&&user.getPassword().equals(password))
		{
			UserBean userBean=new UserBean(user);
			UserServlet.addUserSession(session, userBean);
			if(user.getUsingAccountBook()==null)
				return NO_USING_ACCOUNT_BOOK;
			return LOGIN_SUCCESS;
		}
		return PASSWORD_WRONG;
	}

	@Override
	public boolean isPasswordRight(String password, HttpSession session) 
	{
		User user=userDao.get(UserServlet.getUser(session).getUid());
		if(user!=null)
		{
			if(user.getPassword().equals(password))
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public boolean modifyPassword(String password, HttpSession session) 
	{
		int uid=UserServlet.getUser(session).getUid();
		User user=userDao.get(uid);
		if(user!=null)
		{
			user.setPassword(password);
			userDao.update(user);
			if(userDao.get(uid).getPassword().equals(password))
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public UserBean getUser(HttpSession session) 
	{
		return UserServlet.getUser(session);
	}

	@Override
	public boolean checkSession(HttpSession session) 
	{
		if(UserServlet.getUser(session)==null)
			return false;
		return true; 
	}

	@Override
	public boolean refreshSession(HttpSession session) 
	{
		int uid=UserServlet.getUser(session).getUid();
		User user=userDao.get(uid);
		if(user!=null)
		{
			UserBean userBean=new UserBean(user);
			UserServlet.replaceUserSession(session, userBean);
			return true;
		}
		return false;
	}

	@Override
	public int iOSLogin(String email, String password) 
	{
		User user=userDao.findByEmail(email);
		if(user!=null&&user.getPassword().equals(password))
		{
			if(user.getUsingAccountBook()==null)
				return NO_USING_ACCOUNT_BOOK;
			return LOGIN_SUCCESS;
		}
		return PASSWORD_WRONG;
	}

}
