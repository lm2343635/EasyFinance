package edu.kit.ActMgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.TemplateBean;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Classification;
import edu.kit.ActMgr.domain.Shop;
import edu.kit.ActMgr.domain.Template;
import edu.kit.ActMgr.service.TemplateManager;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.common.hibernate3.support.SystemInit;

public class TemplateManagerImpl extends ManagerTemplate implements TemplateManager
{

	@Override
	public int addTemplate(String tpname, int cid, int aid, int sid,HttpSession session)
	{
		Template template=new Template();
		template.setSync(SystemInit.NOT_SYNC);
		template.setTpname(tpname);
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		template.setAccountBook(usingAccountBook);
		Classification classification=classificationDao.get(cid);
		template.setClassification(classification);
		Account account=accountDao.get(aid);
		template.setAccount(account);
		Shop shop=shopDao.get(sid);
		template.setShop(shop);
		return templateDao.save(template);
	}

	@Override
	public void deleteTemplate(int tpid,HttpSession session) 
	{
		if(getUsingAccountBookFromSession(session).getAbid()==templateDao.get(tpid).getAccountBook().getAbid())
			templateDao.delete(tpid);
	}

	@Override
	public List<TemplateBean> getTemplate(HttpSession session) 
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		List<TemplateBean> templates=new ArrayList<TemplateBean>();
		for(Template template:templateDao.findByAccountBook(usingAccountBook))
			templates.add(new TemplateBean(template));
		return templates;
	}

	@Override
	public TemplateBean getTemplateById(int tpid,HttpSession session) 
	{
		Template template=templateDao.get(tpid);
		return new TemplateBean(template);
	}

	@Override
	public void modifyTemplate(int tpid, String tpname, int cid, int aid,int sid, HttpSession session) 
	{
		if(getUsingAccountBookFromSession(session).getAbid()==templateDao.get(tpid).getAccountBook().getAbid())
		{
			
		}
	}

}
