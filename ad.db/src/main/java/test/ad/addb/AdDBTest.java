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


import java.io.IOException;
import java.util.List;

import net.mad.ads.db.AdDBManager;
import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;

public class AdDBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		AdDB db = new AdDB();
		
		AdDBManager.getInstance().getAdDB().open();
		
		ImageBannerDefinition ib = new ImageBannerDefinition();
		ib.setFormat(BannerFormat.MEDIUM_RECTANGLE);
		ib.setId("1");
		AdDBManager.getInstance().getAdDB().addBanner(ib);
		
		ib = new ImageBannerDefinition();
		ib.setFormat(BannerFormat.MEDIUM_RECTANGLE);
		ib.setId("2");
		AdDBManager.getInstance().getAdDB().addBanner(ib);
		
		AdDBManager.getInstance().getAdDB().reopen();
		
		AdRequest request = new AdRequest();
		request.getFormats().add(BannerFormat.MEDIUM_RECTANGLE);
		request.getTypes().add(BannerType.IMAGE);
		
		List<BannerDefinition> result = AdDBManager.getInstance().getAdDB().search(request);
		
		System.out.println(result.size());
		
		
		AdDBManager.getInstance().getAdDB().close();
	}

}
