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
package net.mad.ads.base.api.track.impl.local.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

public class TrackTree<T> extends ConcurrentSkipListMap<TrackKey, T> {
	
	private int maxSize = 1;
	
	public TrackTree(int maxSize) {
//		super(new LongComparator());
//		super(new UUIDComparator());
		super(new TrackKeyComparator());
		
		this.maxSize = maxSize;
	}

	@Override
	public T put(TrackKey key, T value) {
		T result = super.put(key, value);
		if (size() > maxSize) {
			remove(firstEntry());
		}
		
		return result;
	}

	public int count(TrackKey from, TrackKey to) {
//		SortedMap<Long, T> matchMap = new TreeMap<Long, T>();
//		matchMap.putAll(this.subMap(from, true, to, true));
		
		SortedMap<TrackKey, T> matchMap = this.subMap(from, true, to, true);

		return matchMap.size();
	}
	
	public List<T> list(TrackKey from, TrackKey to) {
//		SortedMap<UUID, T> matchMap = new TreeMap<UUID, T>(new UUIDComparator());
//		matchMap.putAll(this.subMap(from, true, to, true));
		
		SortedMap<TrackKey, T> matchMap = this.subMap(from, true, to, true);

		List<T> matches = new LinkedList<T>();
		for (T ci : matchMap.values()) {
			matches.add(ci);
		}
		
		return matches;
	}
}
