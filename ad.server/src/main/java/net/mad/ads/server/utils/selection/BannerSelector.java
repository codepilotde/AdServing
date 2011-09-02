package net.mad.ads.server.utils.selection;

import java.util.List;

import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.server.utils.context.AdContext;

public interface BannerSelector {
	public BannerDefinition selectBanner (List<BannerDefinition> banners, AdContext context);
}
