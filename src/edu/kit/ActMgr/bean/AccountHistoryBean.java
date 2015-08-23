package edu.kit.ActMgr.bean;

public class AccountHistoryBean 
{
	private String date;
	private double inflow;
	private double outflow;
	private double totalInflow;
	private double totalOutflow;
	private double surplus;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getInflow() {
		return inflow;
	}
	public void setInflow(double inflow) {
		this.inflow = inflow;
	}
	public double getOutflow() {
		return outflow;
	}
	public void setOutflow(double outflow) {
		this.outflow = outflow;
	}
	public double getTotalInflow() {
		return totalInflow;
	}
	public void setTotalInflow(double totalInflow) {
		this.totalInflow = totalInflow;
	}
	public double getTotalOutflow() {
		return totalOutflow;
	}
	public void setTotalOutflow(double totalOutflow) {
		this.totalOutflow = totalOutflow;
	}
	public double getSurplus() {
		return surplus;
	}
	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}
	
	public AccountHistoryBean() 
	{
		super();
	}
	
	public AccountHistoryBean(String date, double inflow, double outflow,double totalInflow, double totalOutflow, double surplus) 
	{
		super();
		this.date = date;
		this.inflow = inflow;
		this.outflow = outflow;
		this.totalInflow = totalInflow;
		this.totalOutflow = totalOutflow;
		this.surplus = surplus;
	}
}
