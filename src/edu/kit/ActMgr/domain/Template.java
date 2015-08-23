package edu.kit.ActMgr.domain;

import java.io.Serializable;

public class Template implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer tpid;
	private String tpname;
	private Classification classification;
	private Shop shop;
	private Account account;
	private AccountBook accountBook;
	private Integer sync;
	
	public Integer getTpid() {
		return tpid;
	}
	public void setTpid(Integer tpid) {
		this.tpid = tpid;
	}
	public String getTpname() {
		return tpname;
	}
	public void setTpname(String tpname) {
		this.tpname = tpname;
	}
	public Classification getClassification() {
		return classification;
	}
	public void setClassification(Classification classification) {
		this.classification = classification;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
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
	public Template() {
		super();
	}
	public Template(Integer tpid, String tpname, Classification classification,
			Shop shop, Account account, AccountBook accountBook, Integer sync) {
		super();
		this.tpid = tpid;
		this.tpname = tpname;
		this.classification = classification;
		this.shop = shop;
		this.account = account;
		this.accountBook = accountBook;
		this.sync = sync;
	}
}
