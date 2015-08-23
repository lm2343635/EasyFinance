package edu.kit.ActMgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ActMgr.dao.TemplateDao;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Template;
import edu.kit.ActMgr.domain.User;
import edu.kit.common.hibernate3.support.AccountHibernateDaoSupport;
import edu.kit.common.hibernate3.support.SystemInit;

public class TemplateDaoHibernate extends AccountHibernateDaoSupport implements TemplateDao 
{
	@Override
	public Template get(Integer tpid) 
	{
		return getHibernateTemplate().get(Template.class, tpid);
	}

	@Override
	public Integer save(Template template) 
	{
		return (Integer)getHibernateTemplate().save(template);
	}

	@Override
	public void update(Template template) 
	{
		getHibernateTemplate().update(template);
	}

	@Override
	public void delete(Template template) 
	{
		getHibernateTemplate().delete(template);
	}

	@Override
	public void delete(Integer tpid) 
	{
		getHibernateTemplate().delete(get(tpid));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Template> findAll() 
	{
		return (List<Template>)getHibernateTemplate().find("from Template");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Template> findByAccountBook(AccountBook accountBook) 
	{
		return (List<Template>)getHibernateTemplate().find("from Template where accountBook=?",accountBook);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Template> findNotSyncByUser(User user) 
	{
		List<Template> notSyncTemplates=new ArrayList<Template>();
		for(AccountBook accountBook: user.getAccountBooks())
		{
			List<Template> templates=getHibernateTemplate().
					find("from Template where accountBook=? and sync=?",accountBook,SystemInit.NOT_SYNC);
			notSyncTemplates.addAll(templates);
		}
		return notSyncTemplates;
	}

}
