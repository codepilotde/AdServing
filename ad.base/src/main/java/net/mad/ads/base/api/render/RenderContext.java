package net.mad.ads.base.api.render;

import java.util.HashMap;

import net.mad.ads.base.api.BaseObject;
import net.mad.ads.db.definition.BannerDefinition;

public class RenderContext extends BaseObject {
	
	public static final String BANNER_DEFINITION = "bannerdefinition";
	
	public RenderContext () {
		super();
	}
	
	public void setBannerDefinition (BannerDefinition banner) {
		put(BANNER_DEFINITION, banner);
	}
	
	public BannerDefinition getBannerDefinition () {
		return get(BANNER_DEFINITION, BannerDefinition.class, null);
	}
	
	
	public String getTemplateName () {
		BannerDefinition bd = getBannerDefinition();
		
		String format = bd.getFormat().getCompoundName();
		String type = bd.getType().getTypeAsString();
		
		return (format + type).toLowerCase();
	}
}
