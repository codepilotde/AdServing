package net.mad.ads.base.api.render.impl.freemarker.test;


import java.util.HashMap;
import java.util.Map;

import net.mad.ads.base.api.render.BannerRenderer;
import net.mad.ads.base.api.render.RenderContext;
import net.mad.ads.base.api.render.impl.freemarker.FreemarkerBannerRenderer;


public class FmTest {
	public static void main (String [] args) throws Exception {
		
		
		RenderContext context = new RenderContext();
		context.put("server", "www.myserver1.de");
		
		
		BannerRenderer tm = new FreemarkerBannerRenderer();
		tm.init("testdata/templates");
		tm.registerTemplate("test", "test.ftl");
		
		System.out.println(tm.render("test", context));
	}
}
