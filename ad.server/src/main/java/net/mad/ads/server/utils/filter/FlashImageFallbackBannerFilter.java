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
 * Dieser Filter filtert FlashBanner aus dem Ergebnis, die kein Fallback Image haben
 * 
 * @author thmarx
 *
 */
public class FlashImageFallbackBannerFilter implements Predicate<BannerDefinition> {

	private static final Logger logger = LoggerFactory.getLogger(FlashImageFallbackBannerFilter.class);
	
	public FlashImageFallbackBannerFilter () {
	}
	
	@Override
	public boolean apply(BannerDefinition banner) {
		if (banner instanceof FlashBannerDefinition) {
			return ((FlashBannerDefinition)banner).getFallbackImageUrl() != null;
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
