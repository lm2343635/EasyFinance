package edu.kit.ActMgr.domain;

import java.io.Serializable;
import java.util.Date;

public class Transfer implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Integer tfid;
	private Double money;
	private Date time;
	private String remark;
	private Account tfout;
	private Account tfin;
	private AccountBook accountBook;
	private Integer sync;
	
	public Integer getTfid() {
		return tfid;
	}
	public void setTfid(Integer tfid) {
		this.tfid = tfid;
	}
	public Account getTfout() {
		return tfout;
	}
	public void setTfout(Account tfout) {
		this.tfout = tfout;
	}
	public Account getTfin() {
		return tfin;
	}
	public void setTfin(Account tfin) {
		this.tfin = tfin;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
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
	public AccountBook getAccountBook() {
		return accountBook;
	}
	public Integer getSync() {
		return sync;
	}
	public void setSync(Integer sync) {
		this.sync = sync;
	}
	public void setAccountBook(AccountBook accountBook) {
		this.accountBook = accountBook;
	}
	public Transfer() {
		super();
	}
	public Transfer(Integer tfid, Double money, Date time, String remark,
			Account tfout, Account tfin, AccountBook accountBook, Integer sync) {
		super();
		this.tfid = tfid;
		this.money = money;
		this.time = time;
		this.remark = remark;
		this.tfout = tfout;
		this.tfin = tfin;
		this.accountBook = accountBook;
		this.sync = sync;
	}

}
