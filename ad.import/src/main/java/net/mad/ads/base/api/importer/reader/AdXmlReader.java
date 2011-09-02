package net.mad.ads.base.api.importer.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import net.mad.ads.db.AdDBConstants;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.Keyword;
import net.mad.ads.db.definition.impl.banner.expandable.ExpandableImageBannerDefinition;
import net.mad.ads.db.definition.impl.banner.extern.ExternBannerDefinition;
import net.mad.ads.db.definition.impl.banner.flash.FlashBannerDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.enums.Day;
import net.mad.ads.db.enums.State;
import net.mad.ads.db.utils.mapper.BannerTypeMapping;

public class AdXmlReader {
	public static BannerDefinition readBannerDefinition (String filename) throws IOException{
		
		SAXBuilder builder = new SAXBuilder();
	    Document doc;
		try {
			doc = builder.build(new File(filename));
			
			// Get the root element
		    Element root = doc.getRootElement();
		    
		    String type = root.getAttributeValue("type");
		    BannerDefinition banner = BannerTypeMapping.getInstance().getDefinition(type);
		    
		    banner.setId(root.getAttributeValue("id"));
		    Element fe = root.getChild("format");
		    banner.setFormat(BannerFormat.valueOf(fe.getAttributeValue("name")));
		    
		    fe = root.getChild("targetUrl");
		    banner.setTargetUrl(fe.getTextTrim());
		    
		    fe = root.getChild("linkTarget");
		    if (fe != null) {
		    	banner.setLinkTarget(fe.getTextTrim());
		    }
		    
		    fe = root.getChild("linkTitle");
		    if (fe != null) {
		    	banner.setLinkTitle(fe.getTextTrim());
		    }
		    
		    fe = root.getChild("product");
		    if (fe != null) {
		    	banner.setProduct(fe.getTextTrim());
		    }
		    
		    
		    
		    banner = ConditionReader.processConditions(banner, root.getChild("condition"));
		    
		    banner = processBannerType(banner, root);
		    
		    return banner;
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	private static BannerDefinition processBannerType (BannerDefinition definition, Element banner) {
		
		switch (definition.getType()) {
			case IMAGE:
				return processImageBannerDefinition(definition, banner);
			case FLASH:
				return processFlashBannerDefinition(definition, banner);
			case EXTERN:
				return processExternBannerDefinition(definition, banner);
			case EXPANDABLEIMAGE:
				processImageBannerDefinition(definition, banner);
				return processExpandableImageBannerDefinition(definition, banner);
		}
		
		return definition;
	}
	
	private static BannerDefinition processExpandableImageBannerDefinition (BannerDefinition definition, Element banner) {
		
		
		((ExpandableImageBannerDefinition)definition).setExpandedImageUrl(banner.getChildTextTrim("expandedImageUrl"));
		((ExpandableImageBannerDefinition)definition).setExpandedImageWidth(banner.getChildTextTrim("expandedImageWidth"));
		((ExpandableImageBannerDefinition)definition).setExpandedImageHeight(banner.getChildTextTrim("expandedImageHeight"));
		
		return definition;
	}
	
	private static BannerDefinition processImageBannerDefinition (BannerDefinition definition, Element banner) {
		
		((ImageBannerDefinition)definition).setImageUrl(banner.getChildTextTrim("imageUrl"));
		
		return definition;
	}
	
	private static BannerDefinition processFlashBannerDefinition (BannerDefinition definition, Element banner) {
		// Url auf den Flashfilm
		((FlashBannerDefinition)definition).setMovieUrl(banner.getChildTextTrim("movieUrl"));
		// Die minimale Flashversion
		if (banner.getChild("minFlashVersion") != null) {
			((FlashBannerDefinition)definition).setMinFlashVersion(Integer.parseInt(banner.getChildTextTrim("minFlashVersion")));
		}
		// Fallback für das Imagebanner
		((FlashBannerDefinition)definition).setFallbackImageUrl(banner.getChildTextTrim("fallbackImageUrl"));
		
		return definition;
	}
	
	private static BannerDefinition processExternBannerDefinition (BannerDefinition definition, Element banner) {
		
		Element e = banner.getChild("externContent");
		if (e != null) {
			
			((ExternBannerDefinition)definition).setExternContent(e.getText());
		}
		
		return definition;
	}
}