package edu.kit.ActMgr.data;

public class AssetsReportData 
{
	private String [] categories;
	private double [] netAssets;
	private double [] totalAssets;
	private double [] totalLiabilities;
	
	public String[] getCategories() {
		return categories;
	}
	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	public double[] getNetAssets() {
		return netAssets;
	}
	public void setNetAssets(double[] netAssets) {
		this.netAssets = netAssets;
	}
	public double[] getTotalAssets() {
		return totalAssets;
	}
	public void setTotalAssets(double[] totalAssets) {
		this.totalAssets = totalAssets;
	}
	public double[] getTotalLiabilities() {
		return totalLiabilities;
	}
	public void setTotalLiabilities(double[] totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}
	
	public AssetsReportData() {
		super();
	}
	
	public AssetsReportData(String[] categories, double[] netAssets,
			double[] totalAssets, double[] totalLiabilities) {
		super();
		this.categories = categories;
		this.netAssets = netAssets;
		this.totalAssets = totalAssets;
		this.totalLiabilities = totalLiabilities;
	}
	
}
