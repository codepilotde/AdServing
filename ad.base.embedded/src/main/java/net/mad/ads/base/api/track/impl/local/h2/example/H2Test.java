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
package net.mad.ads.base.api.track.impl.local.h2.example;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.h2.jdbcx.JdbcDataSource;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.EmbeddedBaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.track.Criterion;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.events.ClickTrackEvent;
import net.mad.ads.base.api.track.events.EventAttribute;
import net.mad.ads.base.api.track.events.EventType;
import net.mad.ads.base.api.track.events.ImpressionTrackEvent;
import net.mad.ads.base.api.track.events.TrackEvent;
import net.mad.ads.base.api.track.impl.local.bdb.BDBTrackingService;
import net.mad.ads.base.api.track.impl.local.h2.H2TrackingService;
import net.mad.ads.base.api.utils.DateHelper;

/**
 * 
 * @author tmarx
 */
public class H2Test {
	public static void main(String[] args) throws Exception {

		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:mem:test");
		ds.setUser("sa");
		ds.setPassword("sa");

		EmbeddedBaseContext context = new EmbeddedBaseContext();
		context.put(EmbeddedBaseContext.EMBEDDED_TRACKING_DATASOURCE, ds);

		TrackingService ts = new H2TrackingService();

		ts.open(context);

		trackData(ts);

		Calendar from = Calendar.getInstance(Locale.GERMANY);
		from.set(Calendar.MONTH, Calendar.JANUARY);
		from.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		from.set(Calendar.HOUR_OF_DAY, 0);
		from.set(Calendar.MINUTE, 0);
		from.set(Calendar.SECOND, 0);
		from.set(Calendar.MILLISECOND, 0);

		Calendar to = Calendar.getInstance(Locale.GERMANY);
		to.set(Calendar.MONTH, Calendar.JANUARY);
		to.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// to.set(Calendar.HOUR_OF_DAY, 23);
		// to.set(Calendar.MINUTE, 59);
		// to.set(Calendar.SECOND, 59);
		// to.set(Calendar.MILLISECOND, 999);
		to.set(Calendar.HOUR_OF_DAY, 0);
		to.set(Calendar.MINUTE, 0);
		to.set(Calendar.SECOND, 0);
		to.set(Calendar.MILLISECOND, 0);
		to.add(Calendar.DAY_OF_WEEK, 1);

		// System.out.println();
		// System.out.println(DateHelper.format(from.getTime()));
		// System.out.println(DateHelper.format(to.getTime()));
		// System.out.println();

		// System.out.println(ts.countClicks("b1", from.getTime(),
		// to.getTime()));
		// System.out.println(ts.countImpressions("b1", from.getTime(),
		// to.getTime()));

		List<TrackEvent> list = ts.list(new Criterion(Criterion.Criteria.Site,
				"demo Site"), null, from.getTime(), to.getTime());
		System.out.println(list.size());

		System.out.println(ts.count(new Criterion(Criterion.Criteria.Banner,
				"b1"), EventType.CLICK, from.getTime(), to.getTime()));
		System.out.println(ts.count(new Criterion(Criterion.Criteria.Banner,
				"b1"), EventType.IMPRESSION, from.getTime(), to.getTime()));

		ts.close();
	}

	private static void trackData(TrackingService ts) throws ServiceException {
		Calendar created = Calendar.getInstance(Locale.GERMANY);
		created.set(Calendar.MONTH, Calendar.JANUARY);
		created.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		TrackEvent event = new ClickTrackEvent();
		event.put(EventAttribute.TIME, DateHelper.format(created.getTime()));
		event.setTime(created.getTime().getTime());
		event.setCampaign("c1");
		// System.out.println(DateHelper.format(created.getTime()));
		event.setSite("demo Site");
		event.put(EventAttribute.BANNER_ID, "b1");
		event.setUser("user1");
		event.setId("id 1");
		event.setIp("ip1");
		ts.track(event);

		created.add(Calendar.HOUR_OF_DAY, 1);
		event = new ClickTrackEvent();
		event.setId("id 2");
		event.setCampaign("c1");
		event.setIp("ip1");
		event.put(EventAttribute.TIME, DateHelper.format(created.getTime()));
		event.setTime(created.getTime().getTime());
		// System.out.println(DateHelper.format(created.getTime()));
		event.setSite("demo Site");
		event.put(EventAttribute.BANNER_ID, "b1");
		event.setUser("user1");
		ts.track(event);

		created.add(Calendar.HOUR_OF_DAY, 1);
		event = new ImpressionTrackEvent();
		event.setId("id 3");
		event.setIp("ip1");
		event.setCampaign("c1");
		event.put(EventAttribute.TIME, DateHelper.format(created.getTime()));
		event.setTime(created.getTime().getTime());
		// System.out.println(DateHelper.format(created.getTime()));
		event.setSite("demo Site");
		event.put(EventAttribute.BANNER_ID, "b1");
		event.setUser("user1");
		ts.track(event);
		event.setUser("user2");
		event.setId("id 4");
		ts.track(event);
	}
}
