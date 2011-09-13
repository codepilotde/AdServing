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
package net.mad.ads.base.api.track.events;

public enum EventAttribute {
	ID ("id"),
	TYPE ("type"),
	TIME ("time"),
	USER ("user"),
	SITE ("site"),
	CAMPAIGN ("campaign"),
	BANNER_ID ("banner_id"),
	IP ("ip"),
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
