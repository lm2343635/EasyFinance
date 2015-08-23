package edu.kit.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool
{
	//“年-月-日”格式
	public static final String YEAR_MONTH_DATE_FORMAT="yyyy-MM-dd";
	//“年-月”格式
	public static final String YEAR_MONTH_FORMAT="yyyy-MM";
	//“年”格式
	public static final String YEAR_FORMAT="yyyy";
	//“年-月-日 时:分”格式
	public static final String DATE_HOUR_MINUTE_FORMAT="yyyy-MM-dd hh:mm";
	
	//一年中每个月的天数
	public static int [] MonthDay={31,28,31,30,31,30,31,31,30,31,30,31};
	
	/**
	 * 得到截止日期xxxx-xx-xx xx:xx相对1969-12-01 00:00的分钟数
	 * @param deadline 截止日期
	 * @return 分钟数
	 */
	public static int getTimeInMinute(String deadline)
	{
		String [] datebuff=deadline.split(" ")[0].split("-");
		String [] timebuff=deadline.split(" ")[1].split(":");
		int year=Integer.parseInt(datebuff[0]);
		int month=Integer.parseInt(datebuff[1]);
		int day=Integer.parseInt(datebuff[2]);
		int hour=Integer.parseInt(timebuff[0]);
		int minute=Integer.parseInt(timebuff[1]);
		Calendar calendar=Calendar.getInstance();
		calendar.set(year, month, day, hour, minute);
		long time=calendar.getTimeInMillis()/1000/60;
		return (int)time;
	}
	
	/**
	 * 将字符串转换为Date对象
	 * @param time 时间字符串
	 * @param format 转换格式
	 * @return Date对象
	 */
	public static Date transferDate(String time,String format)
	{
		try 
		{
			SimpleDateFormat df=new SimpleDateFormat(format);
			return df.parse(time);
		}
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将Date对象格式化为字符串
	 * @param date 时间Date对象
	 * @param format 转换格式
	 * @return 时间字符串
	 */
	public static String formatDate(Date date,String format)
	{
		SimpleDateFormat df=new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**  
    * 计算两个日期之间相差的天数  
    * @param start 较小的时间 
    * @param end  较大的时间 
    * @return 相差天数 
    */    
	public static int daysBetween(Date start,Date end) 
	{
		SimpleDateFormat sdf=new SimpleDateFormat(YEAR_MONTH_DATE_FORMAT);
		try
		{
			start=sdf.parse(sdf.format(start));  
			end=sdf.parse(sdf.format(end));  
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return 0;
		}
	    Calendar cal = Calendar.getInstance();    
	    cal.setTime(start);    
	    long time1 = cal.getTimeInMillis();                 
	    cal.setTime(end);    
	    long time2 = cal.getTimeInMillis();         
	    long between_days=(time2-time1)/(1000*3600*24);  
	    return Integer.parseInt(String.valueOf(between_days));  
	}
	
	/**
	 * 计算两个日期之间相差的天数  
    * @param start 较小的时间 
    * @param end  较大的时间 
    * @return 相差天数 
	 */
	public static int daysBetween(String start,String end)
	{
		return daysBetween(transferDate(start, YEAR_MONTH_DATE_FORMAT), transferDate(end, YEAR_MONTH_DATE_FORMAT));
	}
	
	/**
	 * d1的下一天是d2吗
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isNextPDay(Date d1,Date d2)
	{
		return isSameDay(nextDay(d1), d2);
	}
     
	/**
	 * 判断两个日期是否为同一日
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameDay(Date d1,Date d2)
	{
		 SimpleDateFormat sf = new SimpleDateFormat(YEAR_MONTH_DATE_FORMAT);  
		 if(sf.format(d1).equals(sf.format(d2)))
			 return true;
		 return false;
	}
	
	/**
	 * 判断两个日期是否为同一月
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameMonth(Date d1,Date d2)
	{
		 SimpleDateFormat sf = new SimpleDateFormat(YEAR_MONTH_FORMAT);  
		 if(sf.format(d1).equals(sf.format(d2)))
			 return true;
		 return false;
	}
	
	/**
	 * 判断两个日期是否为同一年
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameYear(Date d1,Date d2)
	{
		 SimpleDateFormat sf = new SimpleDateFormat(YEAR_FORMAT);  
		 if(sf.format(d1).equals(sf.format(d2)))
			 return true;
		 return false;
	}

	/**
	 * 判断是否为闰年
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year)
	{
		if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
			return true;
		return false;
	}
	
	/**
	 * 得到结束时间点
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getEnd(int year, int month) 
	{
		int day=MonthDay[month-1];
		if(isLeapYear(year)&&month==2)
			day++;
		String time=year+"-"+month+"-"+day+" 23:59:59";
		return transferDate(time,DATE_HOUR_MINUTE_FORMAT);
	}
	
	/**
	 * 得到结束时间点
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getEnd(int year, int month,int day) 
	{
		String time=year+"-"+month+"-"+day+" 23:59:59";
		return transferDate(time,DATE_HOUR_MINUTE_FORMAT);
	}
	
	/**
	 * 得到结束时间点
	 * @param year
	 * @param month
	 * @param day
	 * @param week
	 * @return
	 */
	public static Date getEnd(int year, int month,int day,int week) 
	{
		int endDay=(day+7-week);
		String time=year+"-"+month+"-"+endDay+" 23:59:59";
		return transferDate(time,DATE_HOUR_MINUTE_FORMAT);
	}
	
	/**
	 * 得到结束时间点
	 * @param year
	 * @return
	 */
	public static Date getEnd(int year) 
	{
		String time=year+"-12-31 23:59:59";
		return transferDate(time,DATE_HOUR_MINUTE_FORMAT);
	}

	/**
	 * 得到开始时间点
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getStart(int year, int month,int day) 
	{
		String time=year+"-"+month+"-"+day+" 00:00:00";
		return transferDate(time,DATE_HOUR_MINUTE_FORMAT);
	}
	
	/**
	 * 得到开始时间点
	 * @param year
	 * @param month
	 * @param day
	 * @param week
	 * @return
	 */
	public static Date getStart(int year, int month,int day,int week) 
	{
		int startDay=day-week+1;
		if(startDay<1)
		{
			month--;
			if(month<1)
			{
				month+=12;
				year--;
			}
			startDay+=getMonthDay(year)[month-1];
		}
		String time=year+"-"+month+"-"+startDay+" 00:00:00";
		return transferDate(time,DATE_HOUR_MINUTE_FORMAT);
	}
	
	/**
	 * 得到本周开始时间
	 * @return
	 */
	public static Date thisWeekStart()
	{
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		int day=calendar.get(Calendar.DATE);
		int week=calendar.get(Calendar.DAY_OF_WEEK);
		return getStart(year, month, day, week);
	}
	
	/**
	 * 得到本周结束时间
	 * @return
	 */
	public static Date thisWeekEnd()
	{
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		int day=calendar.get(Calendar.DATE);
		int week=calendar.get(Calendar.DAY_OF_WEEK);
		return getEnd(year, month, day, week);
	}
	
	/**
	 * 得到开始时间点
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getStart(int year, int month) 
	{
		String time=year+"-"+month+"-01 00:00:00";
		return transferDate(time,DATE_HOUR_MINUTE_FORMAT);
	}
	
	/**
	 * 得到开始时间点
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getStart(int year) 
	{
		String time=year+"-01-01 00:00:00";
		return transferDate(time,DATE_HOUR_MINUTE_FORMAT);
	}
	
	/**
	 * 得到每个月的天数
	 * @param year
	 * @return
	 */
	public static int [] getMonthDay(int year)
	{
		int [] monthDay=MonthDay;
		if(isLeapYear(year))
			monthDay[1]++;
		return monthDay;
	}
	
	/**
	 * 按日期数字得到日期字符串 添加0
	 * @param date
	 * @return
	 */
	public static String getDateStr(int date)
	{
		if(date<10)
			return "0"+date;
		return date+"";
	}
	
	/**
	 * 得到下一天的日期
	 * @param date 指定日期
	 * @return
	 */
	public static Date nextDay(Date date)
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
		return calendar.getTime();
	}
	
	/**
	 * 得到指定天数后的日期
	 * @param date 指定日期
	 * @param days 指定天数
	 * @return
	 */
	public static Date nextDay(Date date,int days)
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+days);
		return calendar.getTime();
	}
	
}
