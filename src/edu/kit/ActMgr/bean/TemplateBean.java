package edu.kit.ActMgr.bean;

import edu.kit.ActMgr.domain.Template;

public class TemplateBean 
{
	private int tpid;
	private String tpname;
	private ClassificationBean classification;
	private ShopBean shop;
	private AccountBean account;
	private AccountBookBean accountBook;
	
	public int getTpid() {
		return tpid;
	}
	public void setTpid(int tpid) {
		this.tpid = tpid;
	}
	public String getTpname() {
		return tpname;
	}
	public void setTpname(String tpname) {
		this.tpname = tpname;
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
	public AccountBookBean getAccountBook() {
		return accountBook;
	}
	public void setAccountBook(AccountBookBean accountBook) {
		this.accountBook = accountBook;
	}
	
	public TemplateBean() 
	{
		super();
	}
	
	public TemplateBean(int tpid, String tpname,
			ClassificationBean classification, ShopBean shop,
			AccountBean account, AccountBookBean accountBook) 
	{
		super();
		this.tpid = tpid;
		this.tpname = tpname;
		this.classification = classification;
		this.shop = shop;
		this.account = account;
		this.accountBook = accountBook;
	}
	
	public TemplateBean(Template template) 
	{
		super();
		this.tpid = template.getTpid();
		this.tpname = template.getTpname();
		this.classification = new ClassificationBean(template.getClassification());
		this.shop = new ShopBean(template.getShop());
		this.account = new AccountBean(template.getAccount());
		this.accountBook = new AccountBookBean(template.getAccountBook());
	}
}
