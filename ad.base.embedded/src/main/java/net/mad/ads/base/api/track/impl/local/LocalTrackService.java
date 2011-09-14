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
package net.mad.ads.base.api.track.impl.local;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.track.Criterion;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.events.EventAttribute;
import net.mad.ads.base.api.track.events.EventType;
import net.mad.ads.base.api.track.events.TrackEvent;
import net.mad.ads.base.api.track.impl.local.helper.TrackKey;
import net.mad.ads.base.api.track.impl.local.helper.TrackTree;
import net.mad.ads.base.api.track.impl.local.helper.UUIDHelper;
import net.mad.ads.common.util.CollectionUtils;
import net.mad.ads.base.api.utils.DateHelper;

/**
 * Eine lokale Implementierung zum tracken von Events, es wird keine Datenbank benötigt 
 * Diese Implementierung eignet sich für die Entwicklung oder ganz ganz kleine Installationen mit wenig traffic
 * 
 * @author tmarx
 *
 */
public class LocalTrackService implements TrackingService {

	private static final Logger logger = LoggerFactory.getLogger(LocalTrackService.class);
	
	private Map<String, TrackTree<TrackEvent>> siteEvents = new HashMap<String, TrackTree<TrackEvent>>();
	private Map<String, TrackTree<TrackEvent>> bannerEvents = new HashMap<String, TrackTree<TrackEvent>>();
	
//	public static final SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.GERMANY);
	
	@Override
	public void open(BaseContext context) throws ServiceException {
	}

	@Override
	public void close() throws ServiceException {
	}

	@Override
	public void track(TrackEvent event) throws ServiceException {
		if (!bannerEvents.containsKey(event.get(EventAttribute.BANNER_ID))) {
			bannerEvents.put(event.get(EventAttribute.BANNER_ID), new TrackTree<TrackEvent>(1000));
		}
		if (!siteEvents.containsKey(event.get(EventAttribute.SITE))) {
			siteEvents.put(event.get(EventAttribute.SITE), new TrackTree<TrackEvent>(1000));
		}
		
		try {
			TrackKey key = new TrackKey(java.util.UUID.randomUUID().toString(), DateHelper.parse(event.get(EventAttribute.TIME)).getTime());
			bannerEvents.get(event.get(EventAttribute.BANNER_ID)).put(key, event);
			siteEvents.get(event.get(EventAttribute.SITE)).put(key, event);
		} catch (Exception e) {
			logger.error("", e);
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<TrackEvent> list(Criterion criterion, EventType type, Date from, Date to)
			throws ServiceException {

		if (siteEvents.containsKey(criterion.value)) {
			TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
			TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
			return siteEvents.get(criterion.value).list(key1, key2);
		}

		return null;
	}

	@Override
	public long count(Criterion criterion, EventType type, Date from, Date to) throws ServiceException {
		
		if (type.equals(EventType.CLICK)) {
			return countClicks(criterion, from, to);
		} else if (type.equals(EventType.IMPRESSION)) {
			return countImpressions(criterion, from, to);
		}
		
		if (siteEvents.containsKey(criterion.value)) {
			TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
			TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
			return siteEvents.get(criterion.value).count(key1, key2);
		}
		
		return 0;
	}

	@Override
	public void delete(Criterion criterion, Date from, Date to) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear(Criterion criterion) throws ServiceException {
		if (siteEvents.containsKey(criterion.value)) {
			siteEvents.get(criterion.value).clear();
		}
	}

	
	private long countClicks(Criterion criterion, Date from, Date to)
			throws ServiceException {
		
		if (bannerEvents.containsKey(criterion.value)) {
			TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
			TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
			List<TrackEvent> result = bannerEvents.get(criterion.value).list(key1, key2);
			
			result = (List<TrackEvent>) CollectionUtils.filter(result, new CollectionUtils.Predicate<TrackEvent>() {

				@Override
				public boolean apply(TrackEvent event) {
					if (event.getType().equals(EventType.CLICK)) {
						return true;
					}
					return false;
				}
			});
			return result.size();
		}
		
		return 0;
	}

	
	private long countImpressions(Criterion criterion, Date from, Date to)
			throws ServiceException {
		
		if (bannerEvents.containsKey(criterion.value)) {
			TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
			TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
			List<TrackEvent> result = bannerEvents.get(criterion.value).list(key1, key2);
			
			result = (List<TrackEvent>) CollectionUtils.filter(result, new CollectionUtils.Predicate<TrackEvent>() {

				@Override
				public boolean apply(TrackEvent event) {
					if (event.getType().equals(EventType.IMPRESSION)) {
						return true;
					}
					return false;
				}
			});
			return result.size();
		}
		
		return 0;
	}

}
