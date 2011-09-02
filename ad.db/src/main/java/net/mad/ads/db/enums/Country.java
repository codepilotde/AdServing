package net.mad.ads.db.enums;

import java.io.Serializable;

public enum Country implements Serializable {
	
	US("us"),	// USA
	IE("ie"),	// Irland
	AT("at"),	// Österreich
	AU("au"),	// Australien
	BE("be"),	// Belgien
	CA("ca"),	// Kanada
	FR("fr"),	// Frankreich
	DE("de"),	// Deutschland
	IT("it"),	// Italien
	ES("es"),	// Spanien
	CH("ch"),	// Schweiz
	UK("uk"),	// Englang
	NL("nl"),	// Holland
	
	ALL("all"),
	UNKNOWN("");
	
	private String code = "";
	private Country (String code) {
		this.code = code;
	}
	
	public String getCode () {
		return this.code;
	}
	
	public static Country getCountryForString (String country) {
		for (Country c : Country.values()) {
			if (c.getCode().equalsIgnoreCase(country)) {
				return c;
			}
		}
		
		return UNKNOWN;
	}
}
