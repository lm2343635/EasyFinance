package edu.kit.ActMgr.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.ShopBean;

public interface ShopManager 
{
	/**
	 * 新增商家
	 * @param sname 商家名称
	 * @param siid 图标id
	 * @return 商家id
	 */
	int addShop(String sname,int siid,HttpSession session);

	/**
	 * 删除商家
	 * @param sid 商家id
	 */
	void deleteShop(int sid,HttpSession session);

	/**
	 * 
	 * @param sid 商家id
	 * @param sname 商家名称
	 * @param siid 图标id
	 */
	void modifyShop(int sid,String sname,int siid,HttpSession session);
	
	/**
	 * 得到当前账本商家
	 * @param session
	 * @return
	 */
	List<ShopBean> getShop(HttpSession session);
	
	/**
	 * 根据商家id获取商家
	 * @param sid 商家id
	 * @return
	 */
	ShopBean getShopById(int sid,HttpSession session);
}
