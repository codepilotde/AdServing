package net.mad.ads.base.api.track.impl.local.helper;

import java.io.Serializable;
import java.util.*;

public class TrackKeyComparator implements Comparator<TrackKey>, Serializable {

	@Override
	public int compare(TrackKey first, TrackKey second) {
		Long l1 = first.getTime();
		Long l2 = second.getTime();
		
		int compare = l1.compareTo(l2);
		if (compare == 0){
			compare = -1;
		}
		
		return compare;
	}

}
