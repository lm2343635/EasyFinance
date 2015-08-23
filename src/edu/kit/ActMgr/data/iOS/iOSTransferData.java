package edu.kit.ActMgr.data.iOS;

import edu.kit.ActMgr.domain.Transfer;

public class iOSTransferData 
{
	private int tfid;
	private int tfoutid;
	private int tfinid;
	private double money;
	private String remark;
	private long timeInterval;
	private int abid;
	private int sync;
	
	public int getTfid() {
		return tfid;
	}

	public void setTfid(int tfid) {
		this.tfid = tfid;
	}

	public int getTfoutid() {
		return tfoutid;
	}

	public int getTfinid() {
		return tfinid;
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

	public void setTfoutid(int tfoutid) {
		this.tfoutid = tfoutid;
	}

	public void setTfinid(int tfinid) {
		this.tfinid = tfinid;
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

	public iOSTransferData(Transfer transfer)
	{
		super();
		this.tfid=transfer.getTfid();
		this.tfoutid = transfer.getTfout().getAid();
		this.tfinid = transfer.getTfin().getAid();
		this.money = transfer.getMoney();
		this.remark = transfer.getRemark();
		this.timeInterval = transfer.getTime().getTime();
		this.abid=transfer.getAccountBook().getAbid();
		this.sync=transfer.getSync();
	}

	public iOSTransferData() 
	{
		super();
	}
	
}
