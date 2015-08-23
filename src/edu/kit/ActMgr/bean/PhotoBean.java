package edu.kit.ActMgr.bean;

import java.util.Date;

import edu.kit.ActMgr.domain.Photo;

public class PhotoBean 
{
	private int pid;
	private String filename;
	private String pname;
	private Date upload;
	private AccountBookBean accountBook;
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Date getUpload() {
		return upload;
	}
	public void setUpload(Date upload) {
		this.upload = upload;
	}
	public AccountBookBean getAccountBook() {
		return accountBook;
	}
	public void setAccountBook(AccountBookBean accountBook) {
		this.accountBook = accountBook;
	}
	
	public PhotoBean() 
	{
		super();
	}

	public PhotoBean(int pid, String filename, String pname, Date upload,
			AccountBookBean accountBook) {
		super();
		this.pid = pid;
		this.filename = filename;
		this.pname = pname;
		this.upload = upload;
		this.accountBook = accountBook;
	}
	
	public PhotoBean(Photo photo) 
	{
		super();
		this.pid = photo.getPid();
		this.filename=photo.getFilename();
		this.pname=photo.getPname();
		this.upload=photo.getUpload();
		this.accountBook=new AccountBookBean(photo.getAccountBook());
	}
}
