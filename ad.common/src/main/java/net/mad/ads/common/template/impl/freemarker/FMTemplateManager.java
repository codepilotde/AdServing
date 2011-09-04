package net.mad.ads.common.template.impl.freemarker;


import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import net.mad.ads.common.template.TemplateManager;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FMTemplateManager implements TemplateManager {

	private Configuration cfg = null;
	
	private Map<String, Template> templates = null;
	
	public void init(String templatePath) throws IOException {
		cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(templatePath));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		
		this.templates = new HashMap<String, Template>();
	}

	public void registerTemplate(String name, String file) throws IOException {
		Template temp = cfg.getTemplate(file);
		templates.put(name, temp);
	}

	public String processTemplate(String name, Map<String, Object> parameters) throws IOException {
		try {
			Template temp = templates.get(name);
			
			StringWriter sw = new StringWriter();
			BufferedWriter bw = new BufferedWriter(sw);
			temp.process(parameters, bw);
			bw.flush();
			return sw.toString();
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

}
