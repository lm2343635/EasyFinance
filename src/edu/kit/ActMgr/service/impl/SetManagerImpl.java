package edu.kit.ActMgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.IconBean;
import edu.kit.ActMgr.bean.SynchronizationHistoryBean;
import edu.kit.ActMgr.domain.Icon;
import edu.kit.ActMgr.domain.SynchronizationHistory;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.SetManager;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.ActMgr.servlet.UserServlet;

public class SetManagerImpl extends ManagerTemplate implements SetManager 
{
	@Override
	public List<IconBean> getIcon(Integer uid, Integer type, int pageNumber,int pageSize) 
	{
		User user=userDao.get(uid);
		List<Icon> icons=iconDao.findByType(user, type, pageNumber, pageSize);
		List<IconBean> iconBeans=new ArrayList<IconBean>();
		for(Icon icon:icons)
			iconBeans.add(new IconBean(icon));
		return iconBeans;
	}

	@Override
	public int getIconSize(Integer uid, Integer type) 
	{
		User user=userDao.get(uid);
		return iconDao.getIconSize(user, type);
	}

	@Override
	public List<SynchronizationHistoryBean> getSynchronizationHistories(HttpSession session) 
	{
		int uid=UserServlet.getUser(session).getUid();
		User user=userDao.get(uid);
		List<SynchronizationHistoryBean> synchronizationHistories=new ArrayList<SynchronizationHistoryBean>();
		for(SynchronizationHistory synchronizationHistory: synchronizationHistoryDao.findByUser(user))
			synchronizationHistories.add(new SynchronizationHistoryBean(synchronizationHistory));
		return synchronizationHistories;
	}

}