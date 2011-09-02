package net.mad.ads.db.test;


import java.io.IOException;
import java.util.List;

import net.mad.ads.db.AdDBManager;
import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;

public class StoredAdDBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		AdDBManager.getInstance().getContext().useRamOnly = false;
		AdDBManager.getInstance().getContext().tempDir = "D:/www/applicationData/adserver/temp/";
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
