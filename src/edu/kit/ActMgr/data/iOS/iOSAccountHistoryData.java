package edu.kit.ActMgr.data.iOS;

import edu.kit.ActMgr.domain.AccountHistory;

public class iOSAccountHistoryData 
{
	private int ahid;
	private long timeInterval;
	private double aout;
	private double ain;
	private int aid;
	private int sync;
	
	public int getAhid() {
		return ahid;
	}

	public long getTimeInterval() {
		return timeInterval;
	}

	public double getAout() {
		return aout;
	}

	public double getAin() {
		return ain;
	}

	public int getAid() {
		return aid;
	}

	public void setAhid(int ahid) {
		this.ahid = ahid;
	}

	public void setTimeInterval(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	public void setAout(double aout) {
		this.aout = aout;
	}

	public void setAin(double ain) {
		this.ain = ain;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getSync() {
		return sync;
	}

	public void setSync(int sync) {
		this.sync = sync;
	}

	public iOSAccountHistoryData(AccountHistory history)
	{
		super();
		this.ahid = history.getAhid();
		this.timeInterval = history.getDate().getTime();
		this.aout = history.getAout();
		this.ain = history.getAin();
		this.aid = history.getAccount().getAid();
		this.sync=history.getSync();
	}
}
