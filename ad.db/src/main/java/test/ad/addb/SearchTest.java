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
package test.ad.addb;

import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.Keyword;
import net.mad.ads.db.definition.condition.CountryConditionDefinition;
import net.mad.ads.db.definition.condition.DateConditionDefinition;
import net.mad.ads.db.definition.condition.DayConditionDefinition;
import net.mad.ads.db.definition.condition.KeywordConditionDefinition;
import net.mad.ads.db.definition.condition.SiteConditionDefinition;
import net.mad.ads.db.definition.condition.StateConditionDefinition;
import net.mad.ads.db.definition.condition.TimeConditionDefinition;
import net.mad.ads.db.definition.impl.banner.extern.ExternBannerDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.enums.Day;
import net.mad.ads.db.enums.State;

public class SearchTest {
	public SearchTest () {
		
	}
	
	public void doDaySearchTest () throws Exception {
		System.out.println("Day");
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ImageBannerDefinition();
		b.setId("1");
		
		DayConditionDefinition ddef = new DayConditionDefinition();
		ddef.addDay(Day.Saturday);
		b.addConditionDefinition(ConditionDefinitions.DAY, ddef);
		
		
		b.setFormat(BannerFormat.FULL_BANNER);
		db.addBanner(b);
		
		
		db.reopen();
		
		AdRequest request = new AdRequest();
		List<BannerFormat> formats = new ArrayList<BannerFormat>();
		formats.add(BannerFormat.FULL_BANNER);
		request.setFormats(formats);
		List<BannerType> types = new ArrayList<BannerType>();
		types.add(BannerType.IMAGE);
		request.setTypes(types);
		request.setDay(Day.Monday);
		
		List<BannerDefinition> result = db.search(request);
		System.out.println(result);
		
		request.setDay(Day.Saturday);
		
		result = db.search(request);
		System.out.println(result);
		
		db.close();
	}
	
	public void doStateSearchTest () throws Exception {
		System.out.println("State");
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ImageBannerDefinition();
		b.setId("1");
		
		StateConditionDefinition sdef = new StateConditionDefinition();
		sdef.addState(State.BB);
		b.addConditionDefinition(ConditionDefinitions.STATE, sdef);
		
		b.setFormat(BannerFormat.FULL_BANNER);
		db.addBanner(b);
		
		db.reopen();
		
		AdRequest request = new AdRequest();
		List<BannerFormat> formats = new ArrayList<BannerFormat>();
		formats.add(BannerFormat.FULL_BANNER);
		request.setFormats(formats);
		List<BannerType> types = new ArrayList<BannerType>();
		types.add(BannerType.IMAGE);
		request.setTypes(types);
		request.setState(State.BE);
		
		List<BannerDefinition> result = db.search(request);
		System.out.println(result);
		
		request.setState(State.BB);
		
		result = db.search(request);
		System.out.println(result);
		
		db.close();
	}
	
	public void doTimeSearchTest () throws Exception {
		System.out.println("Time");
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ImageBannerDefinition();
		b.setId("1");
		
		TimeConditionDefinition tdef = new TimeConditionDefinition();
		
		tdef.addPeriod("1000", "2000");
		
		b.addConditionDefinition(ConditionDefinitions.TIME, tdef);

		b.setFormat(BannerFormat.FULL_BANNER);
		db.addBanner(b);
		
		db.reopen();
		
		AdRequest request = new AdRequest();
		List<BannerFormat> formats = new ArrayList<BannerFormat>();
		formats.add(BannerFormat.FULL_BANNER);
		request.setFormats(formats);
		List<BannerType> types = new ArrayList<BannerType>();
		types.add(BannerType.IMAGE);
		request.setTypes(types);
		request.setTime("0800");
		
		List<BannerDefinition> result = db.search(request);
		System.out.println(result);
		
		request.setTime("1100");
		result = db.search(request);
		System.out.println(result);
		
		db.close();
	}
	
	public void doDateSearchTest () throws Exception {
		System.out.println("Date");
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ImageBannerDefinition();
		b.setId("1");
		
		DateConditionDefinition dateDef = new DateConditionDefinition();
		dateDef.addPeriod("20100623", "20100723");
		b.addConditionDefinition(ConditionDefinitions.DATE, dateDef);
		
		b.setFormat(BannerFormat.FULL_BANNER);
		db.addBanner(b);
		
		b = new ImageBannerDefinition();
		b.setId("1");
		
		dateDef = new DateConditionDefinition();
		dateDef.addPeriod("20100623", null);
		b.addConditionDefinition(ConditionDefinitions.DATE, dateDef);
		
		b.setFormat(BannerFormat.FULL_BANNER);
		db.addBanner(b);
		
		db.reopen();
		
		AdRequest request = new AdRequest();
		List<BannerFormat> formats = new ArrayList<BannerFormat>();
		formats.add(BannerFormat.FULL_BANNER);
		request.setFormats(formats);
		List<BannerType> types = new ArrayList<BannerType>();
		types.add(BannerType.IMAGE);
		request.setTypes(types);
		request.setDate("20100623");
		
		List<BannerDefinition> result = db.search(request);
		System.out.println(result);
		
		request.setDate("20100724");
		result = db.search(request);
		System.out.println(result);
		
		db.reopen();
	}
	
	public void doCountrySearchTest () throws Exception {
		System.out.println("Country");
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ImageBannerDefinition();
		b.setId("1");
		
		CountryConditionDefinition cdef = new CountryConditionDefinition();
		cdef.addCountry(Country.DE);
		b.addConditionDefinition(ConditionDefinitions.COUNTRY, cdef);
		
		b.setFormat(BannerFormat.FULL_BANNER);
		db.addBanner(b);
		
		db.reopen();
		
		AdRequest request = new AdRequest();
		List<BannerFormat> formats = new ArrayList<BannerFormat>();
		formats.add(BannerFormat.FULL_BANNER);
		request.setFormats(formats);
		List<BannerType> types = new ArrayList<BannerType>();
		types.add(BannerType.IMAGE);
		request.setTypes(types);
		request.setCountry(Country.DE);
		
		List<BannerDefinition> result = db.search(request);
		System.out.println(result);
		
		request.setCountry(Country.UK);
		
		result = db.search(request);
		System.out.println(result);
		
		db.close();
	}
	
	public void doKeywordSearchTest () throws Exception {
		System.out.println("Keyword");
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ExternBannerDefinition();
		b.setId("1");

		CountryConditionDefinition cdef = new CountryConditionDefinition();
		cdef.addCountry(Country.DE);
		b.addConditionDefinition(ConditionDefinitions.COUNTRY, cdef);

		b.setFormat(BannerFormat.FULL_BANNER);
		Keyword kw = new Keyword("Esprit");
		KeywordConditionDefinition kdef = new KeywordConditionDefinition();
		kdef.addKeyword(kw);
		b.addConditionDefinition(ConditionDefinitions.KEYWORD, cdef);
		db.addBanner(b);
		
		db.reopen();
		
		AdRequest request = new AdRequest();
		List<BannerFormat> formats = new ArrayList<BannerFormat>();
		formats.add(BannerFormat.FULL_BANNER);
		request.setFormats(formats);
		List<BannerType> types = new ArrayList<BannerType>();
		types.add(BannerType.EXTERN);
		request.setTypes(types);
		
		request.getKeywords().add("Puma");
//		request.getKeywords().add("Esprit");
		
		List<BannerDefinition> result = db.search(request);
		System.out.println(result.size());
		
		db.close();
	}
	
	public void doSiteSearchTest () throws Exception {
		System.out.println("Site test");
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ExternBannerDefinition();
		b.setId("1");
		
		CountryConditionDefinition cdef = new CountryConditionDefinition();
		cdef.addCountry(Country.DE);
		b.addConditionDefinition(ConditionDefinitions.COUNTRY, cdef);
		b.setFormat(BannerFormat.FULL_BANNER);

		SiteConditionDefinition sdef = new SiteConditionDefinition();
		sdef.addSite("test_site");
		b.addConditionDefinition(ConditionDefinitions.SITE, sdef);
		
		db.addBanner(b);
		
		db.reopen();
		
		AdRequest request = new AdRequest();
		List<BannerFormat> formats = new ArrayList<BannerFormat>();
		formats.add(BannerFormat.FULL_BANNER);
		request.setFormats(formats);
		List<BannerType> types = new ArrayList<BannerType>();
		types.add(BannerType.EXTERN);
		request.setTypes(types);
		
		
		List<BannerDefinition> result = db.search(request);
		System.out.println(result.size());
		
		request.setSite("demo_site");
		result = db.search(request);
		System.out.println(result.size());
		
		request.setSite("test_site");
		result = db.search(request);
		System.out.println(result.size());
		
		db.close();
	}
	
	public static void main (String [] args) throws Exception {
		SearchTest st = new SearchTest();
		
//		st.doDaySearchTest();
//		System.out.println();
//		st.doTimeSearchTest();
//		System.out.println();
//		st.doStateSearchTest();
//		System.out.println();
//		st.doDateSearchTest();
//		System.out.println();
//		st.doCountrySearchTest();
		
//		st.doKeywordSearchTest();
		st.doSiteSearchTest();
	}
}
