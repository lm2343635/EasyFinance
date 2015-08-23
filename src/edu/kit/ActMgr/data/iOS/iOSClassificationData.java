package edu.kit.ActMgr.data.iOS;

import edu.kit.ActMgr.domain.Classification;

public class iOSClassificationData 
{
	private int cid;
	private String cname;
	private double cin;
	private double cout;
	private int iid;
	private int abid;
	private int sync;
	
	public int getCid() {
		return cid;
	}
	public String getCname() {
		return cname;
	}
	public double getCin() {
		return cin;
	}
	public double getCout() {
		return cout;
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
	public void setCid(int cid) {
		this.cid = cid;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public void setCin(double cin) {
		this.cin = cin;
	}
	public void setCout(double cout) {
		this.cout = cout;
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
	
	public iOSClassificationData() {
		super();
	}
	
	public iOSClassificationData(Classification classification) 
	{
		super();
		this.cid = classification.getCid();
		this.cname = classification.getCname();
		this.cin = classification.getCin();
		this.cout = classification.getCout();
		this.iid = classification.getCicon().getIid();
		this.abid = classification.getAccountBook().getAbid();
		this.sync = classification.getSync();
	}

}
