package net.mad.ads.db.definition.condition;

import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.enums.Day;

/**
 * Steuerung an welchen Wochentagen (Montag - Sonntag) das Banner angezeigt werden soll
 * 
 * @author tmarx
 *
 */
public class DayConditionDefinition implements ConditionDefinition {
	
	private List<Day> days = new ArrayList<Day>();
	
	public DayConditionDefinition () {
		
	}
	
	public List<Day> getDays() {
		return this.days;
	}
	public void addDay(Day day) {
		this.days.add(day);
	}
}
