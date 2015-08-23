package edu.kit.ActMgr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.RecordBean;
import edu.kit.ActMgr.bean.TransferBean;
import edu.kit.ActMgr.data.DateRecord;
import edu.kit.ActMgr.data.DateTransfer;
import edu.kit.ActMgr.data.TallyListTime;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Classification;
import edu.kit.ActMgr.domain.Photo;
import edu.kit.ActMgr.domain.Record;
import edu.kit.ActMgr.domain.Shop;
import edu.kit.ActMgr.domain.Transfer;
import edu.kit.ActMgr.service.AccountHistoryManager;
import edu.kit.ActMgr.service.TallyManager;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.common.hibernate3.support.SystemInit;
import edu.kit.common.util.DateTool;

public class TallyManagerImpl extends ManagerTemplate implements TallyManager 
{
	protected AccountHistoryManager accountHistoryManager;

	public void setAccountHistoryManager(AccountHistoryManager accountHistoryManager) 
	{
		this.accountHistoryManager = accountHistoryManager;
	}

	@Override
	public int addSpend(int cid, int aid, int sid, String time,double money, String remark,int pid,HttpSession session)
	{
		Record record=new Record();
		record.setSync(SystemInit.NOT_SYNC);
		Classification classification=classificationDao.get(cid);
		if(cid!=SystemInit.SYS_NULL_ID)
			classification.setCout(classification.getCout()+money);
		record.setClassification(classification);		
		Shop shop=shopDao.get(sid);
		if(sid!=SystemInit.SYS_NULL_ID)
			shop.setSout(shop.getSout()+money);
		record.setShop(shop);
		Account account=accountDao.get(aid);
		if(aid!=SystemInit.SYS_NULL_ID)
		{
			accountHistoryManager.update(account, DateTool.transferDate(time.split(" ")[0], DateTool.YEAR_MONTH_DATE_FORMAT), -money);
			account.setAout(account.getAout()+money);
		}
		record.setAccount(account);
		record.setTime(DateTool.transferDate(time,"yyyy-MM-dd hh:mm"));
		record.setMoney(-money);
		record.setRemark(remark);
		/**
		 * 这个地方为啥要这么写呢，你想想不这么写直接两条set语句也行吧。。
		 * record.setPhoto(photoDao.get(pid));
		 * record.setAccountBook(getUsingAccountBookFromSession(session));
		 * 这时候肯定报错了
		 * a different object with the same identifier value was already associated with the session
		 * 这是一个经典的错误啊
		 * 网上有一些乱起八糟的解决办法，都是治标不治本
		 * 人家问题是：一个有着相同id的不同的对象也被关联到session上了
		 * 那你想为啥有另外一个对象啊
		 * 首先是执行setPhoto语句时photoDao.get(pid)得到照片
		 * 照片中有AccountBook属性，此时加载了一次
		 * 执行setAccountBook方法中调用getUsingAccountBookFromSession方法
		 * 	public AccountBook getUsingAccountBookFromSession(HttpSession session)
		 * 	{
		 * 		User user=userDao.get(UserServlet.getUser(session).getUid());
		 * 		AccountBook usingAccountBook=user.getUsingAccountBook();
		 * 		return usingAccountBook;
		 * 	 }
		 * 这个方法中调用了userDao.get方法，有AccountBook属性，此时又加载了一次
		 * 这就是为什么报错的原因
		 * AccountBook被加载了两次
		 * 所有应该用photo中的accountBook属性赋值给record
		 * 但是如果photo为收支记录空照片，他的AccountBook属性为系统空账本
		 * 不是用户的账本
		 * 这时候就应该从用户Session中导入AccountBook
		 * 也就是执行getUsingAccountBookFromSession方法
		 */
		Photo photo=photoDao.get(pid);
		record.setPhoto(photo);
		if(photo.getAccountBook().getAbid()==SystemInit.SYS_NULL_ID)
			record.setAccountBook(getUsingAccountBookFromSession(session));
		else
			record.setAccountBook(photo.getAccountBook());
		return recordDao.save(record);
	}
	
	@Override
	public int addEarn(int cid, int aid, int sid, String time, double money,String remark, int pid,HttpSession session) 
	{
		Record record=new Record();
		record.setSync(SystemInit.NOT_SYNC);
		Classification classification=classificationDao.get(cid);
		if(cid!=SystemInit.SYS_NULL_ID)
			classification.setCin(classification.getCin()+money);
		record.setClassification(classification);		
		Shop shop=shopDao.get(sid);
		if(sid!=SystemInit.SYS_NULL_ID)
			shop.setSin(shop.getSin()+money);
		record.setShop(shop);
		Account account=accountDao.get(aid);
		if(aid!=SystemInit.SYS_NULL_ID)
		{
			accountHistoryManager.update(account, DateTool.transferDate(time.split(" ")[0], DateTool.YEAR_MONTH_DATE_FORMAT), money);
			account.setAin(account.getAin()+money);
		}
		record.setAccount(account);
		record.setTime(DateTool.transferDate(time,"yyyy-MM-dd hh:mm"));
		record.setMoney(money);
		record.setRemark(remark);
		Photo photo=photoDao.get(pid);
		record.setPhoto(photo);
		if(photo.getAccountBook().getAbid()==SystemInit.SYS_NULL_ID)
			record.setAccountBook(getUsingAccountBookFromSession(session));
		else
			record.setAccountBook(photo.getAccountBook());
		return recordDao.save(record);
	}
	
	@Override
	public int addTransfer(int tfout, int tfin, String time,double money, String remark,HttpSession session) 
	{
		Date date=DateTool.transferDate(time.split(" ")[0], DateTool.YEAR_MONTH_DATE_FORMAT);
		Transfer transfer=new Transfer();
		transfer.setSync(SystemInit.NOT_SYNC);
		Account in=accountDao.get(tfin);
		accountHistoryManager.update(in, date, money);
		in.setAin(money+in.getAin());
		accountDao.update(in);
		transfer.setTfin(in);
		Account out=accountDao.get(tfout);
		accountHistoryManager.update(out, date, -money);
		out.setAout(money+out.getAout());
		accountDao.update(out);
		transfer.setTfout(out);
		transfer.setTime(DateTool.transferDate(time,"yyyy-MM-dd hh:mm"));
		transfer.setMoney(money);
		transfer.setRemark(remark);
		transfer.setAccountBook(getUsingAccountBookFromSession(session));
		return transferDao.save(transfer);
	}

	@Override
	public List<DateRecord> getRecord(int year,int month, int cid, int aid, int sid, String remark,HttpSession session) 
	{
		Classification classification=null;
		if(cid>=0)
			classification=classificationDao.get(cid);
		Account account=null;
		if(aid>=0)
			account=accountDao.get(aid);
		Shop shop=null;
		if(sid>=0)
			shop=shopDao.get(sid);
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		Date start=DateTool.getStart(year,month);
		Date end=DateTool.getEnd(year,month);
		List<Record> records=recordDao.find(start,end,classification,account,shop,remark,usingAccountBook);
		//开始对record按日期分类
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
			if(!DateTool.isSameDay(time, record.getTime()))
			{
				dateRecords.add(new DateRecord(DateTool.formatDate(recordBeans.get(0).getTime(),"MM/dd"),recordBeans,spend,earn));
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
				dateRecords.add(new DateRecord(DateTool.formatDate(recordBeans.get(0).getTime(),"MM/dd"),recordBeans,spend,earn));
		}
		return dateRecords;
	}

	@Override
	public TallyListTime getRecordTime(int year,HttpSession session) 
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		List<Integer> months=new ArrayList<Integer>();
		for(int month=1;month<=12;month++)
		{
			Date start=DateTool.getStart(year,month);
			Date end=DateTool.getEnd(year,month);
			int size=recordDao.getRecordSize(start,end,usingAccountBook);
			if(size>0)
				months.add(month);
		}
		return new TallyListTime(year, months);
	}

	@Override
	public List<DateTransfer> getTransfer(int year,int month,int tfout, int tfin, String remark,HttpSession session)
	{
		Account in=null;
		if(tfin>=0)
			in=accountDao.get(tfin);
		Account out=null;
		if(tfout>=0)
			out=accountDao.get(tfout);
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		Date start=DateTool.getStart(year,month);
		Date end=DateTool.getEnd(year,month);
		List<Transfer> transfers=transferDao.find(start, end, in, out, remark,usingAccountBook);
		List<TransferBean> transferBeans=new ArrayList<TransferBean>();
		List<DateTransfer> dateTransfers=new ArrayList<DateTransfer>();
		Date time=null;
		int count=0;
		double money=0;
		for(Transfer transfer:transfers)
		{
			count++;
			if(time==null)
				time=transfer.getTime();
			if(!DateTool.isSameDay(time, transfer.getTime()))
			{
				dateTransfers.add(new DateTransfer(DateTool.formatDate(transferBeans.get(0).getTime(),"MM/dd"), transferBeans, money));
				transferBeans=new ArrayList<TransferBean>();
				time=transfer.getTime();
				money=0;
			}
			transferBeans.add(new TransferBean(transfer));
			money+=transfer.getMoney();
			if(count==transfers.size())
				dateTransfers.add(new DateTransfer(DateTool.formatDate(transferBeans.get(0).getTime(),"MM/dd"), transferBeans, money));
		}
		return dateTransfers;
	}

	@Override
	public TallyListTime getTransferTime(int year, HttpSession session) 
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		List<Integer> months=new ArrayList<Integer>();
		for(int month=1;month<=12;month++)
		{
			Date start=DateTool.getStart(year,month);
			Date end=DateTool.getEnd(year,month);
			int size=transferDao.getTransferSize(start, end, usingAccountBook);
			if(size>0)
				months.add(month);
		}
		return new TallyListTime(year, months);
	}

}
