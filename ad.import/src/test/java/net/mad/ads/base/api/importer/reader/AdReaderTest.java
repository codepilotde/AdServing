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
package net.mad.ads.base.api.importer.reader;

import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.ClickExpirationConditionDefinition;
import net.mad.ads.db.definition.condition.DateConditionDefinition;
import net.mad.ads.db.definition.condition.DayConditionDefinition;
import net.mad.ads.db.definition.condition.DistanceConditionDefinition;
import net.mad.ads.db.definition.condition.StateConditionDefinition;
import net.mad.ads.db.definition.condition.TimeConditionDefinition;
import net.mad.ads.db.definition.condition.ViewExpirationConditionDefinition;
import net.mad.ads.db.definition.impl.banner.extern.ExternBannerDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.ExpirationResolution;


public class AdReaderTest {
	public static void main (String [] args) throws Exception {
		BannerDefinition banner = AdXmlReader.readBannerDefinition("banner/banner1.xml");
		
//		System.out.println(((ExternBannerDefinition)banner).getExternContent());
		
//		if (banner.hasConditionDefinition(ConditionDefinitions.DATE)) {
//			System.out.println(((DateConditionDefinition)banner.getConditionDefinition(ConditionDefinitions.DATE)).getFrom());
//			System.out.println(((DateConditionDefinition)banner.getConditionDefinition(ConditionDefinitions.DATE)).getTo());
//		}
//		if (banner.hasConditionDefinition(ConditionDefinitions.TIME)) {
//			System.out.println(((TimeConditionDefinition)banner.getConditionDefinition(ConditionDefinitions.TIME)).getFrom());
//			System.out.println(((TimeConditionDefinition)banner.getConditionDefinition(ConditionDefinitions.TIME)).getTo());
//		}
		if (banner.hasConditionDefinition(ConditionDefinitions.DAY)) {
			System.out.println(((DayConditionDefinition)banner.getConditionDefinition(ConditionDefinitions.DAY)).getDays().size());
		}
		if (banner.hasConditionDefinition(ConditionDefinitions.STATE)) {
			System.out.println(((StateConditionDefinition)banner.getConditionDefinition(ConditionDefinitions.STATE)).getStates().size());
		}
		
		if (banner.hasConditionDefinition(ConditionDefinitions.DISTANCE)) {
			System.out.println("distance");
			DistanceConditionDefinition dd = (DistanceConditionDefinition) banner.getConditionDefinition(ConditionDefinitions.DISTANCE);
			
			
			System.out.println(dd.getGeoRadius());
			System.out.println(dd.getGeoLocation().getLatitude());
			System.out.println(dd.getGeoLocation().getLongitude());
		}
		
		if (banner.hasConditionDefinition(ConditionDefinitions.VIEW_EXPIRATION)) {
			ViewExpirationConditionDefinition dd = (ViewExpirationConditionDefinition) banner.getConditionDefinition(ConditionDefinitions.VIEW_EXPIRATION);
			System.out.println("view expiration");
			
			for (ExpirationResolution res : dd.getViewExpirations().keySet()) {
				System.out.println(res.getName() + " = " + dd.getViewExpirations().get(res));
			}
		}
		if (banner.hasConditionDefinition(ConditionDefinitions.CLICK_EXPIRATION)) {
			ClickExpirationConditionDefinition dd = (ClickExpirationConditionDefinition) banner.getConditionDefinition(ConditionDefinitions.CLICK_EXPIRATION);
			System.out.println("click expiration");
			
			for (ExpirationResolution res : dd.getClickExpirations().keySet()) {
				System.out.println(res.getName() + " = " + dd.getClickExpirations().get(res));
			}
		}
	}
}
