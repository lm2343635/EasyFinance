package edu.kit.ActMgr.domain;

import java.io.Serializable;
import java.util.Date;

public class Photo implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Integer pid;
	private String filename;
	private String pname;
	private Date upload;
	private AccountBook accountBook;
	private Integer sync;
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Date getUpload() {
		return upload;
	}
	public void setUpload(Date upload) {
		this.upload = upload;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}	
	public AccountBook getAccountBook() {
		return accountBook;
	}
	public void setAccountBook(AccountBook accountBook) {
		this.accountBook = accountBook;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getSync() {
		return sync;
	}
	public void setSync(Integer sync) {
		this.sync = sync;
	}
	public Photo() {
		super();
	}
	public Photo(Integer pid, String filename, String pname, Date upload,
			AccountBook accountBook, Integer sync) {
		super();
		this.pid = pid;
		this.filename = filename;
		this.pname = pname;
		this.upload = upload;
		this.accountBook = accountBook;
		this.sync = sync;
	}
}
