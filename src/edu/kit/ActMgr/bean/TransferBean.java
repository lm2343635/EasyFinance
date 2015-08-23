package edu.kit.ActMgr.bean;

import java.util.Date;

import edu.kit.ActMgr.domain.Transfer;

public class TransferBean 
{
	private int tfid;
	private AccountBean tfout;
	private AccountBean tfin;
	private double money;
	private Date time;
	private String remark;
	private AccountBookBean accountBook;
	
	public int getTfid() {
		return tfid;
	}
	public void setTfid(int tfid) {
		this.tfid = tfid;
	}
	public AccountBean getTfout() {
		return tfout;
	}
	public void setTfout(AccountBean tfout) {
		this.tfout = tfout;
	}
	public AccountBean getTfin() {
		return tfin;
	}
	public void setTfin(AccountBean tfin) {
		this.tfin = tfin;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public AccountBookBean getAccountBook() {
		return accountBook;
	}
	public void setAccountBook(AccountBookBean accountBook) {
		this.accountBook = accountBook;
	}
	
	public TransferBean() {
		super();
	}
	
	public TransferBean(int tfid, AccountBean tfout, AccountBean tfin,
			double money, Date time, String remark, AccountBookBean accountBook) {
		super();
		this.tfid = tfid;
		this.tfout = tfout;
		this.tfin = tfin;
		this.money = money;
		this.time = time;
		this.remark = remark;
		this.accountBook = accountBook;
	}
	
	public TransferBean(Transfer transfer) 
	{
		super();
		this.tfid = transfer.getTfid();
		this.tfout = new AccountBean(transfer.getTfout());
		this.tfin = new AccountBean(transfer.getTfin());
		this.money =transfer.getMoney();
		this.time = transfer.getTime();
		this.remark = transfer.getRemark();
		this.accountBook = new AccountBookBean(transfer.getAccountBook());
	}
}
