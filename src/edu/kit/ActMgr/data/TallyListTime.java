package edu.kit.ActMgr.data;

import java.util.ArrayList;
import java.util.List;

/**
 * TallyList中显示的年和月
 * @author lm2343635
 *
 */
public class TallyListTime 
{
	private int year;
	private List<Integer> months=new ArrayList<Integer>();
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<Integer> getMonths() {
		return months;
	}
	public void setMonths(List<Integer> months) {
		this.months = months;
	}
	
	public TallyListTime() {
		super();
	}
	
	public TallyListTime(int year, List<Integer> months) {
		super();
		this.year = year;
		this.months = months;
	}
}
