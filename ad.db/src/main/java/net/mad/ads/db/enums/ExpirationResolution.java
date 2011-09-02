package net.mad.ads.db.enums;

import de.marx.common.tools.Strings;
/**
 * Auflösung für Zeiten
 * 
 * @author tmarx
 *
 */
public enum ExpirationResolution {
	DAY ("DAY"), 
	WEEK ("WEEK"),
	MONTH ("MONTH"),
	NONE ("NONE");
	
	private String name = null;
	
	private ExpirationResolution (String name) {
		this.name = name;
	}
	
	public String getName () {
		return this.name;
	}
	
	public static ExpirationResolution forName (String name) {
		if (Strings.isEmpty(name)) {
			return NONE;
		}
		
		for (ExpirationResolution res : values()) {
			if (res.getName().equalsIgnoreCase(name)) {
				return res;
			}
		}
		
		
		return NONE;
	}
}
