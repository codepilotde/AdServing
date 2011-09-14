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
package net.mad.ads.server.utils.filter;

import java.util.Calendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.track.Criterion;
import net.mad.ads.base.api.track.events.EventType;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.ViewExpirationConditionDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.ExpirationResolution;
import net.mad.ads.server.utils.RuntimeContext;

/**
 * Filter zum entfernen von Bannern, die die konfigurierte Anzeigehäufigkeit
 * für einen bestimmten Zeitraum schon erreicht oder überschritten haben  
 * 
 * @author tmarx
 *
 */
public class ViewExpirationFilter implements Predicate<BannerDefinition> {

	private static final Logger logger = LoggerFactory.getLogger(ViewExpirationFilter.class);
	
//	private static final ViewExpirationFilter FILTER = new ViewExpirationFilter();
//	
//	public static final Predicate<BannerDefinition> getFilter () {
//		return FILTER;
//	}
	
	@Override
	public boolean apply(BannerDefinition banner) {
		// Banner enth�lt keine View-Beschr�nkung
		if (!banner.hasConditionDefinition(ConditionDefinitions.VIEW_EXPIRATION)){
			return true;
		} else if (RuntimeContext.getTrackService() == null) {
			return true;
		}
		
		
		/*
		 * 1. Prüfen, ob die Impressions f�r diesen Monat schon erreicht wurden
		 * 2. Prüfen, ob die Impressions f�r diese Woche schon erreicht wurden
		 * 3. Prüfen, ob die Impressions f�r diesen Tag schon erreicht wurden
		 */
		
		ViewExpirationConditionDefinition def = (ViewExpirationConditionDefinition) banner.getConditionDefinition(ConditionDefinitions.VIEW_EXPIRATION);
		
		try {
			if (def.getViewExpirations().containsKey(ExpirationResolution.MONTH)) {
				Calendar from = Calendar.getInstance(Locale.GERMANY);
				from.set(Calendar.HOUR_OF_DAY, 0);
				from.set(Calendar.MINUTE, 0);
				from.set(Calendar.SECOND, 0);
				from.set(Calendar.MILLISECOND, 0);
				
				Calendar to = Calendar.getInstance(Locale.GERMANY);
				to.set(Calendar.HOUR_OF_DAY, 23);
				to.set(Calendar.MINUTE, 59);
				to.set(Calendar.SECOND, 59);
				to.set(Calendar.MILLISECOND, 999);
				
				
				int maxviews = def.getViewExpirations().get(ExpirationResolution.MONTH);
				long impressions = RuntimeContext.getTrackService().count(new Criterion(Criterion.Criteria.Banner, banner.getId()), EventType.IMPRESSION, from.getTime(), to.getTime());
				
				if (impressions <= maxviews) {
					return true;
				}
			}
			if (def.getViewExpirations().containsKey(ExpirationResolution.WEEK)) {
				Calendar from = Calendar.getInstance(Locale.GERMANY);
				from.set(Calendar.HOUR_OF_DAY, 0);
				from.set(Calendar.MINUTE, 0);
				from.set(Calendar.SECOND, 0);
				from.set(Calendar.MILLISECOND, 0);
				from.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				
				Calendar to = Calendar.getInstance(Locale.GERMANY);
				to.set(Calendar.HOUR_OF_DAY, 23);
				to.set(Calendar.MINUTE, 59);
				to.set(Calendar.SECOND, 59);
				to.set(Calendar.MILLISECOND, 999);
				to.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				
				int maxviews = def.getViewExpirations().get(ExpirationResolution.MONTH);
				long impressions = RuntimeContext.getTrackService().count(new Criterion(Criterion.Criteria.Banner, banner.getId()), EventType.IMPRESSION, from.getTime(), to.getTime());
				
				if (impressions <= maxviews) {
					return true;
				}
			}
			if (def.getViewExpirations().containsKey(ExpirationResolution.DAY)) {
				Calendar from = Calendar.getInstance(Locale.GERMANY);
				from.set(Calendar.HOUR_OF_DAY, 0);
				from.set(Calendar.MINUTE, 0);
				from.set(Calendar.SECOND, 0);
				from.set(Calendar.MILLISECOND, 0);
				from.set(Calendar.DAY_OF_MONTH, 1);
				
				Calendar to = Calendar.getInstance(Locale.GERMANY);
				
				int lastDate = to.getActualMaximum(Calendar.DATE);
				to.set(Calendar.DATE, lastDate); 
				to.set(Calendar.HOUR_OF_DAY, 23);
				to.set(Calendar.MINUTE, 59);
				to.set(Calendar.SECOND, 59);
				to.set(Calendar.MILLISECOND, 999);
				
				
				int maxviews = def.getViewExpirations().get(ExpirationResolution.MONTH);
				long impressions = RuntimeContext.getTrackService().count(new Criterion(Criterion.Criteria.Banner, banner.getId()), EventType.IMPRESSION, from.getTime(), to.getTime());
				
				if (impressions <= maxviews) {
					return true;
				}
			}
		} catch (ServiceException e) {
			logger.error("", e);
		}
		
		return false;
	}

}
