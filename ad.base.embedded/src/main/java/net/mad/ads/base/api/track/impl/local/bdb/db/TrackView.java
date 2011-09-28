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
