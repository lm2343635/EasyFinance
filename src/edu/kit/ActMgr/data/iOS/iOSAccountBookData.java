package edu.kit.ActMgr.data.iOS;

import edu.kit.ActMgr.domain.AccountBook;

public class iOSAccountBookData 
{
	private int sync;
	private int abid;
	private String abname;
	private int iid;
	private int uid;

	public int getSync() {
		return sync;
	}
	public int getAbid() {
		return abid;
	}
	public String getAbname() {
		return abname;
	}
	public int getIid() {
		return iid;
	}
	public int getUid() {
		return uid;
	}
	public void setSync(int sync) {
		this.sync = sync;
	}
	public void setAbid(int abid) {
		this.abid = abid;
	}
	public void setAbname(String abname) {
		this.abname = abname;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public iOSAccountBookData() {
		super();
	}
	public iOSAccountBookData(AccountBook accountBook) 
	{
		this.sync=accountBook.getSync();
		this.abid=accountBook.getAbid();
		this.abname=accountBook.getAbname();
		this.uid=accountBook.getUser().getUid();
		this.iid=accountBook.getAbicon().getIid();
	}
}
