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
package net.mad.ads.server.utils.selection.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.server.utils.context.AdContext;
import net.mad.ads.server.utils.selection.BannerSelector;

public class RandomSingleBannerSelector implements BannerSelector {

	public static final Random random = new Random(System.currentTimeMillis());
	
	@Override
	public BannerDefinition selectBanner(List<BannerDefinition> banners, AdContext context) {
		BannerDefinition banner = null;
		
		if (banners != null && banners.size() >= 2) {
			int i = random.nextInt(banners.size());
			banner = banners.get(i);
		} else if (banners != null && banners.size() == 1) {
			banner = banners.get(0);
		}
		
		
		return banner;
	}
}
