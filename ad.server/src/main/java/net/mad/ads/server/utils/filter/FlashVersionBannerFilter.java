package net.mad.ads.server.utils.filter;

import java.util.Calendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.ViewExpirationConditionDefinition;
import net.mad.ads.db.definition.impl.banner.flash.FlashBannerDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.ExpirationResolution;
import net.mad.ads.server.utils.RuntimeContext;

/**
 * Dieser Filter fitlert FlashBanner aus dem Ergebnis, die eine höhere Flashversion benötigen
 * als sie im Request übegeben wurde.
 * 
 * @author thmarx
 *
 */
public class FlashVersionBannerFilter implements Predicate<BannerDefinition> {

	private static final Logger logger = LoggerFactory.getLogger(FlashVersionBannerFilter.class);
	
	private int version = -1;
	
	public FlashVersionBannerFilter (int version) {
		this.version = version;
	}
	
	@Override
	public boolean apply(BannerDefinition banner) {
		if (banner instanceof FlashBannerDefinition) {
			int minFlash = ((FlashBannerDefinition)banner).getMinFlashVersion();
			
			if (minFlash > version) {
				return false;
			}
			return true;
		}
		return false;
//		logger.debug("Requestid: " + RuntimeContext.getRequestBanners().containsKey("pv" + requestID + "_" + banner.getId()));
//		if (RuntimeContext.getRequestBanners().containsKey("pv" + requestID + "_" + banner.getId())) {
//			return false;
//		}
//		
//		return true;
	}

}
