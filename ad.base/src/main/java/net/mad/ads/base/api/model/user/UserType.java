/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
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

package net.mad.ads.base.api.model.user;

import net.mad.ads.common.util.Strings;



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
