package net.mad.ads.db.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.CountryConditionDefinition;
import net.mad.ads.db.definition.condition.DayConditionDefinition;
import net.mad.ads.db.definition.condition.StateConditionDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.enums.Day;
import net.mad.ads.db.enums.State;

import junit.framework.TestCase;


public class DayConditionTest extends TestCase {
	
	@Test
	public void testDayCondition () throws Exception {
		System.out.println("DayCondition");
		
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ImageBannerDefinition();
		b.setId("1");
		
		DayConditionDefinition sdef = new DayConditionDefinition();
		sdef.addDay(Day.Monday);
		sdef.addDay(Day.Wednesday);
		b.addConditionDefinition(ConditionDefinitions.DAY, sdef);
		b.setFormat(BannerFormat.FULL_BANNER);
		db.addBanner(b);
		
		b = new ImageBannerDefinition();
		b.setId("2");
		sdef = new DayConditionDefinition();
		sdef.addDay(Day.All);
		b.addConditionDefinition(ConditionDefinitions.DAY, sdef);
		b.setFormat(BannerFormat.FULL_BANNER);
		db.addBanner(b);
		
		db.reopen();
		System.out.println(db.size());
		
		AdRequest request = new AdRequest();
		List<BannerFormat> formats = new ArrayList<BannerFormat>();
		formats.add(BannerFormat.FULL_BANNER);
		request.setFormats(formats);
		List<BannerType> types = new ArrayList<BannerType>();
		types.add(BannerType.IMAGE);
		request.setTypes(types);
		request.setDay(Day.Tuesday);
		
		List<BannerDefinition> result = db.search(request);
		assertEquals(1, result.size());
		
		request.setDay(Day.Monday);
		result = db.search(request);
		assertEquals(2, result.size());
		
		request.setDay(Day.Wednesday);
		result = db.search(request);
		assertEquals(2, result.size());
		
		request.setDay(Day.Sunday);
		result = db.search(request);
		System.out.println("size= " + result.size());
		System.out.println("id= " + result.get(0).getId());
		assertEquals(1, result.size());
		
//		db.writeDirectory("/temp");
		db.close();
	}
}
