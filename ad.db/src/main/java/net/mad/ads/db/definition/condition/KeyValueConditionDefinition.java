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
import net.mad.ads.db.definition.KeyValue;
import net.mad.ads.db.definition.Keyword;
/**
 * Eine einfache Key-Value Bedingung
 * 
 * kann z.B. verwendet werden um Default-Banner zu definieren
 * 
 * @author tmarx
 *
 */
public class KeyValueConditionDefinition implements ConditionDefinition {

	private List<KeyValue> keyvalues = new ArrayList<KeyValue>();
	
	public KeyValueConditionDefinition () {
		
	}

	public List<KeyValue> getKeyValues() {
		return keyvalues;
	}
	
	public void addKeyValue (KeyValue kv) {
		this.keyvalues.add(kv);
	}
}
