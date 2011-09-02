package net.mad.ads.base.api.track.events;

public enum EventAttribute {
	ID ("id"),
	TYPE ("type"),
	TIME ("time"),
	USER ("user"),
	SITE ("site"),
	BANNER_ID ("banner_id"),
	UNKNOWN ("unknown");
	
	private String name;
	
	private EventAttribute (String name) {
		this.name = name;
	}
	
	public String getName () {
		return this.name;
	}
	
	public static EventAttribute forName (String name) {
		if (name == null || name.equals("")) {
			return EventAttribute.UNKNOWN;
		}
		
		for (EventAttribute type : values()) {
			if (type.getName().equals(name)) {
				return type;
			}
		}
		
		return EventAttribute.UNKNOWN;
	}
}
