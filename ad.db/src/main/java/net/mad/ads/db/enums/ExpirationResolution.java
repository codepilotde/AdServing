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

import com.google.common.base.Strings;


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
		if (Strings.isNullOrEmpty(name)) {
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
