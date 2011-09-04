package net.mad.ads.common.template;

import java.io.IOException;
import java.util.Map;

public interface TemplateManager {
	public void init (String templatePath) throws IOException;
	
	public void registerTemplate (String name, String file) throws IOException;
	
	public String processTemplate (String name, Map<String, Object> parameters) throws IOException;
}
