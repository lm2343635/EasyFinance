package edu.kit.ActMgr.bean;

import edu.kit.ActMgr.domain.Classification;

public class ClassificationBean 
{
	private int cid;
	private String cname;
	private double cin;
	private double cout;
	private double asset;
	private IconBean cicon;
	private AccountBookBean accountBook;
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public double getCin() {
		return cin;
	}
	public void setCin(double cin) {
		this.cin = cin;
	}
	public double getCout() {
		return cout;
	}
	public void setCout(double cout) {
		this.cout = cout;
	}
	public double getAsset() {
		return asset;
	}
	public void setAsset(double asset) {
		this.asset = asset;
	}
	public IconBean getCicon() {
		return cicon;
	}
	public void setCicon(IconBean cicon) {
		this.cicon = cicon;
	}
	public AccountBookBean getAccountBook() {
		return accountBook;
	}
	public void setAccountBook(AccountBookBean accountBook) {
		this.accountBook = accountBook;
	}
	
	public ClassificationBean() 
	{
		super();
	}
	
	public ClassificationBean(int cid, String cname, double cin, double cout,
			IconBean cicon, AccountBookBean accountBook) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.cin = cin;
		this.cout = cout;
		this.asset=this.cin-this.cout;
		this.cicon = cicon;
		this.accountBook = accountBook;
	}
	
	public ClassificationBean(Classification classification) 
	{
		super();
		this.cid =classification.getCid();
		this.cname = classification.getCname();
		this.cin = classification.getCin();
		this.cout = classification.getCout();
		this.asset=this.cin-this.cout;
		this.cicon = new IconBean(classification.getCicon());
		this.accountBook = new AccountBookBean(classification.getAccountBook());
	}
	
}
