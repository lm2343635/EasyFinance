package edu.kit.ActMgr.domain;

import java.io.Serializable;

public class Classification implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer cid;
	private String cname;
	private Double cin;
	private Double cout;
	private Icon cicon;
	private AccountBook accountBook;
	private Integer sync;
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Double getCin() {
		return cin;
	}
	public void setCin(Double cin) {
		this.cin = cin;
	}
	public Double getCout() {
		return cout;
	}
	public void setCout(Double cout) {
		this.cout = cout;
	}
	public Icon getCicon() {
		return cicon;
	}
	public void setCicon(Icon cicon) {
		this.cicon = cicon;
	}
	public Classification() {
		super();
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
	public Classification(Integer cid, String cname, Double cin, Double cout,
			Icon cicon, AccountBook accountBook, Integer sync) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.cin = cin;
		this.cout = cout;
		this.cicon = cicon;
		this.accountBook = accountBook;
		this.sync = sync;
	}

}
