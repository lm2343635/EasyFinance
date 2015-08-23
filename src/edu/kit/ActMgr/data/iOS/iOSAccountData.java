package edu.kit.ActMgr.data.iOS;

import edu.kit.ActMgr.domain.Account;

public class iOSAccountData 
{
	private int aid;
	private String aname;
	private double ain;
	private double aout;
	private int iid;
	private int abid;
	private int sync;
	
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
	public int getIid() {
		return iid;
	}
	public int getAbid() {
		return abid;
	}
	public int getSync() {
		return sync;
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
	public void setIid(int iid) {
		this.iid = iid;
	}
	public void setAbid(int abid) {
		this.abid = abid;
	}
	public void setSync(int sync) {
		this.sync = sync;
	}
	
	public iOSAccountData() {
		super();
	}
	
	public iOSAccountData(Account account)
	{
		super();
		this.aid = account.getAid();
		this.aname = account.getAname();
		this.ain = account.getAin();
		this.aout = account.getAout();
		this.iid = account.getAicon().getIid();
		this.abid = account.getAccountBook().getAbid();
		this.sync = account.getSync();
	}
}
