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
package net.mad.ads.db.definition.condition;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.mad.ads.db.AdDBConstants;
import net.mad.ads.db.definition.ConditionDefinition;

/**
 * Steuerung der Tageszeit, zu der ein Banner angezeigt werden soll
 * So kann gesteuert werden, dass ein Banner nur zwischen 8 und 12 Uhr angezeigt werden soll
 * 
 * @author tmarx
 *
 */
public class TimeConditionDefinition implements ConditionDefinition {
	
	public static final Period ALL_TIMES = new Period();
	static {
		ALL_TIMES.setFrom(AdDBConstants.ADDB_BANNER_TIME_ALL);
		ALL_TIMES.setTo(AdDBConstants.ADDB_BANNER_TIME_ALL);
	}
	public static final int MAX_PERIOD_COUNT = 4;

	private Set<TimeConditionDefinition.Period> periods = new HashSet<TimeConditionDefinition.Period>();
	
	public TimeConditionDefinition () {
	}
	
	public Set<TimeConditionDefinition.Period> getPeriods() {
		return periods;
	}
	
	public void addPeriod (String from, String to) {
		Period p = new Period();
		if (from != null) {
			p.setFrom(from);
		} else {
			p.setFrom(AdDBConstants.ADDB_BANNER_TIME_ALL);
		}
		
		if (to != null) {
			p.setTo(to);
		} else {
			p.setTo(AdDBConstants.ADDB_BANNER_TIME_ALL);
		}
		
		periods.add(p);
	}

	public static class Period {
		private String from;
		private String to;
		
		public final String getFrom() {
			return from;
		}

		public final void setFrom(String from) {
			this.from = from;
		}

		public final String getTo() {
			return to;
		}

		public final void setTo(String to) {
			this.to = to;
		}
	}
	
	
}
