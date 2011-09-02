package net.mad.ads.db.definition.condition;

import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.enums.Country;

/**
 * Steuerung der Länder in denen das Banner angezeigt werden soll
 * 
 * @author tmarx
 *
 */
public class CountryConditionDefinition implements ConditionDefinition {

	private List<Country> countries = new ArrayList<Country>();
	
	public CountryConditionDefinition () {
		
	}
	
	/**
	 * in welchen Ländern soll das Banner angezeigt werden
	 * @return
	 */
	public List<Country> getCountries () {
		return this.countries;
	}
	public void addCountry (Country country) {
		this.countries.add(country);
	}
}
