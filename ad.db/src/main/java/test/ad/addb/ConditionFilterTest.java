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
package test.ad.addb;

import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.Keyword;
import net.mad.ads.db.definition.condition.CountryConditionDefinition;
import net.mad.ads.db.definition.condition.DistanceConditionDefinition;
import net.mad.ads.db.definition.impl.banner.extern.ExternBannerDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.enums.Day;
import net.mad.ads.db.enums.State;
import net.mad.ads.db.utils.geo.GeoLocation;

public class ConditionFilterTest {
	public ConditionFilterTest () {
		
	}
	
	public void doGeoLocationTest () throws Exception {
		System.out.println("geo test");
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ExternBannerDefinition();
		b.setId("1");
		
		CountryConditionDefinition cdef = new CountryConditionDefinition();
		cdef.addCountry(Country.DE);
		b.addConditionDefinition(ConditionDefinitions.COUNTRY, cdef);
		
		b.setFormat(BannerFormat.FULL_BANNER);
		GeoLocation gl = new GeoLocation(51.4844, 7.2188);
		DistanceConditionDefinition dcdef = new DistanceConditionDefinition();
		dcdef.setGeoLocation(gl);
		dcdef.setGeoRadius(5);
		b.addConditionDefinition(ConditionDefinitions.DISTANCE, dcdef);
		
		db.addBanner(b);
		
		db.reopen();
		
		AdRequest request = new AdRequest();
		List<BannerFormat> formats = new ArrayList<BannerFormat>();
		formats.add(BannerFormat.FULL_BANNER);
		request.setFormats(formats);
		List<BannerType> types = new ArrayList<BannerType>();
		types.add(BannerType.EXTERN);
		request.setTypes(types);
		
		gl = new GeoLocation(51.4863, 7.180);
		request.setGeoLocation(gl);
		
		List<BannerDefinition> result = db.search(request);
		System.out.println(result.size());
		
		
		gl = new GeoLocation(51.4857, 7.0958);
		request.setGeoLocation(gl);
		result = db.search(request);
		System.out.println(result.size());
		
		
		db.close();
	}
	
	public static void main (String [] args) throws Exception {
		ConditionFilterTest st = new ConditionFilterTest();
		
		st.doGeoLocationTest();
	}
}
