package net.mad.ads.db.definition.condition;

import java.util.HashSet;
import java.util.Set;

import net.mad.ads.db.AdDBConstants;
import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.definition.condition.TimeConditionDefinition.Period;

/**
 * Steuerung zu welchen Daten das Banner angezeigt werden soll
 * 
 * Dadurch ist es möglich, ein Banner nur im Sommer anzuzeigen
 * 01.06.2011 - 31.08.2011 
 * 
 * Durch die Einführung der Perioden, kann geregelt werden, dass ein Banner zu
 * verschiedenen Zeitspannen angezeigt wird:
 * 01.06.2011 - 31.08.2011
 * 01.10.2011 - 31.10.2011
 * 
 * @author tmarx
 *
 */
public class DateConditionDefinition implements ConditionDefinition {

	public static final Period ALL_TIMES = new Period();
	static {
		ALL_TIMES.setFrom(AdDBConstants.ADDB_BANNER_DATE_ALL);
		ALL_TIMES.setTo(AdDBConstants.ADDB_BANNER_DATE_ALL);
	}
	public static final int MAX_PERIOD_COUNT = 6;

	private Set<DateConditionDefinition.Period> periods = new HashSet<DateConditionDefinition.Period>();
	
	public DateConditionDefinition () {
		
	}
	
	public Set<DateConditionDefinition.Period> getPeriods() {
		return periods;
	}
	
	public void addPeriod (String from, String to) {
		Period p = new Period();
		if (from != null) {
			p.setFrom(from);
		} else {
			p.setFrom(AdDBConstants.ADDB_BANNER_DATE_ALL);
		}
		
		if (to != null) {
			p.setTo(to);
		} else {
			p.setTo(AdDBConstants.ADDB_BANNER_DATE_ALL);
		}
		
		periods.add(p);
	}
	
	public static class Period {
		private String from;
		private String to;
		
		/**
		 * von welchem Datum an soll das banner angezeigt werdne
		 * z.b. 01.02.2010
		 * @return
		 */
		public String getFrom() {
			return this.from;
		}
		public void setFrom(String date) {
			this.from = date;
		}
		/**
		 * bis zu welchem Datum soll das Banner angezegit werden
		 * @return
		 */
		public String getTo() {
			return this.to;
		}
		public void setTo(String date) {
			this.to = date;
		}
	}
}
