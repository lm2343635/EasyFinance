package edu.kit.ActMgr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.AccountHistoryBean;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.AccountHistory;
import edu.kit.ActMgr.domain.Record;
import edu.kit.ActMgr.domain.Transfer;
import edu.kit.ActMgr.service.AccountHistoryManager;
import edu.kit.ActMgr.service.util.ManagerTemplate;
import edu.kit.common.hibernate3.support.SystemInit;
import edu.kit.common.util.DateTool;

/**
 * 账户历史管理器实现类，继承管理器模板，实现账户历史管理器
 * @author limeng
 *
 */
public class AccountHistoryManagerImpl extends ManagerTemplate implements AccountHistoryManager
{
	public static final int YEAR_TYPE=2;
	public static final int MONTH_TYPE=1;
	public static final int DAY_TYPE=0;
	@Override
	public int update(Account account, Date date, double money) 
	{
		AccountHistory history=accountHistoryDao.find(account, date);
		//当该日历史记录为空，直接添加新的历史记录。
		if(history==null)
		{
			history=new AccountHistory();
			history.setSync(SystemInit.NOT_SYNC);
			history.setAccount(account);
			history.setDate(date);
			//如果本日的记录是第一条记录。
			if(accountHistoryDao.isFirstHistory(history))
			{
				//情况1：该账户不存在任何历史记录。
				//并且还是最后一条记录，说明该账户不存在任何历史记录，直接创建该条历史记录，因为该记录是该账户的首条记录。
				if(accountHistoryDao.isLastHistory(history))
				{
					if(money>0)
					{
						history.setAin(money+account.getAin());
						history.setAout(account.getAout());
					}
					else
					{
						history.setAin(account.getAin());
						history.setAout(account.getAout()-money);
					}
				}
				//情况2：该账户存在历史记录，但本日记录是该账户最早的记录
				//该条记录由距离当前记录最近的之后的那一天的记录生成
				else
				{
					//首先，应该得到距离当前记录最近的之后的那一天的记录，并用它的ain和aout的值来初始化要生成的记录history的ain和aout
					AccountHistory latest=accountHistoryDao.findLatestAfter(account, date);
					history.setAin(latest.getAin());
					history.setAout(latest.getAout());
					//查询当前记录最近的之后的那一天的所有收支记录和转账记录
					//我们要根据这些记录还原账户的原始信息
					List<Record> records=recordDao.findDay(latest.getDate(), account);
					for(Record record:records)
					{
						//record.money为正说明该条收入记录应该被减去
						if(record.getMoney()>0)
							history.setAin(history.getAin()-record.getMoney());
						//否则说明该条支出记录应该被减去，money为负！！
						else
							history.setAout(history.getAout()+record.getMoney());
					}
					//查询这一天所有该账户为转入账户的转账记录
					List<Transfer> tfins=transferDao.findIn(account, latest.getDate());
					//转入的金额应该在ain中被减去
					for(Transfer tfin:tfins)
						history.setAin(history.getAin()-tfin.getMoney());
					//查询这一天所有该账户为转出账户的转账记录
					List<Transfer> tfouts=transferDao.findOut(account, latest.getDate());
					//转出金额应该在aout中被减去
					for(Transfer tfout:tfouts)
						history.setAout(history.getAout()-tfout.getMoney());
					//到此为止，记录已经被还原，应该把本次的记录添加进去了~
					if(money>0)
						history.setAin(history.getAin()+money);
					else
						history.setAout(history.getAout()-money);
					//之后的每一条记录都将受到影响
					List<AccountHistory> histories=accountHistoryDao.findByStart(account, date);
					for(AccountHistory accountHistory:histories)
					{
						if(money>0)
							accountHistory.setAin(accountHistory.getAin()+money);
						else
							accountHistory.setAout(accountHistory.getAout()-money);
						accountHistoryDao.update(accountHistory);
					}
				}
			}
			//情况3：该账户存在历史记录，但在该日期下不存在历史记录，且该日期的记录是最后一条历史记录，需要新建一条账户历史记录。
			//否则说明在该记录之前有其他记录存在，要找到距离当前记录最近的之前的那一天的记录，并在此之上。
			//直接添加的原因是：在本次修改和latest之间不可能有其他的修改，否则在本次修改的日期就会存在一条账户历史记录。
			else 
			{
				AccountHistory latest=accountHistoryDao.findLatestBefore(account, date);
				//若money为正，直接在latest.ain上添加money作为ain，aout保持不变。
				if(money>0)
				{
					history.setAin(latest.getAin()+money);
					history.setAout(latest.getAout());
				}
				//若money为负，直接在latest.aout上添加money的绝对值作为aout，ain保持不变
				else
				{
					history.setAin(latest.getAin());
					history.setAout(latest.getAout()-money);
				}
				//情况4：该账户存在历史记录，但在该日期下不存在历史记录，且该日期前后都有历史记录
				//该日期的历史记录将会同时受之前历史记录的影响，并且将会影响之后的历史记录
				if(!accountHistoryDao.isLastHistory(history))
				{
					List<AccountHistory> histories=accountHistoryDao.findByStart(account, date);
					for(AccountHistory accountHistory:histories)
					{
						if(money>0)
							accountHistory.setAin(accountHistory.getAin()+money);
						else
							accountHistory.setAout(accountHistory.getAout()-money);
						accountHistoryDao.update(accountHistory);
					}
				}
			}

			return accountHistoryDao.save(history);
		}
		//否则本日已有历史记录，就要修改当前的历史记录
		else
		{
			//情况5：本日已有历史记录，但本日之后没有历史记录
			//如果当前历史记录是该账户的最后一条历史记录，说明之前的历史记录将不会受到本次修改的影响，直接在该条记录上修改即可
			if(accountHistoryDao.isLastHistory(history))
			{
				if(money>0)
					history.setAin(history.getAin()+money);
				else
					history.setAout(history.getAout()-money);
				accountHistoryDao.update(history);
			}
			//情况6：本日已有历史记录，且本日之后有历史记录
			//否则说明当前历史记录之后还有其他记录，本次修改也会影响到后面的记录
			else
			{
				List<AccountHistory> histories=accountHistoryDao.findByStart(account, date);
				for(AccountHistory accountHistory:histories)
				{
					if(money>0)
						accountHistory.setAin(accountHistory.getAin()+money);
					else
						accountHistory.setAout(accountHistory.getAout()-money);
					accountHistoryDao.update(accountHistory);
				}
			}			
		}
		return history.getAhid();
	}
	
	@Override
	public List<AccountHistoryBean> getAccountHistories(String start, String end,int type, int aid, HttpSession session)
	{
		AccountBook usingAccountBook=getUsingAccountBookFromSession(session);
		Account account=accountDao.get(aid);
		if(account.getAccountBook().getAbid()==usingAccountBook.getAbid())
		{
			List<AccountHistoryBean> historyBeans=new ArrayList<AccountHistoryBean>();
			switch (type) 
			{
			case DAY_TYPE:
				Date startDate=DateTool.transferDate(start, DATE_FORMAT);
				//Date endDate=DateTool.transferDate(end, DATE_FORMAT);
				List<AccountHistory> histories=accountHistoryDao.findByAccount(account);
						//accountHistoryDao.find(account, startDate, endDate);
				//现在不能选择时间，要选择时间的话应该查询第一条历史记录之前的那一条
				AccountHistory last=new AccountHistory(null, startDate, 0.0, 0.0,null,0);
				for(AccountHistory history: histories)
				{
					AccountHistoryBean historyBean=new AccountHistoryBean();
					historyBean.setDate(DateTool.formatDate(history.getDate(), DATE_FORMAT));
					historyBean.setInflow(history.getAin()-last.getAin());
					historyBean.setOutflow(history.getAout()-last.getAout());
					historyBean.setTotalInflow(history.getAin());
					historyBean.setTotalOutflow(history.getAout());
					historyBean.setSurplus(history.getAin()-history.getAout());
					historyBeans.add(historyBean);
					last=history;
				}
				break;
			case MONTH_TYPE:
				
				break;
			case YEAR_TYPE:
				
				break;
			default:
				break;
			}
			return historyBeans;
		}
		return null;
	}


}
