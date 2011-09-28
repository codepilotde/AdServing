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
package net.mad.ads.db.utils.mapper;

import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.definition.impl.banner.text.TextlinkBannerDefinition;
import net.mad.ads.db.definition.impl.banner.expandable.ExpandableImageBannerDefinition;
import net.mad.ads.db.definition.impl.banner.extern.ExternBannerDefinition;
import net.mad.ads.db.definition.impl.banner.flash.FlashBannerDefinition;
import net.mad.ads.db.enums.BannerType;

public class BannerTypeMapping {
	private static BannerTypeMapping INSTANCE = new BannerTypeMapping();
	private BannerTypeMapping () {
	}
	
	public static BannerTypeMapping getInstance () {
		return INSTANCE;
	}
	
	public String getBannerTypeAsString (BannerType type) {
		return type.toString();
	}
	
	public BannerType getBannerTypeFromString (String type) {
		return BannerType.valueOf(type);
	}
	
	/**
	 * Mapping von BannerType auf BannerDefinition
	 * @param type
	 * @return
	 */
	public BannerDefinition getDefinition (String type) {
		BannerType btype = BannerType.valueOf(type);
		BannerDefinition def = null;
		
		switch (btype) {
			case IMAGE:
				def = new ImageBannerDefinition();
				break;
			case EXTERN:
				def = new ExternBannerDefinition();
				break;
			case TEXTLINK:
				def = new TextlinkBannerDefinition();
				break;
			case FLASH:
				def = new FlashBannerDefinition();
				break;
			case EXPANDABLEIMAGE:
				def = new ExpandableImageBannerDefinition();
				break;
		}
		
		return def;
	}
}
