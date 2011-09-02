package net.mad.ads.base.api.render;

import net.mad.ads.base.api.render.impl.freemarker.FreemarkerBannerRenderer;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;

public class ImageBannerRenderTest {
	public static void main (String [] args) throws Exception {
		
		ImageBannerDefinition banner = new ImageBannerDefinition();
		banner.setFormat(BannerFormat.HALF_BANNER);
		banner.setImageUrl("test.jpg");
		
		RenderContext context = new RenderContext();
		context.put("banner", banner);
		
		
		BannerRenderer tm = new FreemarkerBannerRenderer();
		tm.init("testdata/templates/banner");
		tm.registerTemplate("image", "image.ftl");
		
		System.out.println(tm.render("image", context));
	}
}
