package edu.kit.ActMgr.data;

public class ProportionReportData 
{
	private String subtitle;
	private String [] categories;
	private double [] inflows;
	private double [] outflows;
	
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
	public double[] getInflows() {
		return inflows;
	}
	public void setInflows(double[] inflows) {
		this.inflows = inflows;
	}
	public double[] getOutflows() {
		return outflows;
	}
	public void setOutflows(double[] outflows) {
		this.outflows = outflows;
	}
	
	public ProportionReportData(String subtitle,String[] categories, double[] inflows, double[] outflows) 
	{
		super();
		this.subtitle = subtitle;
		this.categories = categories;
		this.inflows = inflows;
		this.outflows = outflows;
	}
	
	public ProportionReportData() {
		super();
	}

}
