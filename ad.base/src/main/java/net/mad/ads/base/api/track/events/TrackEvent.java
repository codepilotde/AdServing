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
	
	public String getId () {
		return get(EventAttribute.ID);
	}
	public void setId (String id) {
		put(EventAttribute.ID, id);
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
