package edu.kit.ActMgr.bean;

import java.util.Date;

import edu.kit.ActMgr.domain.Record;

public class RecordBean 
{
	private int rid;
	private ClassificationBean classification;
	private ShopBean shop;
	private AccountBean account;
	private Date time;
	private double money;
	private String remark;
	private PhotoBean photo;
	private AccountBookBean accountBook;
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public ClassificationBean getClassification() {
		return classification;
	}
	public void setClassification(ClassificationBean classification) {
		this.classification = classification;
	}
	public ShopBean getShop() {
		return shop;
	}
	public void setShop(ShopBean shop) {
		this.shop = shop;
	}
	public AccountBean getAccount() {
		return account;
	}
	public void setAccount(AccountBean account) {
		this.account = account;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public PhotoBean getPhoto() {
		return photo;
	}
	public void setPhoto(PhotoBean photo) {
		this.photo = photo;
	}
	public AccountBookBean getAccountBook() {
		return accountBook;
	}
	public void setAccountBook(AccountBookBean accountBook) {
		this.accountBook = accountBook;
	}
	
	public RecordBean()
	{
		super();
	}
	
	public RecordBean(int rid, ClassificationBean classification,
			ShopBean shop, AccountBean account, Date time, double money,
			String remark, PhotoBean photo, AccountBookBean accountBook) 
	{
		super();
		this.rid = rid;
		this.classification = classification;
		this.shop = shop;
		this.account = account;
		this.time = time;
		this.money = money;
		this.remark = remark;
		this.photo = photo;
		this.accountBook = accountBook;
	}
	
	public RecordBean(Record record)
	{
		super();
		this.rid = record.getRid();
		this.classification = new ClassificationBean(record.getClassification());
		this.shop = new ShopBean(record.getShop());
		this.account = new AccountBean(record.getAccount());
		this.time = record.getTime();
		this.money = record.getMoney();
		this.remark = record.getRemark();
		this.photo = new PhotoBean(record.getPhoto());
		this.accountBook = new AccountBookBean(record.getAccountBook());
	}
}
