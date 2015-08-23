package edu.kit.ActMgr.data;

import java.util.Date;
import java.util.List;

/**
 * @author limeng
 * 历史汇率记录数据
 */
public class RateHistory 
{
	private Date start;
	private List<Double> data;
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
		this.data = data;
	}
	public RateHistory(Date start, List<Double> data) {
		super();
		this.start = start;
		this.data = data;
	}
	public RateHistory() {
		super();
	}
}
