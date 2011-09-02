package net.mad.ads.base.api.render.impl.freemarker;


import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import net.mad.ads.base.api.render.BannerRenderer;
import net.mad.ads.base.api.render.RenderContext;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerBannerRenderer implements BannerRenderer {

	private Configuration cfg = null;
	
	private Map<String, Template> templates = null;
	
	@Override
	public void init(String templatePath) throws IOException {
		cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(templatePath));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		
		this.templates = new HashMap<String, Template>();
	}

	@Override
	public void registerTemplate(String name, String file) throws IOException {
		Template temp = cfg.getTemplate(file);
		templates.put(name, temp);
	}

	@Override
	public String render(String name, RenderContext context) throws IOException {
		try {
			Template temp = templates.get(name);
			
			context.put("standardCompress", new freemarker.template.utility.StandardCompress());
			
			StringWriter sw = new StringWriter();
			BufferedWriter bw = new BufferedWriter(sw);
			temp.process(context, bw);
			bw.flush();
			return sw.toString();
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

}
