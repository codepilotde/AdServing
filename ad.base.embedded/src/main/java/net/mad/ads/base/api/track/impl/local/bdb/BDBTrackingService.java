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
package net.mad.ads.base.api.track.impl.local.bdb;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.EmbeddedBaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.track.Criterion;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.events.EventAttribute;
import net.mad.ads.base.api.track.events.EventType;
import net.mad.ads.base.api.track.events.TrackEvent;
import net.mad.ads.base.api.track.impl.local.bdb.db.TrackDB;
import net.mad.ads.base.api.track.impl.local.bdb.db.TrackView;
import net.mad.ads.base.api.track.impl.local.helper.TrackKey;
import net.mad.ads.base.api.utils.DateHelper;

public class BDBTrackingService implements TrackingService {

	private String envDir = "./tmp";
	
	private TrackDB trackdb = null;
	private TrackView trackView = null;
    
	
	@Override
	public void open(BaseContext context) throws ServiceException {
		envDir = context.get(EmbeddedBaseContext.EMBEDDED_DB_DIR, String.class, null);
		
		
		trackdb = new TrackDB (envDir);
		trackdb.open ();
		trackView = new TrackView (trackdb);
	}

	@Override
	public void close() throws ServiceException {
		if (trackdb != null) {
			trackdb.close ();
		}
	}

	@Override
	public void track(TrackEvent event) throws ServiceException {
		try {
//			TrackKey key = new TrackKey(java.util.UUID.randomUUID().toString(), DateHelper.parse(event.get(EventAttribute.TIME)).getTime());
			TrackKey key = new TrackKey(java.util.UUID.randomUUID().toString(), event.getTime());
//			bannerEvents.get(event.get(EventAttribute.BANNER_ID)).put(key, event);
//			siteEvents.get(event.get(EventAttribute.SITE)).put(key, event);
			this.trackView.getTrackEventsMap ().put(key, event);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<TrackEvent> list(Criterion criterion, Date from, Date to)
			throws ServiceException {
		
		TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
		TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
		SortedMap<TrackKey, TrackEvent> matchMap = this.trackView.getTrackEventsMap ().subMap(key1, true, key2, true);

		List<TrackEvent> matches = new LinkedList<TrackEvent>();
		matches.addAll (matchMap.values ());
		
		return matches;
	}

	@Override
	public int count(Criterion criterion, Date from, Date to) throws ServiceException {
		TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
		TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
		SortedMap<TrackKey, TrackEvent> matchMap = this.trackView.getTrackEventsMap ().subMap(key1, true, key2, true);

		return matchMap.size();
	}

	@Override
	public void delete(Criterion criterion, Date from, Date to) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear(Criterion criterion) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public int countClicks(Criterion criterion, Date from, Date to)
			throws ServiceException {
		
		String key_from = criterion.value + "_" + EventType.CLICK.getName () + "_" + from.getTime ();
		String key_to = criterion.value + "_" + EventType.CLICK.getName () + "_" + to.getTime ();
		return trackView.getTrackEventsByBanneridMap ().subMap (key_from, true, key_to, true).size ();
	}

	@Override
	public int countImpressions(Criterion criterion, Date from, Date to)
			throws ServiceException {
		String key_from = criterion.value + "_" + EventType.IMPRESSION.getName () + "_" + from.getTime ();
		String key_to = criterion.value + "_" + EventType.IMPRESSION.getName () + "_" + to.getTime ();
		return trackView.getTrackEventsByBanneridMap ().subMap (key_from, true, key_to, true).size ();
	}

	@Override
	public int countClicks(String user, Criterion criterion, Date from, Date to)
			throws ServiceException {
		
		String key_from = user + "_" + criterion.value + "_" + EventType.CLICK.getName () + "_" + from.getTime ();
		String key_to = user + "_" + criterion.value + "_" + EventType.CLICK.getName () + "_" + to.getTime ();
		return trackView.getUserBannerMap().subMap (key_from, true, key_to, true).size ();
	}

	@Override
	public int countImpressions(String user, Criterion criterion, Date from, Date to)
			throws ServiceException {
		String key_from = user + "_" + criterion.value + "_" + EventType.IMPRESSION.getName () + "_" + from.getTime ();
		String key_to = user + "_" + criterion.value + "_" + EventType.IMPRESSION.getName () + "_" + to.getTime ();
		return trackView.getUserBannerMap().subMap (key_from, true, key_to, true).size ();
	}
	
	@Override
	public void deleteImpressions(Criterion criterion, Date from, Date to)
			throws ServiceException {
//		trackView.getTrackEventsMap ().

	}

	@Override
	public void deleteClicks(Criterion criterion, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
}
