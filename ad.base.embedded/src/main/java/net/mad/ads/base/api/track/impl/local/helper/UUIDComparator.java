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
