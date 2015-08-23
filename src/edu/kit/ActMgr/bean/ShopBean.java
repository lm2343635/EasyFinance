package edu.kit.ActMgr.bean;

import edu.kit.ActMgr.domain.Shop;

public class ShopBean 
{
	private int sid;
	private String sname;
	private double sin;
	private double sout;
	private double asset;
	private IconBean sicon;
	private AccountBookBean accountBook;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public double getSin() {
		return sin;
	}
	public void setSin(double sin) {
		this.sin = sin;
	}
	public double getSout() {
		return sout;
	}
	public void setSout(double sout) {
		this.sout = sout;
	}
	public double getAsset() {
		return asset;
	}
	public void setAsset(double asset) {
		this.asset = asset;
	}
	public IconBean getSicon() {
		return sicon;
	}
	public void setSicon(IconBean sicon) {
		this.sicon = sicon;
	}
	public AccountBookBean getAccountBook() {
		return accountBook;
	}
	public void setAccountBook(AccountBookBean accountBook) {
		this.accountBook = accountBook;
	}
	
	public ShopBean() {
		super();
	}
	
	public ShopBean(int sid, String sname, double sin, double sout,
			IconBean sicon, AccountBookBean accountBook) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.sin = sin;
		this.sout = sout;
		this.asset=this.sin-this.sout;
		this.sicon = sicon;
		this.accountBook = accountBook;
	}
	
	public ShopBean(Shop shop) 
	{
		super();
		this.sid = shop.getSid();
		this.sname = shop.getSname();
		this.sin = shop.getSin();
		this.sout = shop.getSout();
		this.asset=this.sin-this.sout;
		this.sicon = new IconBean(shop.getSicon());
		this.accountBook = new AccountBookBean(shop.getAccountBook());
	}
}
