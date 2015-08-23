package edu.kit.ActMgr.data.iOS;

import edu.kit.ActMgr.domain.Record;

public class iOSRecordData 
{
	private int rid;
	private double money;
	private String remark;
	private long timeInterval;
	private int cid;
	private int aid;
	private int sid;
	private int pid;
	private int abid;
	private int sync;
	
	public int getRid() {
		return rid;
	}

	public double getMoney() {
		return money;
	}

	public String getRemark() {
		return remark;
	}

	public long getTimeInterval() {
		return timeInterval;
	}

	public int getCid() {
		return cid;
	}

	public int getAid() {
		return aid;
	}

	public int getSid() {
		return sid;
	}

	public int getPid() {
		return pid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTimeInterval(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getSync() {
		return sync;
	}

	public void setSync(int sync) {
		this.sync = sync;
	}

	public int getAbid() {
		return abid;
	}

	public void setAbid(int abid) {
		this.abid = abid;
	}

	public iOSRecordData(Record record) 
	{
		super();
		this.rid = record.getRid();
		this.money = record.getMoney();
		this.remark = record.getRemark();
		this.timeInterval = record.getTime().getTime();
		this.cid = record.getClassification().getCid();
		this.aid = record.getAccount().getAid();
		this.sid = record.getShop().getSid();
		this.pid = record.getPhoto().getPid();
		this.abid=record.getAccountBook().getAbid();
		this.sync=record.getSync();
	}

	public iOSRecordData() {
		super();
	}

}
