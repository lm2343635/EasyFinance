package edu.kit.ActMgr.domain;

import java.util.Date;

public class SynchronizationHistory 
{
	private Integer shid;
	private Date time;
	private String ip;
	private String device;
	private User user;
	
	public Integer getShid() {
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
	public User getUser() {
		return user;
	}
	public void setShid(Integer shid) {
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
	public void setUser(User user) {
		this.user = user;
	}

	
}
