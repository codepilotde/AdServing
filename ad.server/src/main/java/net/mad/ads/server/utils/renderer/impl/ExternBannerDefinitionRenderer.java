package net.mad.ads.server.utils.renderer.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.base.api.render.RenderContext;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.impl.banner.extern.ExternBannerDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.server.utils.AdServerConstants;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.server.utils.renderer.BannerDefinitionRenderer;

/**
 * Renderer für die Bannerdefinitionen des Types Extern
 * 
 * @author tmarx
 *
 */
public class ExternBannerDefinitionRenderer implements BannerDefinitionRenderer<ExternBannerDefinition> {
	
	private static final Logger logger = LoggerFactory.getLogger(ExternBannerDefinitionRenderer.class);
	
	public static BannerDefinitionRenderer<ExternBannerDefinition> INSTANCE = null;
	
	private ExternBannerDefinitionRenderer () {
	}
	
	public static synchronized BannerDefinitionRenderer getInstance () {
		if (INSTANCE == null) {
			INSTANCE = new ExternBannerDefinitionRenderer();
		}
		
		return INSTANCE;
	}
	
	/* (non-Javadoc)
	 * @see net.mad.ads.server.utils.renderer.BannerDefinitionRenderer#render(net.mad.ads.api.definition.impl.image.ImageBannerDefinition)
	 */
	@Override
	public String render (ExternBannerDefinition banner, HttpServletRequest request) {
		String clickurl = RuntimeContext.getProperties().getProperty(AdServerConstants.CONFIG.PROPERTIES.CLICK_URL);
		String staticurl = RuntimeContext.getProperties().getProperty(AdServerConstants.CONFIG.PROPERTIES.STATIC_URL);
		if (!staticurl.endsWith("/")) {
			staticurl += "/";
		}
		
		RenderContext context = new RenderContext();
		context.put("banner", banner);
		context.put("staticUrl", staticurl);
		context.put("clickUrl", clickurl);
		
		try {
			return RuntimeContext.getBannerRenderer().render(BannerType.EXTERN.getName().toLowerCase(), context);
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return "";
	}

}
