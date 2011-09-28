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
package net.mad.ads.db.enums;

import java.io.Serializable;
import java.util.Calendar;

public enum Day implements Serializable {
	
	Monday(1),
	Tuesday(2),
	Wednesday(3),
	Thursday(4),
	Friday(5),
	Saturday(6),
	Sunday(7),
	All(0);
	
	private int day = 0;
	private Day (int day) {
		this.day = day;
	}
	
	public int getDay() {
		return day;
	}
	public static Day getDayForInt (int day) throws IllegalArgumentException {
		for (Day d : Day.values()) {
			if (d.getDay() == day) {
				return d;
			}
		}
		throw new IllegalArgumentException("Parameter day=" + day + " not valid");
	}
	
	public static Day getDayForJava (int jDay) {
		
		if (jDay == Calendar.MONDAY) {
			return Day.Monday;
		} else if (jDay == Calendar.TUESDAY) {
			return Day.Tuesday;
		} else if (jDay == Calendar.WEDNESDAY) {
			return Day.Wednesday;
		} else if (jDay == Calendar.THURSDAY) {
			return Day.Thursday;
		} else if (jDay == Calendar.FRIDAY) {
			return Day.Friday;
		} else if (jDay == Calendar.SATURDAY) {
			return Day.Saturday;
		} else if (jDay == Calendar.SUNDAY) {
			return Day.Sunday;
		}
		
		 return All;
	}
}
