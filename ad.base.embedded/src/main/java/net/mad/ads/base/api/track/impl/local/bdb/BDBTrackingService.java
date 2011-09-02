package net.mad.ads.base.api.track.impl.local.bdb;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.EmbeddedBaseContext;
import net.mad.ads.base.api.exception.ServiceException;
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
	public List<TrackEvent> list(String site, Date from, Date to)
			throws ServiceException {
		
		TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
		TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
		SortedMap<TrackKey, TrackEvent> matchMap = this.trackView.getTrackEventsMap ().subMap(key1, true, key2, true);

		List<TrackEvent> matches = new LinkedList<TrackEvent>();
		matches.addAll (matchMap.values ());
		
		return matches;
	}

	@Override
	public int count(String site, Date from, Date to) throws ServiceException {
		TrackKey key1 = new TrackKey(java.util.UUID.randomUUID().toString(), from.getTime());
		TrackKey key2 = new TrackKey(java.util.UUID.randomUUID().toString(), to.getTime());
		SortedMap<TrackKey, TrackEvent> matchMap = this.trackView.getTrackEventsMap ().subMap(key1, true, key2, true);

		return matchMap.size();
	}

	@Override
	public void delete(String site, Date from, Date to) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear(String site) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public int countClicks(String bannerId, Date from, Date to)
			throws ServiceException {
		
		String key_from = bannerId + "_" + EventType.CLICK.getName () + "_" + from.getTime ();
		String key_to = bannerId + "_" + EventType.CLICK.getName () + "_" + to.getTime ();
		return trackView.getTrackEventsByBanneridMap ().subMap (key_from, true, key_to, true).size ();
	}

	@Override
	public int countImpressions(String bannerId, Date from, Date to)
			throws ServiceException {
		String key_from = bannerId + "_" + EventType.IMPRESSION.getName () + "_" + from.getTime ();
		String key_to = bannerId + "_" + EventType.IMPRESSION.getName () + "_" + to.getTime ();
		return trackView.getTrackEventsByBanneridMap ().subMap (key_from, true, key_to, true).size ();
	}

	@Override
	public int countClicks(String user, String bannerId, Date from, Date to)
			throws ServiceException {
		
		String key_from = user + "_" + bannerId + "_" + EventType.CLICK.getName () + "_" + from.getTime ();
		String key_to = user + "_" + bannerId + "_" + EventType.CLICK.getName () + "_" + to.getTime ();
		return trackView.getUserBannerMap().subMap (key_from, true, key_to, true).size ();
	}

	@Override
	public int countImpressions(String user, String bannerId, Date from, Date to)
			throws ServiceException {
		String key_from = user + "_" + bannerId + "_" + EventType.IMPRESSION.getName () + "_" + from.getTime ();
		String key_to = user + "_" + bannerId + "_" + EventType.IMPRESSION.getName () + "_" + to.getTime ();
		return trackView.getUserBannerMap().subMap (key_from, true, key_to, true).size ();
	}
	
	@Override
	public void deleteImpressions(String bannerId, Date from, Date to)
			throws ServiceException {
//		trackView.getTrackEventsMap ().

	}

	@Override
	public void deleteClicks(String bannerId, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub

	}
}
