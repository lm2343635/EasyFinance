package edu.kit.ActMgr.data;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ActMgr.bean.RecordBean;

public class DateRecord 
{
	private String date;
	private List<RecordBean> records=new ArrayList<RecordBean>();
	private double spend;
	private double earn;

	public List<RecordBean> getRecords() {
		return records;
	}

	public void setRecords(List<RecordBean> records) {
		this.records = records;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getSpend() {
		return spend;
	}

	public void setSpend(double spend) {
		this.spend = spend;
	}

	public double getEarn() {
		return earn;
	}

	public void setEarn(double earn) {
		this.earn = earn;
	}

	public DateRecord(String date, List<RecordBean> records, double spend,
			double earn) {
		super();
		this.date = date;
		this.records = records;
		this.spend = spend;
		this.earn = earn;
	}

	public DateRecord() {
		super();
	}
	
}
