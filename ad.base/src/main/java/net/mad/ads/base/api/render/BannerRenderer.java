package net.mad.ads.base.api.render;

import java.io.IOException;
import java.util.Map;

public interface BannerRenderer {
	public void init (String templatePath) throws IOException;
	
	public void registerTemplate (String name, String file) throws IOException;
	
	public String render (String name, RenderContext context) throws IOException;
}
