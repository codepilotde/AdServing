package net.mad.ads.base.api.track.events;

public enum EventType {
	IMPRESSION ("impression"), 
	CLICK ("click"),
	UNKNOWN ("unknown");
	
	private String name;
	
	private EventType (String name) {
		this.name = name;
	}
	
	public String getName () {
		return this.name;
	}
	
	public static EventType forName (String name) {
		if (name == null || name.equals("")) {
			return EventType.UNKNOWN;
		}
		
		for (EventType type : values()) {
			if (type.getName().equals(name)) {
				return type;
			}
		}
		
		return EventType.UNKNOWN;
	}
}
