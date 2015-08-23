package edu.kit.ActMgr.data;

public class EarnSpendReportData 
{
	private String title;
	private String subtitle;
	private String [] categories;
	private double [] earnData;
	private double [] spendData;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String[] getCategories() {
		return categories;
	}
	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	public double[] getEarnData() {
		return earnData;
	}
	public void setEarnData(double[] earnData) {
		this.earnData = earnData;
	}
	public double[] getSpendData() {
		return spendData;
	}
	public void setSpendData(double[] spendData) {
		this.spendData = spendData;
	}
	
	public EarnSpendReportData() {
		super();
	}
	
	public EarnSpendReportData(String title, String subtitle,
			String[] categories, double[] earnData, double[] spendData) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.categories = categories;
		this.earnData = earnData;
		this.spendData = spendData;
	}
}
