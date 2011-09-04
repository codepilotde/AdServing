/*
 * Mad-Advertisement Copyright (C) 2011 Thorsten Marx
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

package net.mad.ads.base.api.model.ads;

import java.util.EnumMap;

import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;

/**
 * Die Kampagne ist das Mutter-Objekt der Banner. Jedes
 * Banner liegt in einer Kampagne und übernimmt die Einstellung, die dort 
 * gemacht werden. So k�nnen Bedingung, wann die Banner angezeigt werden sollen
 * global konfiguriert werden.
 * 
 * 
 * @author thorsten
 *
 */
public class Campaign {
	
	private String id;
	private String name;
	
	/*
	 * Bedingungen für die Anzeige des Banners
	 */
	private EnumMap<ConditionDefinitions, ConditionDefinition> conditionDefinitions = new EnumMap<ConditionDefinitions, ConditionDefinition>(ConditionDefinitions.class);

	/**
	 * prüft, diese Kampagne irgendwelche Bedingungen hat
	 * 
	 * @return
	 */
	public boolean hasConditionDefinitions() {
		return this.conditionDefinitions.isEmpty();
	}
	/**
	 * Prüft, ob die Kampagne eine bestimmte Bedingung hat
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasConditionDefinition(ConditionDefinitions key) {
		return this.conditionDefinitions.containsKey(key);
	}

	/**
	 * Liefert eine bestimmte Bedingung dieser Kampagne
	 * 
	 * @param key
	 * @return
	 */
	public ConditionDefinition getConditionDefinition(ConditionDefinitions key) {
		return this.conditionDefinitions.get(key);
	}
	/**
	 * Fügt der Kampagne eine neue Bedingugn hinzu
	 * 
	 * @param key
	 * @param value
	 */
	public void addConditionDefinition(ConditionDefinitions key, ConditionDefinition value) {
		this.conditionDefinitions.put(key, value);
	}
}
