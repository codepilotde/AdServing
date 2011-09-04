package net.mad.ads.common.template.impl.freemarker.test;


import java.util.HashMap;
import java.util.Map;

import net.mad.ads.common.template.TemplateManager;
import net.mad.ads.common.template.impl.freemarker.FMTemplateManager;



public class FmTest {
	public static void main (String [] args) throws Exception {
		
		
		Map root = new HashMap();
		root.put("server", "www.myserver1.de");
		
		
		TemplateManager tm = new FMTemplateManager();
		tm.init("templates");
		tm.registerTemplate("test", "banner/detail_spartopia.ftl");
		
		System.out.println(tm.processTemplate("test", root));
	}
}
