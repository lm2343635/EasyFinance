package edu.kit.ActMgr.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.data.AssetsReportData;
import edu.kit.ActMgr.data.DateRecord;
import edu.kit.ActMgr.data.EarnSpendReportData;
import edu.kit.ActMgr.data.ProportionReportData;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.AccountHistory;
import edu.kit.ActMgr.domain.Classification;
import edu.kit.ActMgr.domain.Record;
import edu.kit.ActMgr.domain.Shop;
import edu.kit.ActMgr.domain.Transfer;
import edu.kit.ActMgr.service.ReportManager;
import edu.kit.ActMgr.service.util.ClassifyUtil;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.common.util.DateTool;

public class ReportManagerImpl extends ManagerTemplate implements ReportManager
{
	
	@Override
	public EarnSpendReportData getEarnSpendReportData(int cid, int aid,int sid, int type, String start, String end,HttpSession session) 
	{
		int year,num = 0,startIndex = 0;
		Date startDate = null,endDate = null;
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		String [] starts=start.split("-");
		String [] ends=end.split("-");
		switch (type)
		{
		case DAY_TYPE:
			year=Integer.parseInt(starts[0]);
			int month=Integer.parseInt(starts[1]);
			int startDay=Integer.parseInt(starts[2]);
			int endDay=Integer.parseInt(ends[2]);
			num=endDay-startDay+1;
			startIndex=startDay;
			startDate=DateTool.getStart(year,month,startDay);
			endDate=DateTool.getEnd(year,month,endDay);
			break;
		case MONTH_TYPE:
			year=Integer.parseInt(starts[0]);
			int startMonth=Integer.parseInt(starts[1]);
			int endMonth=Integer.parseInt(ends[1]);
			num=endMonth-startMonth+1;
			startIndex=startMonth;
			startDate=DateTool.getStart(year,startMonth);
			endDate=DateTool.getEnd(year,endMonth);
			break;
		case YEAR_TYPE:
			int startYear=Integer.parseInt(start);
			int endYear=Integer.parseInt(end);
			num=endYear-startYear+1;
			startIndex=startYear;
			startDate=DateTool.getStart(startYear);
			endDate=DateTool.getEnd(endYear);
			break;
		default:
			break;
		}
		List<Record> records=recordDao.find(startDate, endDate, null, null	, null, null, usingAccountBook);
		List<DateRecord> dateRecords=ClassifyUtil.classifyRecord(records,type);
		double [] earnData=new double[num];
		double [] spendData=new double[num];
		String [] categories=new String[num];
		for(int i=0;i<num;i++)
		{
			earnData[i]=0;
			spendData[i]=0;
			categories[i]=DateTool.getDateStr(i+startIndex);
		}
		for(DateRecord dateRecord:dateRecords)
		{
			int index=Integer.parseInt(dateRecord.getDate())-startIndex;
			earnData[index]=dateRecord.getEarn();
			spendData[index]=Math.abs(dateRecord.getSpend());
		}
		String title="Earn and Spend from "+start+" to "+end;
		String subtitle="All Classification / All Account / All Shop";
		return new EarnSpendReportData(title, subtitle, categories, earnData, spendData);
	}

	@Override
	public ProportionReportData getAccountReportData(String start,String end, HttpSession session) 
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		Date startDate=DateTool.transferDate(start+" 00:00:00", "yyyy-MM-dd hh:mm");
		Date endDate=DateTool.transferDate(end+" 23:59:59", "yyyy-MM-dd hh:mm");
		//开始逐个账户统计
		List<Account> accounts=accountDao.findByAccountBook(usingAccountBook);
		String [] categories=new String[accounts.size()];
		double [] inflows=new double[accounts.size()];
		double [] outflows=new double[accounts.size()];
		int count=0;
		for(Account account:accounts)
		{
			int inflow=0;
			int outflow=0;
			//统计收入支出记录
			List<Record> records=recordDao.find(startDate, endDate, null, account, null, null, usingAccountBook);
			for(Record record:records)
			{
				if(record.getMoney()>0)
					inflow+=record.getMoney();
				else
					outflow+=Math.abs(record.getMoney());
			}
			//统计该账户作为转入账户的转账记录
			List<Transfer> inTransfers=transferDao.find(startDate, endDate, account, null, null, usingAccountBook);
			for(Transfer transfer:inTransfers)
				inflow+=transfer.getMoney();
			//统计该账户作为转出账户的转账记录
			List<Transfer> outTransfers=transferDao.find(startDate, startDate, null, account, null, usingAccountBook);
			for(Transfer transfer:outTransfers)
				outflow+=transfer.getMoney();
			inflows[count]=inflow;
			outflows[count]=outflow;
			categories[count]=account.getAname();
			count++;
		}
		String subtitle="From "+start+" to "+end;
		return new ProportionReportData(subtitle, categories, inflows, outflows);
	}

	@Override
	public ProportionReportData getShopReportData(String start, String end, HttpSession session) 
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		Date startDate=DateTool.transferDate(start+" 00:00:00", "yyyy-MM-dd hh:mm");
		Date endDate=DateTool.transferDate(end+" 23:59:59", "yyyy-MM-dd hh:mm");
		List<Shop> shops=shopDao.findByAccountBook(usingAccountBook);
		String [] categories=new String[shops.size()];
		double [] inflows=new double[shops.size()];
		double [] outflows=new double[shops.size()];
		int count=0;
		for(Shop shop:shops)
		{
			int inflow=0;
			int outflow=0;
			List<Record> records=recordDao.find(startDate, endDate, null, null, shop, null, usingAccountBook);
			for(Record record:records)
			{
				if(record.getMoney()>0)
					inflow+=record.getMoney();
				else
					outflow+=Math.abs(record.getMoney());
			}
			inflows[count]=inflow;
			outflows[count]=outflow;
			categories[count]=shop.getSname();
			count++;
		}
		String subtitle="From "+start+" to "+end;
		return new ProportionReportData(subtitle, categories, inflows, outflows);
	}

	@Override
	public ProportionReportData getClassificationReportData(String start,String end, HttpSession session) 
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		Date startDate=DateTool.transferDate(start+" 00:00:00", "yyyy-MM-dd hh:mm");
		Date endDate=DateTool.transferDate(end+" 23:59:59", "yyyy-MM-dd hh:mm");
		List<Classification> classifications=classificationDao.findByAccountBook(usingAccountBook);
		String [] categories=new String[classifications.size()];
		double [] inflows=new double[classifications.size()];
		double [] outflows=new double[classifications.size()];
		int count=0;
		for(Classification classification:classifications)
		{
			int inflow=0;
			int outflow=0;
			List<Record> records=recordDao.find(startDate, endDate, classification, null, null, null, usingAccountBook);
			for(Record record:records)
			{
				if(record.getMoney()>0)
					inflow+=record.getMoney();
				else
					outflow+=Math.abs(record.getMoney());
			}
			inflows[count]=inflow;
			outflows[count]=outflow;
			categories[count]=classification.getCname();
			count++;
		}
		String subtitle="From "+start+" to "+end;
		return new ProportionReportData(subtitle, categories, inflows, outflows);
	}
	
	@Override
	public EarnSpendReportData getTrendGroupData(HttpSession session) 
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		Date date=DateTool.thisWeekStart();
		int WEEK_DAY=0;
		double [] earnData=new double[7];
		double [] spendData=new double[7];
		String [] categories=new String[7];
		while(WEEK_DAY<7)
		{
			List<Record> records=recordDao.findDay(date, usingAccountBook);
			for(Record record:records)
			{
				if(record.getMoney()>0)
					earnData[WEEK_DAY]+=record.getMoney();
				else
					spendData[WEEK_DAY]-=record.getMoney();
			}
			categories[WEEK_DAY]=DateTool.formatDate(date, "MM/dd");
			date=DateTool.nextDay(date);
			WEEK_DAY++;
		}
		return new EarnSpendReportData(null,null, categories, earnData, spendData);
	}

	@Override
	public ProportionReportData getSpendClassificationGraphData(HttpSession session) 
	{
		String start=DateTool.formatDate(DateTool.thisWeekStart(), "yyyy-MM-dd");
		String end=DateTool.formatDate(DateTool.thisWeekEnd(), "yyyy-MM-dd");
		return getClassificationReportData(start, end, session);
	}

	@Override
	public AssetsReportData getAssetsReportData(String start,String end,int type,HttpSession session) 
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		List<Account> accounts=accountDao.findByAccountBook(usingAccountBook);
		AssetsReportData data=null;
		switch (type)
		{
		case DAY_TYPE:
			Date date=DateTool.transferDate(start, DATE_FORMAT);
			int num=Integer.parseInt(end.split("-")[2])-Integer.parseInt(start.split("-")[2])+1;
			String [] categories=new String[num];
			double [] netAssets=new double[num];
			double [] totalAssets=new double[num];
			double [] totalLiabilities=new double[num];
			for(int i=0;i<num;i++)
			{
				double totalAsset=0;
				double totalLiability=0;
				for(Account account:accounts)
				{
					AccountHistory history=accountHistoryDao.forcedFind(account, date);	
					if(history!=null)
					{
						double surplus=history.getAin()-history.getAout();
						if(surplus<0)
							totalLiability+=surplus;
						else
							totalAsset+=surplus;
					}
				}
				totalAssets[i]=totalAsset;
				totalLiabilities[i]=totalLiability;
				netAssets[i]=totalAsset+totalLiability;
				categories[i]=DateTool.formatDate(date, "dd");
				date=DateTool.nextDay(date);
			}
			data=new AssetsReportData(categories, netAssets, totalAssets, totalLiabilities);
			break;
		case MONTH_TYPE:
			
			break;
		case YEAR_TYPE:
			
			break;
		default:
			break;
		}
		return data;
	}
}