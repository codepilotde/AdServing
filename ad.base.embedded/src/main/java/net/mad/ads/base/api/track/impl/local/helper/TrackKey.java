package net.mad.ads.base.api.track.impl.local.helper;

import java.io.Serializable;

public class TrackKey implements Comparable<TrackKey>, Serializable{
	
	private String key;
	private long time;
	
	public TrackKey () {
		
	}
	
	public TrackKey (String key, long time) {
		this.key = key;
		this.time = time;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TrackKey) {
			TrackKey ok = (TrackKey)obj;
			if (ok.equals(key) && time == ok.getTime()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public int compareTo(TrackKey o) {
		System.out.println("co,mp");
		Long l1 = getTime();
		Long l2 = o.getTime();
		
		int compare = l1.compareTo(l2);
		if (compare == 0){
			compare = -1;
		}
		
		return compare;
	}
}
