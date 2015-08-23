package edu.kit.ActMgr.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Integer uid;
	private String uname;
	private String email;
	private String password;
	private Photo photo;
	
	private AccountBook usingAccountBook;
	private Set<AccountBook> accountBooks=new HashSet<AccountBook>();
	
	private String syncKey;
	private String iOSDeviceInfo;
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<AccountBook> getAccountBooks() {
		return accountBooks;
	}
	public void setAccountBooks(Set<AccountBook> accountBooks) {
		this.accountBooks = accountBooks;
	}
	public AccountBook getUsingAccountBook() {
		return usingAccountBook;
	}
	public void setUsingAccountBook(AccountBook usingAccountBook) {
		this.usingAccountBook = usingAccountBook;
	}
	public Photo getPhoto() {
		return photo;
	}
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	public String getSyncKey() {
		return syncKey;
	}
	public void setSyncKey(String syncKey) {
		this.syncKey = syncKey;
	}
	public String getiOSDeviceInfo() {
		return iOSDeviceInfo;
	}
	public void setiOSDeviceInfo(String iOSDeviceInfo) {
		this.iOSDeviceInfo = iOSDeviceInfo;
	}
	public User() 
	{
		super();
	}
	public User(Integer uid, String uname, String email, String password,
			Photo photo, AccountBook usingAccountBook,
			Set<AccountBook> accountBooks, String syncKey, String iOSDeviceInfo) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.email = email;
		this.password = password;
		this.photo = photo;
		this.usingAccountBook = usingAccountBook;
		this.accountBooks = accountBooks;
		this.syncKey = syncKey;
		this.iOSDeviceInfo = iOSDeviceInfo;
	}
	

}
