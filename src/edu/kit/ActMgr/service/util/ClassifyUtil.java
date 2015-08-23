package edu.kit.ActMgr.service.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.kit.ActMgr.bean.RecordBean;
import edu.kit.ActMgr.data.DateRecord;
import edu.kit.ActMgr.domain.Record;
import edu.kit.common.util.DateTool;

public class ClassifyUtil 
{
	public static final int YEAR_TYPE=2;
	public static final int MONTH_TYPE=1;
	public static final int DAY_TYPE=0;
	
	private static String getDateStr(Date date,int type)
	{
		String format="";
		switch (type)
		{
		case YEAR_TYPE:
			format="yyyy";
			break;
		case MONTH_TYPE:
			format="MM";
			break;
		case DAY_TYPE:
			format="dd";
			break;
		default:
			break;
		}
		SimpleDateFormat df=new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**
	 * 将earn/spend记录按日期类型分类
	 * @param records 记录
	 * @param type 分类类型 年：2 月：1 日：0
	 * @return
	 */
	public static List<DateRecord> classifyRecord(List<Record> records,int type)
	{
		List<RecordBean> recordBeans=new ArrayList<RecordBean>();
		List<DateRecord> dateRecords=new ArrayList<DateRecord>();
		Date time=null;
		int count=0;
		double spend=0,earn=0;
		for(Record record:records)
		{
			count++;
			if(time==null)
				time=record.getTime();
			boolean same = false;
			switch (type)
			{
			case YEAR_TYPE:
				same=DateTool.isSameYear(time, record.getTime());
				break;
			case MONTH_TYPE:
				same=DateTool.isSameMonth(time, record.getTime());
				break;
			case DAY_TYPE:
				same=DateTool.isSameDay(time, record.getTime());
				break;
			default:
				break;
			}
			if(!same)
			{
				dateRecords.add(new DateRecord(getDateStr(recordBeans.get(0).getTime(),type),recordBeans,spend,earn));
				recordBeans=new ArrayList<RecordBean>();
				time=record.getTime();		
				spend=earn=0;
			}
			recordBeans.add(new RecordBean(record));
			if(record.getMoney()>0)
				earn+=record.getMoney();
			else
				spend+=record.getMoney();
			if(count==records.size())
				dateRecords.add(new DateRecord(getDateStr(recordBeans.get(0).getTime(),type),recordBeans,spend,earn));
		}
		return dateRecords;
	}
}
