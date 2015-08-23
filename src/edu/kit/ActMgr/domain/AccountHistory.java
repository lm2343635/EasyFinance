package edu.kit.ActMgr.domain;

import java.io.Serializable;
import java.util.Date;

public class AccountHistory implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Integer ahid;
	private Date date;
	private Double ain;
	private Double aout;
	private Account account;
	private Integer sync;

	public Integer getAhid() {
		return ahid;
	}
	public void setAhid(Integer ahid) {
		this.ahid = ahid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getAin() {
		return ain;
	}
	public void setAin(Double ain) {
		this.ain = ain;
	}
	public Double getAout() {
		return aout;
	}
	public void setAout(Double aout) {
		this.aout = aout;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}	
	
	public Integer getSync() {
		return sync;
	}
	public void setSync(Integer sync) {
		this.sync = sync;
	}
	
	public AccountHistory() 
	{
		super();
	}
	
	public AccountHistory(Integer ahid, Date date, Double ain, Double aout,
			Account account, Integer sync) {
		super();
		this.ahid = ahid;
		this.date = date;
		this.ain = ain;
		this.aout = aout;
		this.account = account;
		this.sync = sync;
	}

}
