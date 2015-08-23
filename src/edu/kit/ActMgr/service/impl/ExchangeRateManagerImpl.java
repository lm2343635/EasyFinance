package edu.kit.ActMgr.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.kit.ActMgr.data.RateHistory;
import edu.kit.ActMgr.service.ExchangeRateManager;
import edu.kit.common.util.DateTool;
import edu.kit.common.util.ExchangeRate;

public class ExchangeRateManagerImpl implements ExchangeRateManager
{

	@Override
	public String[] getCurrencyCode() 
	{
		return ExchangeRate.CURRENCY;
	}

	@Override
	public double search(String from, String to) 
	{
		return ExchangeRate.exchage(from, to);
	}

	@Override
	public RateHistory history(String from,String to,String start,String end) 
	{
		int fy=0,fm=0,fd=0,ly=0,lm=0,ld=0;
		Date startDate = null,last = null;
		List<Double> data=new ArrayList<Double>();
		boolean isFirst=true;
		RateHistory history= new RateHistory();
		int frequency=3*365;
		//得到搜索次数
		int cycles=(DateTool.daysBetween(start, end)-3)/frequency+1;
		Date searchStartDate = null,searchEndDate = null;
		for(int i=0;i<cycles;i++)
		{
			//搜索开始时间赋值
			//如果是首次搜索，直接将起始日赋给搜索开始时间
			if(i==0)
				searchStartDate=DateTool.transferDate(start, DateTool.YEAR_MONTH_DATE_FORMAT);
			//否则在前一次的搜索结束时间的基础上加一天
			else
				searchStartDate=DateTool.nextDay(searchEndDate);
			//搜索结束时间赋值
			//如果是最后一次搜索，直接将结束日赋给搜索结束时间
			if(i==cycles-1)
				searchEndDate=DateTool.transferDate(end, DateTool.YEAR_MONTH_DATE_FORMAT);
			//否则在搜索开始时间的基础上加一个查询频率3年
			else
				searchEndDate=DateTool.nextDay(searchStartDate, frequency);
			//提交搜索请求
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(searchStartDate);
			fd=calendar.get(Calendar.DAY_OF_MONTH);
			fm=calendar.get(Calendar.MONTH)+1;
			fy=calendar.get(Calendar.YEAR);
			calendar.setTime(searchEndDate);
			ld=calendar.get(Calendar.DAY_OF_MONTH);
			lm=calendar.get(Calendar.MONTH)+1;
			ly=calendar.get(Calendar.YEAR);
			Map<Date, Double> rate=ExchangeRate.history(from, to, "", "daily", fd, fm, fy, ld, lm, ly);	
			int between;
			for(Date date:rate.keySet())
			{
				if(isFirst)
				{
					startDate=last=date;
					isFirst=false;
				}
				else
				{
					between=DateTool.daysBetween(last, date);
					for(int j=0;j<between-1;j++)
						data.add(data.get(data.size()-1));
				}
				data.add(rate.get(date));
				last=date;
			}
		}
		history.setStart(startDate);
		history.setData(data);
		return history;
	}

}
