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

import java.util.EnumMap;

import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.enums.ExpirationResolution;
/**
 * Steuerung der Anzahl an Klicks, die ein Banner nicht überschreiten soll.
 * So kann gesteuert werden, dass ein Banner pro Tag nur 1000 man angeklickt werden soll
 * 
 * @author tmarx
 *
 */
public class ClickExpirationConditionDefinition implements ConditionDefinition {

	private EnumMap<ExpirationResolution, Integer> clickExpirations = new EnumMap<ExpirationResolution, Integer>(ExpirationResolution.class);
	
	public ClickExpirationConditionDefinition () {
		
	}
	
	public EnumMap<ExpirationResolution, Integer> getClickExpirations () {
		return this.clickExpirations;
	}
}
