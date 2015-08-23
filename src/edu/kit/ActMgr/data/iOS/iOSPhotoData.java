package edu.kit.ActMgr.data.iOS;

import edu.kit.ActMgr.domain.Photo;

public class iOSPhotoData 
{
	private int pid;
	private long timeInterval;
	private int sync;
	private int abid;
	
	public int getPid() {
		return pid;
	}

	public long getTimeInterval() {
		return timeInterval;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public void setTimeInterval(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	public int getSync() {
		return sync;
	}

	public void setSync(int sync) {
		this.sync = sync;
	}

	public iOSPhotoData() {
		super();
	}

	public int getAbid() {
		return abid;
	}

	public void setAbid(int abid) {
		this.abid = abid;
	}

	public iOSPhotoData(Photo photo) {
		super();
		this.pid = photo.getPid();
		this.timeInterval = photo.getUpload().getTime();
		this.sync=photo.getSync();
		this.abid=photo.getAccountBook().getAbid();
	}

}
