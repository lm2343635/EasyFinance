package edu.kit.ActMgr.domain;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Integer rid;
	private Date time;
	private Double money;
	private String remark;
	private Classification classification;
	private Shop shop;
	private Account account;
	private Photo photo;
	private AccountBook accountBook;
	private Integer sync;
	
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public Classification getClassification() {
		return classification;
	}
	public void setClassification(Classification classification) {
		this.classification = classification;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Photo getPhoto() {
		return photo;
	}
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	public AccountBook getAccountBook() {
		return accountBook;
	}
	public void setAccountBook(AccountBook accountBook) {
		this.accountBook = accountBook;
	}
	public Integer getSync() {
		return sync;
	}
	public void setSync(Integer sync) {
		this.sync = sync;
	}
	public Record() {
		super();
	}

	public Record(Integer rid, Date time, Double money, String remark,
			Classification classification, Shop shop, Account account,
			Photo photo, AccountBook accountBook, Integer sync) {
		super();
		this.rid = rid;
		this.time = time;
		this.money = money;
		this.remark = remark;
		this.classification = classification;
		this.shop = shop;
		this.account = account;
		this.photo = photo;
		this.accountBook = accountBook;
		this.sync = sync;
	}
	
}
