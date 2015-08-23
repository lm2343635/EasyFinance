package edu.kit.ActMgr.dao;

import java.util.List;

import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Template;
import edu.kit.ActMgr.domain.User;

public interface TemplateDao 
{
	Template get(Integer tpid);
	Integer save(Template template);
	void update(Template template);
	void delete(Template template);
	void delete(Integer tpid);
	List<Template> findAll();
	
	/**
	 * 根据账本查找模板
	 * @return
	 */
	List<Template> findByAccountBook(AccountBook accountBook);
	
	/**
	 * 查询指定用户的未同步模板
	 * @param user
	 * @return
	 */
	List<Template> findNotSyncByUser(User user);
}
