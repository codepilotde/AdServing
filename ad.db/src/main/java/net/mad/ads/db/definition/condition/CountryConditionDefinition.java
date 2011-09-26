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
