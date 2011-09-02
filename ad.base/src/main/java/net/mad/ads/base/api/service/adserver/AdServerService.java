package net.mad.ads.base.api.service.adserver;

import net.mad.ads.db.definition.BannerDefinition;

public interface AdServerService {
	public boolean add (BannerDefinition banner);
	
	public boolean delete (String id);
}
