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
package net.mad.ads.db.definition;

import net.mad.ads.common.code.Base16;

import com.google.common.base.Strings;


/**
 * The AdSlot is the combination of a page and an unique place
 * 
 * @author thmarx
 *
 */
public class AdSlot {
	
	private String site;
	private String place;
	
	public AdSlot (String site, String place) {
		this.site = site;
		this.place = place;
	}
	
	public String getSite() {
		return site;
	}

	public String getPlace() {
		return place;
	}

	@Override
	public String toString() {
//		new Base
		return Base16.encode(site) + "-" + Base16.encode(place);
	}
	
	public static AdSlot fromString (String uuid) throws IllegalArgumentException{
		if (Strings.isNullOrEmpty(uuid)) {
            throw new IllegalArgumentException("Invalid AdUUID string: " + uuid);
        }
		String[] components = uuid.split("-");
        if (components.length != 3) {
            throw new IllegalArgumentException("Invalid AdUUID string: " + uuid);
        }
        
        AdSlot aduuid = new AdSlot(Base16.decode(components[0]), Base16.decode(components[1]));
        
        return aduuid;
	}


	public static void main (String []args) throws Exception {
		AdSlot uuid = new AdSlot("1", "2");
		System.out.println(uuid.toString());
		AdSlot uuid2 = AdSlot.fromString(uuid.toString());
		
		System.out.println(uuid2.getSite());
		System.out.println(uuid2.getPlace());
	}
}
