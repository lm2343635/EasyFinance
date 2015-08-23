package edu.kit.ActMgr.dao;

import java.util.List;

import edu.kit.ActMgr.domain.SynchronizationHistory;
import edu.kit.ActMgr.domain.User;

public interface SynchronizationHistoryDao 
{
	SynchronizationHistory get(Integer shid);
	Integer save(SynchronizationHistory synchronizationHistory);
	void update(SynchronizationHistory synchronizationHistory);
	void delete(SynchronizationHistory synchronizationHistory);
	void delete(Integer shid);
	List<SynchronizationHistory> findAll();

	/**
	 * 通过用户查找同步历史用户
	 * @param user 指定用户
	 * @return
	 */
	List<SynchronizationHistory> findByUser(User user);
}
