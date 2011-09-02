package net.mad.ads.base.api.model.user;

import de.marx.common.tools.Strings;

public enum NotificationType {
	DEFAULT("Default");
	
	private String name;
	private NotificationType (String name) {
		this.name = name;
	}
	
	public String getName () {
		return this.name;
	}
	
	public static NotificationType forName (String name) {
		if (Strings.isEmpty(name)) {
			return NotificationType.DEFAULT;
		}
		
		for (NotificationType m : values()) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		
		return NotificationType.DEFAULT;
	}
}
