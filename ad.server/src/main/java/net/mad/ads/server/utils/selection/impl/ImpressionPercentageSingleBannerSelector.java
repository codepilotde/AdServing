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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Collections2;

import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.ViewExpirationConditionDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.ExpirationResolution;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.server.utils.context.AdContext;
import net.mad.ads.server.utils.selection.BannerSelector;

/**
 * Das Banner wird aufgrund der prozentualen Anzeige-Häufigkeit ausgewählt.
 * 
 * 1. ermitteln, wie viel Prozent der Impressions schon erreicht wurde
 * 2. das Banner wählen, das prozentual am wenigsten angezeigt wurde
 * 
 * Verhalten für Banner ohne Beschränkung der Impressionen:
 * 1. der kleinste ermittelte Prozentwert wird verwendet
 * 
 * @author thmarx
 *
 */
public class ImpressionPercentageSingleBannerSelector implements BannerSelector {
	
	private static final Logger logger = LoggerFactory.getLogger(ImpressionPercentageSingleBannerSelector.class);

	private static final BannerSelector RANDOM_SELECTOR = new RandomSingleBannerSelector();
	
	@Override
	public BannerDefinition selectBanner(List<BannerDefinition> banners, AdContext context) {

		List<BannerDecorator> decorators = new ArrayList<ImpressionPercentageSingleBannerSelector.BannerDecorator>();
		for (BannerDefinition def : banners) {
			decorators.add(new BannerDecorator(def));
		}
		
		// Sortieren
		Collections.sort(decorators, new Comparator<BannerDecorator>() {

			@Override
			public int compare(BannerDecorator o1, BannerDecorator o2) {
				float p1 = getPercentage(o1);
				float p2 = getPercentage(o2);
				
				o1.setPercentage(p1);
				o2.setPercentage(p2);
				
				if (p1 > p2) {
					return -1;
				} else if (p2 > p1) {
					return 1;
				}
				return 0;
			}

			
		});
		Collections.reverse(decorators);
		
		/*
		 * Alle Banner ermittel die in Frage kommen, also die, die noch nicht so häufig angezeigt wurden
		 */
		List<BannerDefinition> bannerList = new ArrayList<BannerDefinition>();
		
		float sma = -2f;
		for (BannerDecorator bd : decorators) {
			if (bd.getPercentage() == -1f) {
				bannerList.add(bd.getBanner());
			} else if (sma == -2f) {
				sma = bd.getPercentage();
				bannerList.add(bd.getBanner());
			} else if (bd.getPercentage() == sma) {
				bannerList.add(bd.getBanner());
			} else if (bd.getPercentage() > sma) {
				break;
			}
		}

		return RANDOM_SELECTOR.selectBanner(bannerList, context);
	}
	
	private float getPercentage(BannerDecorator decorator) {
		try {
			BannerDefinition o1 = decorator.getBanner();
			if (decorator.hasPercentage()) {
				return decorator.getPercentage();
			}
			if (o1.hasConditionDefinition(ConditionDefinitions.VIEW_EXPIRATION)) {
				ViewExpirationConditionDefinition def = (ViewExpirationConditionDefinition) o1.getConditionDefinition(ConditionDefinitions.VIEW_EXPIRATION);
				if (def.getViewExpirations().containsKey(ExpirationResolution.DAY)) {
	                Calendar from = Calendar.getInstance(Locale.GERMANY);
	                from.set(Calendar.HOUR_OF_DAY, 0);
	                from.set(Calendar.MINUTE, 0);
	                from.set(Calendar.SECOND, 0);
	                from.set(Calendar.MILLISECOND, 0);
	                
	                Calendar to = Calendar.getInstance(Locale.GERMANY);
	                to.set(Calendar.HOUR_OF_DAY, 0);
	                to.set(Calendar.MINUTE, 0);
	                to.set(Calendar.SECOND, 0);
	                to.set(Calendar.MILLISECOND, 0);
	                to.add(Calendar.DAY_OF_WEEK, 1);
					
					int maxViewCount = def.getViewExpirations().get(ExpirationResolution.DAY);
					int viewCount = RuntimeContext.getTrackService().countImpressions(o1.getId(), from.getTime(), to.getTime());
					
					float percent = 0.0f;
					if (maxViewCount > 0) {
						percent = (float)viewCount / (float)maxViewCount;
					}
					return percent;
				} else {
					return -1;
				}
			} else {
				return -1;
			}
			
		} catch (Exception e) {
			logger.error("", e);
		}
		return 0.0f;
	}
	
	class BannerDecorator {
		private BannerDefinition banner = null;
		private float percentage = -1.0f;
		
		public BannerDecorator (BannerDefinition banner) {
			this.banner = banner;
		}

		public float getPercentage() {
			return percentage;
		}

		public void setPercentage(float percentage) {
			this.percentage = percentage;
		}

		public BannerDefinition getBanner() {
			return banner;
		}
		
		public boolean hasPercentage () {
			return percentage != -1f;
		}
	}

}
