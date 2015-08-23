package edu.kit.ActMgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.ClassificationBean;
import edu.kit.ActMgr.bean.UserBean;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Classification;
import edu.kit.ActMgr.domain.Icon;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.ClassificationManager;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.ActMgr.servlet.UserServlet;
import edu.kit.common.hibernate3.support.SystemInit;

public class ClassificationManagerImpl extends ManagerTemplate implements ClassificationManager 
{
	@Override
	public int addClassification(String cname, int ciid,HttpSession session)
	{
		UserBean userBean=UserServlet.getUser(session);
		if(userBean!=null)
		{
			User user=userDao.get(userBean.getUid());
			Icon cicon=iconDao.get(ciid);
			Classification classification=new Classification();
			classification.setSync(SystemInit.NOT_SYNC);
			classification.setCname(cname);
			classification.setCicon(cicon);
			classification.setAccountBook(user.getUsingAccountBook());
			classification.setCin(0.0);
			classification.setCout(0.0);
			return classificationDao.save(classification);
		}
		return -1;
	}

	@Override
	public void deleteClassification(int cid,HttpSession session)
	{
		if(getUsingAccountBookFromSession(session).getAbid()==classificationDao.get(cid).getAccountBook().getAbid())
			classificationDao.delete(cid);
	}
	
	@Override
	public void modifyClassification(int cid, String cname, int ciid,HttpSession session) 
	{
		if(getUsingAccountBookFromSession(session).getAbid()==classificationDao.get(cid).getAccountBook().getAbid())
		{
			
		}
	}

	@Override
	public int getClassificationSize(HttpSession session) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ClassificationBean> getClassification(HttpSession session) 
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		List<ClassificationBean> classifications=new ArrayList<ClassificationBean>();
		for(Classification classification:classificationDao.findByAccountBook(usingAccountBook))
			classifications.add(new ClassificationBean(classification));
		return classifications;
	}

	@Override
	public ClassificationBean getClassificationById(int cid,HttpSession session) 
	{
		Classification classification=classificationDao.get(cid);
		if(getUsingAccountBookFromSession(session).getAbid()==classification.getAccountBook().getAbid())
			return new ClassificationBean(classification);
		return null;
	}
}
