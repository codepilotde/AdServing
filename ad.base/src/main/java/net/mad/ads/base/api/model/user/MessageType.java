package net.mad.ads.base.api.model.user;

import de.marx.common.tools.Strings;

public enum MessageType {
	DEFAULT("Default");
	
	private String name;
	private MessageType (String name) {
		this.name = name;
	}
	
	public String getName () {
		return this.name;
	}
	
	public static MessageType forName (String name) {
		if (Strings.isEmpty(name)) {
			return MessageType.DEFAULT;
		}
		
		for (MessageType m : values()) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		
		return MessageType.DEFAULT;
	}
}
