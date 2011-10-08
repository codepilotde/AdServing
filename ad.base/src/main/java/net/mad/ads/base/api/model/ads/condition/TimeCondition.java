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
package net.mad.ads.base.api.model.ads.condition;

import java.sql.Time;

import net.mad.ads.base.api.model.BaseModel;

/**
 * Describes the times of the day the banner should be delivered
 * 
 * @author thmarx
 *
 */
public class TimeCondition extends BaseModel {
	private Time from;
	private Time to;
	
	public TimeCondition () {
		
	}
	
	public TimeCondition (Time from, Time to) {
		super();
		
		this.to = to;
		this.from = from;
	}

	/**
	 * @return the from
	 */
	public Time getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(Time from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public Time getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(Time to) {
		this.to = to;
	}
	
	
	
}
