package edu.kit.ActMgr.data.iOS;

import edu.kit.ActMgr.domain.Template;

public class iOSTemplateData 
{
	private int tpid;
	private String tpname;
	private int cid;
	private int aid;
	private int sid;
	private int abid;
	private int sync;
	public int getTpid() {
		return tpid;
	}
	public String getTpname() {
		return tpname;
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
	public int getAbid() {
		return abid;
	}
	public int getSync() {
		return sync;
	}
	public void setTpid(int tpid) {
		this.tpid = tpid;
	}
	public void setTpname(String tpname) {
		this.tpname = tpname;
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
	public void setAbid(int abid) {
		this.abid = abid;
	}
	public void setSync(int sync) {
		this.sync = sync;
	}
	
	public iOSTemplateData() {
		super();
	}
	
	public iOSTemplateData(Template template) 
	{
		super();
		this.tpid = template.getTpid();
		this.tpname = template.getTpname();
		this.cid = template.getClassification().getCid();
		this.aid = template.getAccount().getAid();
		this.sid = template.getShop().getSid();
		this.abid = template.getAccountBook().getAbid();
		this.sync = template.getSync();
	}
}
