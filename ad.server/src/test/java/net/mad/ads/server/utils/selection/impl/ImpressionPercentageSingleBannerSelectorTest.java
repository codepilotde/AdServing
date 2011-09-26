/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.track.Criterion;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.events.EventType;
import net.mad.ads.base.api.track.events.TrackEvent;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.ViewExpirationConditionDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.ExpirationResolution;
import net.mad.ads.server.utils.RuntimeContext;

import org.junit.BeforeClass;
import org.junit.Test;

public class ImpressionPercentageSingleBannerSelectorTest {

	private static ImpressionPercentageSingleBannerSelector SELECTOR = new ImpressionPercentageSingleBannerSelector();
	private static List<BannerDefinition> banners = new ArrayList<BannerDefinition>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		RuntimeContext.setTrackService(new TrackingService() {
			@Override
			public void track(TrackEvent event) throws ServiceException {}
			@Override
			public void open(BaseContext context) throws ServiceException {}
			@Override
			public List<TrackEvent> list(Criterion criterion, EventType type,
					Date from, Date to) throws ServiceException { return null; }
			@Override
			public void delete(Criterion criterion, Date from, Date to)
					throws ServiceException {}

			@Override
			public void close() throws ServiceException {}
			@Override
			public void clear(Criterion criterion) throws ServiceException {}
			
			@Override
			public long count(Criterion criterion, EventType type, Date from, Date to)
					throws ServiceException {
				String bannerID = criterion.value;
				if (bannerID.equals("1")) {
					return 10;
				} else if (bannerID.equals("2")) {
					return 20;
				} else if (bannerID.equals("3")) {
					return 30;
				} else if (bannerID.equals("4")) {
					return 40;
				} else if (bannerID.equals("5")) {
					return 40;
				}
				
				return 0;
			}
		});
		for (int i = 1; i <= 4; i++) {
			BannerDefinition b = new ImageBannerDefinition();
			b.setId("" + i);
			ViewExpirationConditionDefinition vdef = new ViewExpirationConditionDefinition();
			vdef.getViewExpirations().put(ExpirationResolution.DAY, 100);
			b.addConditionDefinition(ConditionDefinitions.VIEW_EXPIRATION, vdef);
			
			banners.add(b);
		}
		BannerDefinition b = new ImageBannerDefinition();
		b.setId("5");
		banners.add(b);
		
	}

	@Test
	public void testSelectBanner() {
		BannerDefinition b = SELECTOR.selectBanner(banners, null);
		assertTrue("wrong banner selected", (b.getId().equals("1") || b.getId().equals("5")));
	}

}
