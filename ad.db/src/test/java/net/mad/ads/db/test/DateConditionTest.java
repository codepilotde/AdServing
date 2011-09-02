package net.mad.ads.db.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.mad.ads.db.AdDBConstants;
import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.CountryConditionDefinition;
import net.mad.ads.db.definition.condition.DateConditionDefinition;
import net.mad.ads.db.definition.condition.StateConditionDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.enums.State;

import junit.framework.TestCase;


public class DateConditionTest extends TestCase {
	
	@Test
	public void testDateCondition () throws Exception {
		System.out.println("CountryCondition");
		
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ImageBannerDefinition();
		b.setId("1");
		DateConditionDefinition sdef = new DateConditionDefinition();
		sdef.addPeriod("20110215", "20110220");
		sdef.addPeriod("20110315", "20110320");
		b.addConditionDefinition(ConditionDefinitions.DATE, sdef);
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
		
		request.setDate("20110214");
		List<BannerDefinition> result = db.search(request);
		assertEquals(0, result.size());
		
		request.setDate("20110215");
		result = db.search(request);
		assertEquals(1, result.size());
		
		request.setDate("20110220");
		result = db.search(request);
		assertEquals(1, result.size());
		
		request.setDate("20110221");
		result = db.search(request);
		assertEquals(0, result.size());
		
		request.setDate("20110314");
		result = db.search(request);
		assertEquals(0, result.size());
		
		request.setDate("20110315");
		result = db.search(request);
		assertEquals(1, result.size());
		
		request.setDate("20110320");
		result = db.search(request);
		assertEquals(1, result.size());
		
		request.setDate("20110321");
		result = db.search(request);
		assertEquals(0, result.size());
		
		db.close();
	}
}
