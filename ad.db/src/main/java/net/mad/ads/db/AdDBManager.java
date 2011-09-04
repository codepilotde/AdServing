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
package net.mad.ads.db;

import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.condition.Condition;
import net.mad.ads.db.condition.impl.AdSlotCondition;
import net.mad.ads.db.condition.impl.CountryCondition;
import net.mad.ads.db.condition.impl.DateCondition;
import net.mad.ads.db.condition.impl.DayCondition;
import net.mad.ads.db.condition.impl.DistanceCondition;
import net.mad.ads.db.condition.impl.ExcludeSiteCondition;
import net.mad.ads.db.condition.impl.KeywordCondition;
import net.mad.ads.db.condition.impl.SiteCondition;
import net.mad.ads.db.condition.impl.StateCondition;
import net.mad.ads.db.condition.impl.TimeCondition;
import net.mad.ads.db.db.AdDB;

public class AdDBManager {

	private static AdDBManager INSTANCE = new AdDBManager();
	private static final AdDB adDB = new AdDB();
	
	private static final AdDBContext context = new AdDBContext();
	
	public static AdDBManager getInstance () {
		return INSTANCE;
	}

	private static final List<Condition> conditions = new ArrayList<Condition>();
	static {
		// Default Conditions 
		conditions.add(new CountryCondition());
		conditions.add(new StateCondition());
		conditions.add(new DateCondition());
		conditions.add(new DayCondition());
		conditions.add(new TimeCondition());
		conditions.add(new KeywordCondition());
		conditions.add(new SiteCondition());
		conditions.add(new AdSlotCondition());
		// wieso ist diese ExcludeCondition auskommentiert
//		conditions.add(new ExcludeSiteCondition());
		conditions.add(new DistanceCondition());
	}
//	
	private AdDBManager () {
		
	}
	
	public AdDB getAdDB () {
		return adDB;
	}
	
	public List<Condition> getConditions () {
		return conditions;
	}
	
	public AdDBContext getContext () {
		return this.context;
	}
}
