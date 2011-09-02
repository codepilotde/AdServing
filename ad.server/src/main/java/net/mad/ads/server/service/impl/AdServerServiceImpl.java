package net.mad.ads.server.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.base.api.service.adserver.AdServerService;


public class AdServerServiceImpl implements AdServerService {

	private static final Logger logger = LoggerFactory.getLogger(AdServerServiceImpl.class);
	
	@Override
	public boolean add(BannerDefinition banner) {
		try {
			RuntimeContext.getAdDB().addBanner(banner);
			
			return true;
		} catch (Exception e) {
			logger.error("error add Banner: " + banner.getId(), e);
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		try {
			RuntimeContext.getAdDB().deleteBanner(id);
			
			return true;
		} catch (Exception e) {
			logger.error("error delete Banner: " + id, e);
		}
		return false;
	}

}
