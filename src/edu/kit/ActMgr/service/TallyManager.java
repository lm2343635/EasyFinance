package edu.kit.ActMgr.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.data.DateRecord;
import edu.kit.ActMgr.data.DateTransfer;
import edu.kit.ActMgr.data.TallyListTime;

public interface TallyManager 
{

	/**
	 * 添加一条消费记录
	 * @param cid 分类id
	 * @param aid 账户id
	 * @param sid 商家id
	 * @param time 时间
	 * @param money 金钱
	 * @param remark 备注
	 * @return 记录id
	 */
	int addSpend(int cid,int aid,int sid,String time,double money,String remark,int pid,HttpSession session);
	
	/**
	 * 添加一条收入记录
	 * @param cid 分类id
	 * @param aid 账户id
	 * @param sid 商家id
	 * @param time 时间
	 * @param money 金钱
	 * @param remark 备注
	 * @return 记录id
	 */
	int addEarn(int cid,int aid,int sid,String time,double money,String remark,int pid,HttpSession session);
	
	/**
	 * 添加一条转账
	 * @param tfout 转出账户id
	 * @param tfin 转入账户id
	 * @param time 时间
	 * @param money 金钱
	 * @param remark 备注
	 * @return 转账id
	 */
	int addTransfer(int tfout,int tfin,String time,double money,String remark,HttpSession session);
	
	/**
	 * 查询记账记录
	 * @param cid 分类id
	 * @param aid 账户id
	 * @param sid 商家id
	 * @param remark 备注
	 * @param session
	 * @return
	 */
	List<DateRecord> getRecord(int year,int month,int cid,int aid,int sid,String remark,HttpSession session);
	
	/**
	 * 得到某一年中有记录的月份
	 * @param year
	 * @param session
	 * @return
	 */
	TallyListTime getRecordTime(int year,HttpSession session);
	
	/**
	 * 
	 * @param tfout
	 * @param tfin
	 * @param remark
	 * @param session
	 * @return
	 */
	List<DateTransfer> getTransfer(int year,int month,int tfout,int tfin,String remark,HttpSession session);
	
	/**
	 * 得到某一年中有转账的月份
	 * @param year
	 * @param session
	 * @return
	 */
	TallyListTime getTransferTime(int year,HttpSession session);
}
