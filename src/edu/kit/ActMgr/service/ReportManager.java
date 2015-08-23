package edu.kit.ActMgr.service;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.data.ProportionReportData;
import edu.kit.ActMgr.data.AssetsReportData;
import edu.kit.ActMgr.data.EarnSpendReportData;

public interface ReportManager
{
	public static final int YEAR_TYPE=2;
	public static final int MONTH_TYPE=1;
	public static final int DAY_TYPE=0;
	
	public static final String YEAR_FORMAT="yyyy";
	public static final String MONTH_FORMAT="yyyy-MM";
	public static final String DATE_FORMAT="yyyy-MM-dd";
	
	/**
	 * 获得收入支出报表数据
	 * @param cid 分类id
	 * @param aid 账户id
	 * @param sid 商家id
	 * @param type 查看时间类型
	 * @param start 起始时间
	 * @param end 结束时间
	 * @param session
	 * @return 收入支出报表数据
	 */
	EarnSpendReportData getEarnSpendReportData(int cid,int aid,int sid,int type,String start,String end,HttpSession session);
	
	/**
	 * 获得账户报表数据
	 * @param start 起始时间
	 * @param end 结束时间
	 * @param session
	 * @return 账户报表数据
	 */
	ProportionReportData getAccountReportData(String start,String end,HttpSession session);
	
	/**
	 * 获得账本资产报表数据
	 * @param start 起始时间
	 * @param end 结束时间
	 * @param session
	 * @return 资产报表数据
	 */
	AssetsReportData getAssetsReportData(String start,String end,int type,HttpSession session);

	/**
	 * 获得商家报表数据
	 * @param start 起始时间
	 * @param end 结束时间
	 * @param session
	 * @return 商家报表数据
	 */
	ProportionReportData getShopReportData(String start,String end,HttpSession session);
	
	/**
	 * 获得分类报表数据
	 * @param start 起始时间
	 * @param end 结束时间
	 * @param session
	 * @return 分类报表数据
	 */
	ProportionReportData getClassificationReportData(String start,String end,HttpSession session);
	
	/**
	 * 获得收支趋势图（本周）的数据
	 * @param session
	 * @return
	 */
	EarnSpendReportData getTrendGroupData(HttpSession session);
	
	/**
	 * 获得本周指出分类的数据
	 * @param session
	 * @return
	 */
	ProportionReportData getSpendClassificationGraphData(HttpSession session);
}
