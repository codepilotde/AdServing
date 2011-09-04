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
package net.mad.ads.base.api.render;

import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;

public class DisplayTemplatesNames {
	public static void main (String [] args) {
		BannerFormat[] formats = BannerFormat.values();
		for (BannerFormat format : formats) {
			BannerType[] types = BannerType.values();
			for (BannerType type : types) {
				System.out.println(getTemplateName(format, type));
			}
		}
	}

	public static String getTemplateName (BannerFormat format, BannerType type) {
		String f = format.getCompoundName();
		String t = type.getName();
		
		return (f  + "_" + t).toLowerCase();
	}
}
