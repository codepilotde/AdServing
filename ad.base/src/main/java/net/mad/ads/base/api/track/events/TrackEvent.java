/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
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
package net.mad.ads.base.api.track.events;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashMap;

public class TrackEvent extends EnumMap<EventAttribute, String> implements Serializable{
	
	private long time;
	
	public TrackEvent() {
		super(EventAttribute.class);
	}
	
	
	public EventType getType () {
		if (containsKey(EventAttribute.TYPE)) {
			String type = get(EventAttribute.TYPE);
			
			return EventType.forName(type);
		}
		
		return EventType.UNKNOWN;
	}
	
	public String getSite () {
		return get(EventAttribute.SITE);
	}
	public void setSite (String site) {
		put(EventAttribute.SITE, site);
	}
	
	public String getCampaign () {
		return get(EventAttribute.CAMPAIGN);
	}
	public void setCampaign (String Campaign) {
		put(EventAttribute.CAMPAIGN, Campaign);
	}
	
	public String getId () {
		return get(EventAttribute.ID);
	}
	public void setId (String id) {
		put(EventAttribute.ID, id);
	}
	
	public String getIp () {
		return get(EventAttribute.IP);
	}
	public void setIp (String ip) {
		put(EventAttribute.IP, ip);
	}
	
	public String getUser () {
		return get(EventAttribute.USER);
	}
	public void setUser (String user) {
		put(EventAttribute.USER, user);
	}
	
	public String getBannerId () {
		return get(EventAttribute.BANNER_ID);
	}
	public void setBannerId (String bannerid) {
		put(EventAttribute.BANNER_ID, bannerid);
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}
	
	
}
