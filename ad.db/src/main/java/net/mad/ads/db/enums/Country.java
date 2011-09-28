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
