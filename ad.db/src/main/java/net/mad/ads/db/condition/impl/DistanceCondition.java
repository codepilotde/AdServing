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
package net.mad.ads.db.condition.impl;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.BooleanQuery;

import com.google.common.base.Predicate;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import net.mad.ads.db.condition.Condition;
import net.mad.ads.db.condition.Filter;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.DistanceConditionDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;

/**
 * Condition zum Filtern von Bannern, die nur in einem bestimmten Radius um
 * eine Geo-Koordinate angezeigt werden sollen
 * 
 * @author tmarx
 *
 */
public class DistanceCondition implements Condition, Filter {

	@Override
	public Predicate<BannerDefinition> getFilterPredicate(final AdRequest request) {
		Predicate<BannerDefinition> predicate = new Predicate<BannerDefinition>() {
			@Override
			public boolean apply(BannerDefinition type) {
				
				if (!type.hasConditionDefinition(ConditionDefinitions.DISTANCE)) {
					return true;
				}
				DistanceConditionDefinition dcdef = (DistanceConditionDefinition) type.getConditionDefinition(ConditionDefinitions.DISTANCE);
				if (request.getGeoLocation() == null) {
					/*
					 * Der Request liefert keine Position, dann wird das Banner ebenfalls angezeigt
					 * 
					 * TODO: evtl. könnte man das noch über einen Parameter steuern
					 */
					return true;
				}
				
				/*
				 * Positionen f�r Banner und Request
				 */
				LatLng bannerPOS = new LatLng(dcdef.getGeoLocation().getLatitude(), dcdef.getGeoLocation().getLongitude());
				LatLng requestPOS = new LatLng(request.getGeoLocation().getLatitude(), request.getGeoLocation().getLongitude());
				/*
				 * Distance zwischen Banner und Request
				 */
				double distance = LatLngTool.distance(bannerPOS, requestPOS, LengthUnit.KILOMETER);
				
				/*
				 * Wird der Banner angegebene Radius nicht überschritten, wird das Banner angezeigt, ansonsten nicht
				 */
				if (dcdef.getGeoRadius() >= distance) {
					return true;
				}
				
				return false;
			}
		};
		return predicate;
	}

	@Override
	public void addQuery(AdRequest request, BooleanQuery mainQuery) {
		// aktuell wird kein Radius Query unterstützt
	}

	@Override
	public void addFields(Document bannerDoc, BannerDefinition bannerDefinition) {
		// aktuell wird kein Radius Query unterstützt
	}

}
