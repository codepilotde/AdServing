package net.mad.ads.db.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.StateConditionDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.State;

import junit.framework.TestCase;


public class StateConditionTest extends TestCase {
	
	@Test
	public void testStateCondition () throws Exception {
		System.out.println("StateCondition");
		
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
		assertTrue(result.isEmpty());
		
		request.setState(State.BB);
		
		result = db.search(request);
		assertEquals(result.size(), 1);
		
		db.close();
	}
}
