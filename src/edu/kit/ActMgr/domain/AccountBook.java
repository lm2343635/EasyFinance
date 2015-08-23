package edu.kit.ActMgr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountBook implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Integer abid;
	private String abname;
	
	private User user;
	private Icon abicon;
	
	private Set<Classification> classifications=new HashSet<Classification>();
	private Set<Shop> shops=new HashSet<Shop>();
	private Set<Account> accounts=new HashSet<Account>();
	private Set<Template> templates=new HashSet<Template>();
	private List<Record> records=new ArrayList<Record>();
	private List<Transfer> transfers=new ArrayList<Transfer>();
	
	private Integer sync;
	
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Icon getAbicon() {
		return abicon;
	}
	public void setAbicon(Icon abicon) {
		this.abicon = abicon;
	}
	public Set<Classification> getClassifications() {
		return classifications;
	}
	public void setClassifications(Set<Classification> classifications) {
		this.classifications = classifications;
	}
	public Set<Shop> getShops() {
		return shops;
	}
	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}
	public Set<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
	public Set<Template> getTemplates() {
		return templates;
	}
	public void setTemplates(Set<Template> templates) {
		this.templates = templates;
	}
	public List<Record> getRecords() {
		return records;
	}
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	public List<Transfer> getTransfers() {
		return transfers;
	}
	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}
	public Integer getSync() {
		return sync;
	}
	public void setSync(Integer sync) {
		this.sync = sync;
	}
	public AccountBook() {
		super();
	}

	public AccountBook(Integer abid, String abname, User user, Icon abicon,
			Set<Classification> classifications, Set<Shop> shops,
			Set<Account> accounts, Set<Template> templates,
			List<Record> records, List<Transfer> transfers, Integer sync) {
		super();
		this.abid = abid;
		this.abname = abname;
		this.user = user;
		this.abicon = abicon;
		this.classifications = classifications;
		this.shops = shops;
		this.accounts = accounts;
		this.templates = templates;
		this.records = records;
		this.transfers = transfers;
		this.sync = sync;
	}
}
