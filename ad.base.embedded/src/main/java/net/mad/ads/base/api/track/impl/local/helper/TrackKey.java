/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
