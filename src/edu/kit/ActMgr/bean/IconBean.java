package edu.kit.ActMgr.bean;

import edu.kit.ActMgr.domain.Icon;

public class IconBean 
{
	private int iid;
	private String iname;
	private int type;	
	private int uid;
	
	public Integer getIid() {
		return iid;
	}
	public String getIname() {
		return iname;
	}
	public Integer getType() {
		return type;
	}
	public Integer getUid() {
		return uid;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	public void setIname(String iname) {
		this.iname = iname;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public IconBean(int iid, String iname, int type, int uid) {
		super();
		this.iid = iid;
		this.iname = iname;
		this.type = type;
		this.uid = uid;
	}
	
	public IconBean() 
	{
		super();
	}
	
	public IconBean(Icon icon) 
	{
		super();
		this.iid=icon.getIid();
		this.iname=icon.getIname();
		this.type=icon.getType();
		this.uid=icon.getUser().getUid();
	}
}
