package net.mad.ads.base.api.track.impl.local.helper;

import java.io.Serializable;
import java.util.*;

import net.mad.ads.base.api.utils.DateHelper;

public class UUIDComparator implements Comparator<UUID>, Serializable {

	@Override
	public int compare(UUID first, UUID second) {
		com.eaio.uuid.UUID u1 = new com.eaio.uuid.UUID(first.toString());
		com.eaio.uuid.UUID u2 = new com.eaio.uuid.UUID(second.toString());
		
		
		
		int compare = -first.compareTo(second);
		if (compare == 0) {
			return 1;
		}
		return compare;
		
		
//		return -first.compareTo(second);
	}

}
