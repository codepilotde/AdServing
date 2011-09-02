/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.mad.ads.base.api.track.impl.local.bdb.db;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.collections.StoredSortedMap;
import net.mad.ads.base.api.track.events.TrackEvent;
import net.mad.ads.base.api.track.impl.local.helper.TrackKey;

/**
 *
 * @author tmarx
 */
public class TrackView {
	
	private StoredSortedMap<TrackKey, TrackEvent> trackEventsMap;
    private StoredSortedMap<String, TrackEvent> trackEventsByTypeMap;
	private StoredSortedMap<String, TrackEvent> trackEventsByBanneridMap;
	
	private StoredSortedMap<String, TrackEvent> userBannerMap;
	
	public TrackView (TrackDB trackdb) {
		
		ClassCatalog catalog = trackdb.getClassCatalog();
		
		EntryBinding<TrackKey> trackKeyBinding =
            new SerialBinding<TrackKey> (catalog, TrackKey.class);
		EntryBinding<TrackEvent> trackDataBinding =
            new SerialBinding<TrackEvent> (catalog, TrackEvent.class);
        EntryBinding<String> typeKeyBinding =
            new SerialBinding<String>(catalog, String.class);
		
		trackEventsMap =
            new StoredSortedMap<TrackKey, TrackEvent>(trackdb.getTrackEventDatabase (),
                                trackKeyBinding, trackDataBinding, true);
        trackEventsByTypeMap =
            new StoredSortedMap<String, TrackEvent>(trackdb.getTrackEventsByTypeDatabase (),
                                typeKeyBinding, trackDataBinding, true);
		
		trackEventsByBanneridMap =
            new StoredSortedMap<String, TrackEvent>(trackdb.getTrackEventsByBanneridDatabase (),
                                typeKeyBinding, trackDataBinding, true);
		
		userBannerMap =
	            new StoredSortedMap<String, TrackEvent>(trackdb.getUserBannerDatabase(),
	                                typeKeyBinding, trackDataBinding, true);
	}

	/**
	 * @return the trackEventsMap
	 */
	public StoredSortedMap<TrackKey, TrackEvent> getTrackEventsMap () {
		return trackEventsMap;
	}

	/**
	 * @return the trackEventsByTypeMap
	 */
	public StoredSortedMap<String, TrackEvent> getTrackEventsByTypeMap () {
		return trackEventsByTypeMap;
	}
	public StoredSortedMap<String, TrackEvent> getTrackEventsByBanneridMap () {
		return trackEventsByBanneridMap;
	}
	
	public StoredSortedMap<String, TrackEvent> getUserBannerMap () {
		return this.userBannerMap;
	}
	
}
