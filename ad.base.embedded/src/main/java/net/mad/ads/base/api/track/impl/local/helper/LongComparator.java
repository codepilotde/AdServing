package net.mad.ads.base.api.track.impl.local.helper;

import java.io.Serializable;
import java.util.*;

public class LongComparator implements Comparator<Long>, Serializable {

	@Override
	public int compare(Long first, Long second) {
		return first.compareTo(second);
	}

}
