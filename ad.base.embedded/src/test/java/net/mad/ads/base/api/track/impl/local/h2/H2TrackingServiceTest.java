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
package net.mad.ads.base.api.track.impl.local.h2;

import net.mad.ads.base.api.EmbeddedBaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.events.ClickTrackEvent;
import net.mad.ads.base.api.track.events.TrackEvent;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.h2.jdbcx.JdbcDataSource;

public class H2TrackingServiceTest {

	private static TrackingService ts;
	
	@BeforeClass
	public static void init () {
		 JdbcDataSource ds = new JdbcDataSource();
		 ds.setURL("jdbc:h2:mem:test");
		 ds.setUser("sa");
		 ds.setPassword("sa");
		 
		 EmbeddedBaseContext context = new EmbeddedBaseContext();
		 context.put(EmbeddedBaseContext.EMBEDDED_TRACKING_DATASOURCE, ds);
		 
		 ts = new H2TrackingService();
		 try {
			ts.open(context);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void close () {
		if (ts != null)  {
			try {
				ts.close();
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Test
	public void test_one () {
		TrackEvent ev = new ClickTrackEvent();
		ev.setBannerId("b1");
		ev.setCampaign("c1");
		ev.setId("id 1");
		ev.setIp("ip 1");
		ev.setSite("s1");
		ev.setTime(System.currentTimeMillis());
		ev.setUser("u1");
		
		try {
			ts.track(ev);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
