package net.mad.ads.db.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.mad.ads.db.condition.impl.ExcludeSiteCondition;
import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.CountryConditionDefinition;
import net.mad.ads.db.definition.condition.ExcludeSiteConditionDefinition;
import net.mad.ads.db.definition.condition.SiteConditionDefinition;
import net.mad.ads.db.definition.condition.StateConditionDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.enums.State;

import junit.framework.TestCase;


public class SiteConditionTest extends TestCase {
	
	@Test
	public void testSiteCondition () throws Exception {
		System.out.println("SiteCondition");
		
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ImageBannerDefinition();
		b.setId("1");
		SiteConditionDefinition sdef = new SiteConditionDefinition();
		sdef.addSite("10");
		b.addConditionDefinition(ConditionDefinitions.SITE, sdef);
		b.setFormat(BannerFormat.FULL_BANNER);
		db.addBanner(b);
		
		b = new ImageBannerDefinition();
		b.setId("2");
		sdef = new SiteConditionDefinition();
		sdef.addSite("11");
		b.addConditionDefinition(ConditionDefinitions.SITE, sdef);
		ExcludeSiteConditionDefinition edef = new ExcludeSiteConditionDefinition();
		edef.addSite("10");
		b.addConditionDefinition(ConditionDefinitions.EXCLUDE_SITE, edef);
		
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
		request.setSite("1");
		
		List<BannerDefinition> result = db.search(request);
		assertTrue(result.isEmpty());
		
		request.setSite("10");
		result = db.search(request);
		assertEquals(1, result.size());
		assertTrue(result.get(0).getId().equals("1"));
		
		
		request.setSite("11");
		result = db.search(request);
		assertEquals(1, result.size());
		assertTrue(result.get(0).getId().equals("2"));

		request.setSite("12");
		result = db.search(request);
		assertEquals(0, result.size());
		
		BannerDefinition b1 = new ImageBannerDefinition();
		b1.setId("3");
		b1.setFormat(BannerFormat.FULL_BANNER);
		db.addBanner(b1);
		db.reopen();
		
		System.out.println(db.size());
		
		request.setSite("12");
		result = db.search(request);
		assertEquals(1, result.size());
		assertTrue(result.get(0).getId().equals("3"));
		
		db.close();
	}
}
