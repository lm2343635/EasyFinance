package edu.kit.ActMgr.data.iOS;

import edu.kit.ActMgr.domain.Shop;

public class iOSShopData 
{
	private int sid;
	private String sname;
	private double sin;
	private double sout;
	private int iid;
	private int abid;
	private int sync;
	
	public int getSid() {
		return sid;
	}
	public String getSname() {
		return sname;
	}
	public double getSin() {
		return sin;
	}
	public double getSout() {
		return sout;
	}
	public int getIid() {
		return iid;
	}
	public int getAbid() {
		return abid;
	}
	public int getSync() {
		return sync;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public void setSin(double sin) {
		this.sin = sin;
	}
	public void setSout(double sout) {
		this.sout = sout;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	public void setAbid(int abid) {
		this.abid = abid;
	}
	public void setSync(int sync) {
		this.sync = sync;
	}
	
	public iOSShopData() {
		super();
	}
	
	public iOSShopData(Shop shop) 
	{
		super();
		this.sid = shop.getSid();
		this.sname = shop.getSname();
		this.sin = shop.getSin();
		this.sout = shop.getSout();
		this.iid = shop.getSicon().getIid();
		this.abid = shop.getAccountBook().getAbid();
		this.sync = shop.getSync();
	}

}
