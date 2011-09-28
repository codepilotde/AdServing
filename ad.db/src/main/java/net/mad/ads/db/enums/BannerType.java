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

/**
 * Bannertypen
 * 
 * Text, Image
 * 
 * @author tmarx
 *
 */
public enum BannerType implements Serializable {
	// Einfaches Banner, das nur den Bannercode eines externen Anbieters wie Zanox oder AdSense Banners enthält
	EXTERN("Extern", 1),
	// Ein ImageBanner
	IMAGE("Image", 2),
	TEXTLINK("Textlink", 3),
	FLASH("Flash", 4),
	EXPANDABLEIMAGE ("ExpandableImage", 5);
	
	private int type = 0;
	private String name = "";
	private BannerType(String name, int type) {
		this.type = type;
		this.name = name;
	}
	public final int getType() {
		return type;
	}
	public final String getTypeAsString() {
		return String.valueOf(type);
	}
	public final String getName () {
		return this.name;
	}
	
	public static BannerType forType (int type) {
		for (BannerType t : BannerType.values()) {
			if (t.getType() == type) {
				return t;
			}
		}
		return null;
	}
}
