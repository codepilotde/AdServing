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
