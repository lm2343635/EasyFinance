package edu.kit.ActMgr.bean;

import edu.kit.ActMgr.domain.User;

public class UserBean 
{
	private int uid;
	private String uname;
	private String email;
	private String password;
	private PhotoBean photo;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
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
	public PhotoBean getPhoto() {
		return photo;
	}
	public void setPhoto(PhotoBean photo) {
		this.photo = photo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserBean() 
	{
		super();
	}
	
	public UserBean(int uid, String uname, String email, String password,
			PhotoBean photo) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.email = email;
		this.password = password;
		this.photo = photo;
	}
	
	public UserBean(User user) 
	{
		super();
		this.uid = user.getUid();
		this.uname = user.getUname();
		this.email = user.getEmail();
		this.photo = new PhotoBean(user.getPhoto());
		this.password=user.getPassword();
	}
}
