package edu.kit.ActMgr.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.IconBean;
import edu.kit.ActMgr.bean.SynchronizationHistoryBean;

public interface SetManager 
{
	/**
	 * 分页得到图标
	 * @param uid
	 * @param type
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	List<IconBean> getIcon(Integer uid,Integer type,int pageNumber,int pageSize);
	
	/**
	 * 得到图标总数
	 * @param uid
	 * @param type
	 * @return
	 */
	int getIconSize(Integer uid,Integer type);
	
	/**
	 * 根据用户Session得到当前用户的所有同步历史记录
	 * @param session
	 * @return
	 */
	List<SynchronizationHistoryBean> getSynchronizationHistories(HttpSession session);
}
