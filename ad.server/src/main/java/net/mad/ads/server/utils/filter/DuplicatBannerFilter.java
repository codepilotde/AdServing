package net.mad.ads.server.utils.filter;

import java.util.Calendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.ViewExpirationConditionDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.ExpirationResolution;
import net.mad.ads.server.utils.RuntimeContext;

/**
 * Dieser Filter verhindert das doppelte Anzeigen eines Banners auf der selben 
 * Seite für den selben Benutzer
 * 
 * die request_id ist von allen AdRequest die durch einen Pageview erzeugt werden identisch
 * 
 * @author thmarx
 *
 */
public class DuplicatBannerFilter implements Predicate<BannerDefinition> {

	private static final Logger logger = LoggerFactory.getLogger(DuplicatBannerFilter.class);
	
	private String requestID = null;
	
	public DuplicatBannerFilter (String request_id) {
		this.requestID = request_id;
	}
	
	@Override
	public boolean apply(BannerDefinition banner) {
		logger.debug("Requestid: " + RuntimeContext.getRequestBanners().containsKey("pv" + requestID + "_" + banner.getId()));
		if (RuntimeContext.getRequestBanners().containsKey("pv" + requestID + "_" + banner.getId())) {
			return false;
		}
		
		return true;
	}

}
