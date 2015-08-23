package edu.kit.ActMgr.bean;

import java.util.Date;

import edu.kit.ActMgr.domain.SynchronizationHistory;

public class SynchronizationHistoryBean 
{
	private int shid;
	private Date time;
	private String ip;
	private String device;
	private UserBean user;
	
	public int getShid() {
		return shid;
	}
	public Date getTime() {
		return time;
	}
	public String getIp() {
		return ip;
	}
	public String getDevice() {
		return device;
	}
	public UserBean getUser() {
		return user;
	}
	public void setShid(int shid) {
		this.shid = shid;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	
	public SynchronizationHistoryBean() {
		super();
	}
	
	public SynchronizationHistoryBean(SynchronizationHistory synchronizationHistory)
	{
		super();
		this.shid = synchronizationHistory.getShid();
		this.time = synchronizationHistory.getTime();
		this.ip = synchronizationHistory.getIp();
		this.device = synchronizationHistory.getDevice();
		this.user = new UserBean(synchronizationHistory.getUser());
	}
}
