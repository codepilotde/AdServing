package net.mad.ads.common.template.impl.velocity.test;


import java.util.HashMap;
import java.util.Map;

import net.mad.ads.common.template.TemplateManager;
import net.mad.ads.common.template.impl.velocity.VMTemplateManager;



public class VmTest {
	public static void main (String [] args) throws Exception {
		TemplateManager tm = new VMTemplateManager();
		tm.init("templates");
		
		tm.registerTemplate("test", "test.vm");
		
		Map root = new HashMap();
		root.put("server", "www.myserver2.de");
		System.out.println(tm.processTemplate("test", root));
	}
}
