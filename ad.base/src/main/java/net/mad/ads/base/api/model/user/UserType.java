package net.mad.ads.base.api.model.user;

import de.marx.common.tools.Strings;

public enum UserType {
	Publisher ("Publisher"),
	Advertiser ("Advertiser"),
	Unknown ("Unknown");
	
	private String name;
	
	private UserType (String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static UserType forName (String name) {
		if (Strings.isEmpty(name)) {
			return UserType.Unknown;
		}
		
		for (UserType t : values()) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		
		return UserType.Unknown;
	}
}
