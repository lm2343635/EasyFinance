package edu.kit.ActMgr.bean;

import edu.kit.ActMgr.domain.AccountBook;

public class AccountBookBean 
{
	private Integer abid;
	private String abname;
	private IconBean abicon;
	
	public Integer getAbid() {
		return abid;
	}
	public void setAbid(Integer abid) {
		this.abid = abid;
	}
	public String getAbname() {
		return abname;
	}
	public void setAbname(String abname) {
		this.abname = abname;
	}
	public IconBean getAbicon() {
		return abicon;
	}
	public void setAbicon(IconBean abicon) {
		this.abicon = abicon;
	}
	
	public AccountBookBean() {
		super();
	}
	
	public AccountBookBean(Integer abid, String abname, IconBean abicon) {
		super();
		this.abid = abid;
		this.abname = abname;
		this.abicon = abicon;
	}
	
	public AccountBookBean(AccountBook accountBook) 
	{
		super();
		this.abid=accountBook.getAbid();
		this.abname=accountBook.getAbname();
		this.abicon=new IconBean(accountBook.getAbicon());
	}
	
}
