package edu.kit.ActMgr.data;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ActMgr.bean.TransferBean;

public class DateTransfer 
{
	private String date;
	private List<TransferBean> transfers=new ArrayList<TransferBean>();
	private double money;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<TransferBean> getTransfers() {
		return transfers;
	}
	public void setTransfers(List<TransferBean> transfers) {
		this.transfers = transfers;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	
	public DateTransfer(String date, List<TransferBean> transfers, double money) {
		super();
		this.date = date;
		this.transfers = transfers;
		this.money = money;
	}
	public DateTransfer() {
		super();
	}
}
