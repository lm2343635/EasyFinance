package edu.kit.ActMgr.data.iOS;

import edu.kit.ActMgr.domain.SynchronizationHistory;

public class iOSSynchronizationHistoryData 
{
	private int shid;
	private long timeInterval;
	private String device;
	private String ip;
	private int uid;
	
	public int getShid() {
		return shid;
	}

	public long getTimeInterval() {
		return timeInterval;
	}

	public String getDevice() {
		return device;
	}

	public String getIp() {
		return ip;
	}

	public int getUid() {
		return uid;
	}

	public void setShid(int shid) {
		this.shid = shid;
	}

	public void setTimeInterval(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public iOSSynchronizationHistoryData() {
		super();
	}

	public iOSSynchronizationHistoryData(SynchronizationHistory history) 
	{
		super();
		this.shid = history.getShid();
		this.timeInterval = history.getTime().getTime();
		this.device = history.getDevice();
		this.ip = history.getIp();
		this.uid = history.getUser().getUid();
	}

}
