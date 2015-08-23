package edu.kit.ActMgr.domain;

import java.io.Serializable;

public class Account implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer aid;
	private String aname;
	private Double ain;
	private Double aout;
	private Icon aicon;
	private AccountBook accountBook;
	private Integer sync;
	
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
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
	public Icon getAicon() {
		return aicon;
	}
	public void setAicon(Icon aicon) {
		this.aicon = aicon;
	}
	public AccountBook getAccountBook() {
		return accountBook;
	}
	public void setAccountBook(AccountBook accountBook) {
		this.accountBook = accountBook;
	}
	public Integer getSync() {
		return sync;
	}
	public void setSync(Integer sync) {
		this.sync = sync;
	}
	
	public Account()
	{
		super();
	}
	public Account(Integer aid, String aname, Double ain, Double aout,
			Icon aicon, AccountBook accountBook, Integer sync) {
		super();
		this.aid = aid;
		this.aname = aname;
		this.ain = ain;
		this.aout = aout;
		this.aicon = aicon;
		this.accountBook = accountBook;
		this.sync = sync;
	}
}
