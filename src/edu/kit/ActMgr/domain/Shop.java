package edu.kit.ActMgr.domain;

import java.io.Serializable;

public class Shop implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer sid;
	private String sname;
	private Double sin;
	private Double sout;
	private Icon sicon;
	private AccountBook accountBook;
	private Integer sync;
	
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public Double getSin() {
		return sin;
	}
	public void setSin(Double sin) {
		this.sin = sin;
	}
	public Double getSout() {
		return sout;
	}
	public void setSout(Double sout) {
		this.sout = sout;
	}
	public Icon getSicon() {
		return sicon;
	}
	public void setSicon(Icon sicon) {
		this.sicon = sicon;
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
	public Shop() {
		super();
	}
	public Shop(Integer sid, String sname, Double sin, Double sout, Icon sicon,
			AccountBook accountBook, Integer sync) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.sin = sin;
		this.sout = sout;
		this.sicon = sicon;
		this.accountBook = accountBook;
		this.sync = sync;
	}
}
