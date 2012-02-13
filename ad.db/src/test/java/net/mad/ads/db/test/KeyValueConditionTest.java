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
package net.mad.ads.db.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.mad.ads.db.condition.impl.ExcludeSiteCondition;
import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.KeyValue;
import net.mad.ads.db.definition.condition.CountryConditionDefinition;
import net.mad.ads.db.definition.condition.ExcludeSiteConditionDefinition;
import net.mad.ads.db.definition.condition.KeyValueConditionDefinition;
import net.mad.ads.db.definition.condition.SiteConditionDefinition;
import net.mad.ads.db.definition.condition.StateConditionDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.enums.State;

import junit.framework.TestCase;


public class KeyValueConditionTest extends TestCase {
	
	@Test
	public void testKeyValueCondition () throws Exception {
		System.out.println("KeyValueCondition");
		
		AdDB db = new AdDB();
		
		db.open();
		
		BannerDefinition b = new ImageBannerDefinition();
		b.setId("1");
		KeyValueConditionDefinition sdef = new KeyValueConditionDefinition();
		sdef.getKeyValues().add(new KeyValue("k1", "v1"));
		b.addConditionDefinition(ConditionDefinitions.KEYVALUE, sdef);
		b.setFormat(BannerFormat.FULL_BANNER);
		db.addBanner(b);
		
		b = new ImageBannerDefinition();
		b.setId("2");
		sdef = new KeyValueConditionDefinition();
		sdef.getKeyValues().add(new KeyValue("k1", "v2"));
		b.addConditionDefinition(ConditionDefinitions.KEYVALUE, sdef);
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
		request.getKeyValues().put("k2", "none");
		
		List<BannerDefinition> result = db.search(request);
		assertTrue(result.isEmpty());
		
		request.getKeyValues().clear();
		request.getKeyValues().put("k1", "v1");
		result = db.search(request);
		assertEquals(1, result.size());
		assertTrue(result.get(0).getId().equals("1"));
		
		request.getKeyValues().clear();
		request.getKeyValues().put("k1", "v2");
		
		result = db.search(request);
		assertEquals(1, result.size());
		assertTrue(result.get(0).getId().equals("2"));
		
		db.close();
	}
}
