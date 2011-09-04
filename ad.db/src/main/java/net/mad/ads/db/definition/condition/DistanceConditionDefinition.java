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

package net.mad.ads.db.definition.condition;

import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.utils.geo.GeoLocation;

/**
 * Steuerung für welche Entfernung ein Banner angezeigt werden soll
 * 
 * @author tmarx
 *
 */
public class DistanceConditionDefinition implements ConditionDefinition {

	/*
	 * Der Mittelpunkt zur Berechnung der Entfernung
	 */
	private GeoLocation geoLocation = null;
	
	/*
	 * Die maximale Entfernung in der das Banner angezeigt werden soll
	 */
	private int geoRadius = -1;
	
	public DistanceConditionDefinition () {
		
	}
	
	
	public GeoLocation getGeoLocation() {
		return this.geoLocation;
	}

	
	public void setGeoLocation(GeoLocation location) {
		this.geoLocation = location;
	}
	
	public final int getGeoRadius() {
		return geoRadius;
	}
	
	public final void setGeoRadius(int geoRadius) {
		this.geoRadius = geoRadius;
	}
}
