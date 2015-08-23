package edu.kit.ActMgr.domain;

import java.io.Serializable;

public class Icon implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer iid;
	private String iname;
	private Integer type;	
	private User user;
	private Integer sync;
	
	public Integer getIid() {
		return iid;
	}
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getIname() {
		return iname;
	}
	public void setIname(String iname) {
		this.iname = iname;
	}
	public Integer getSync() {
		return sync;
	}
	public void setSync(Integer sync) {
		this.sync = sync;
	}
	public Icon() {
		super();
	}
	public Icon(Integer iid, String iname, Integer type, User user, Integer sync) {
		super();
		this.iid = iid;
		this.iname = iname;
		this.type = type;
		this.user = user;
		this.sync = sync;
	}

}
