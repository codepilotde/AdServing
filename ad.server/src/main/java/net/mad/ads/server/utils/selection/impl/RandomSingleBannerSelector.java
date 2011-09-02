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
