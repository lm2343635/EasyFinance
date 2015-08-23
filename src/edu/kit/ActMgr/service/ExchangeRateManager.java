package edu.kit.ActMgr.service;

import edu.kit.ActMgr.data.RateHistory;

public interface ExchangeRateManager 
{
	
	/**
	 * 得到当前支持的所有货币代码
	 * @return
	 */
	String [] getCurrencyCode();
	
	/**
	 * 搜索当前汇率
	 * @param from 本位币
	 * @param to 要转换的货币
	 * @return 结果数组
	 */
	double  search(String from,String to);
	
	RateHistory history(String from,String to,String start,String end);
}
