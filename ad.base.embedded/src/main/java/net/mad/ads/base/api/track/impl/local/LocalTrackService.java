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
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.events.EventAttribute;
import net.mad.ads.base.api.track.events.EventType;
import net.mad.ads.base.api.track.events.TrackEvent;
import net.mad.ads.base.api.track.impl.local.helper.TrackKey;
import net.mad.ads.base.api.track.impl.local.helper.TrackTree;
import net.mad.ads.base.api.track.impl.local.helper.UUIDHelper;
import net.mad.ads.base.api.utils.DateHelper;
import de.marx.common.tools.CollectionUtils;

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
	public List<TrackEvent> list(String site, Date from, Date to)
			throws ServiceException {

		if (siteEvents.containsKey(site)) {
			TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
			TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
			return siteEvents.get(site).list(key1, key2);
		}

		return null;
	}

	@Override
	public int count(String site, Date from, Date to) throws ServiceException {
		
		if (siteEvents.containsKey(site)) {
			TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
			TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
			return siteEvents.get(site).count(key1, key2);
		}
		
		return 0;
	}

	@Override
	public void delete(String site, Date from, Date to) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear(String site) throws ServiceException {
		if (siteEvents.containsKey(site)) {
			siteEvents.get(site).clear();
		}
	}

	@Override
	public int countClicks(String bannerId, Date from, Date to)
			throws ServiceException {
		
		if (bannerEvents.containsKey(bannerId)) {
			TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
			TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
			List<TrackEvent> result = bannerEvents.get(bannerId).list(key1, key2);
			
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

	@Override
	public int countImpressions(String bannerId, Date from, Date to)
			throws ServiceException {
		
		if (bannerEvents.containsKey(bannerId)) {
			TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
			TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
			List<TrackEvent> result = bannerEvents.get(bannerId).list(key1, key2);
			
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

	@Override
	public void deleteImpressions(String bannerId, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteClicks(String bannerId, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public int countClicks(String user, String bannerId, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countImpressions(String user, String bannerId, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

}
