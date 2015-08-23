package edu.kit.ActMgr.bean;

import edu.kit.ActMgr.domain.Account;

public class AccountBean 
{
	private int aid;
	private String aname;
	private double ain;
	private double aout;
	private double asset;
	private IconBean aicon;
	private AccountBookBean accountBook;
	
	public int getAid() {
		return aid;
	}

	public String getAname() {
		return aname;
	}

	public double getAin() {
		return ain;
	}

	public double getAout() {
		return aout;
	}

	public double getAsset() {
		return asset;
	}

	public IconBean getAicon() {
		return aicon;
	}

	public AccountBookBean getAccountBook() {
		return accountBook;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public void setAin(double ain) {
		this.ain = ain;
	}

	public void setAout(double aout) {
		this.aout = aout;
	}

	public void setAsset(double asset) {
		this.asset = asset;
	}

	public void setAicon(IconBean aicon) {
		this.aicon = aicon;
	}

	public void setAccountBook(AccountBookBean accountBook) {
		this.accountBook = accountBook;
	}

	public AccountBean() {
		super();
	}
	
	public AccountBean(int aid, String aname, double ain, double aout,
			IconBean aicon, AccountBookBean accountBook) {
		super();
		this.aid = aid;
		this.aname = aname;
		this.ain = ain;
		this.aout = aout;
		this.asset=this.ain-this.aout;
		this.aicon = aicon;
		this.accountBook = accountBook;
	}
	
	public AccountBean(Account account) 
	{
		super();
		this.aid = account.getAid();
		this.aname = account.getAname();
		this.ain = account.getAin();
		this.aout = account.getAout();
		this.asset=this.ain-this.aout;
		this.aicon = new IconBean(account.getAicon());
		this.accountBook = new AccountBookBean(account.getAccountBook());
	}
}
