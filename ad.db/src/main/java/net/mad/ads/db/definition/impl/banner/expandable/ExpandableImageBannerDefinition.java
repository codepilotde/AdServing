package net.mad.ads.db.definition.impl.banner.expandable;

import net.mad.ads.db.definition.impl.banner.AbstractBannerDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerType;

/**
 * Expandierendes Banner
 * 
 * Das Banner besteht aus zwei Bildern, einem kleinen und einem Großen.
 * Bei Mouseover dem kleinen wird das große Bild rechts oder links daneben angezeigt.
 * 
 * @author thmarx
 *
 */
public class ExpandableImageBannerDefinition extends ImageBannerDefinition {
	
	private String expandedImageUrl = null;
	
	private String expandedImageWidth = null;
	private String expandedImageHeight = null;
	
	public ExpandableImageBannerDefinition () {
		super(BannerType.EXPANDABLEIMAGE);
	}

	public String getExpandedImageUrl() {
		return expandedImageUrl;
	}

	public void setExpandedImageUrl(String expandedImageUrl) {
		this.expandedImageUrl = expandedImageUrl;
	}

	public String getExpandedImageWidth() {
		return expandedImageWidth;
	}

	public void setExpandedImageWidth(String expandedImageWidth) {
		this.expandedImageWidth = expandedImageWidth;
	}

	public String getExpandedImageHeight() {
		return expandedImageHeight;
	}

	public void setExpandedImageHeight(String expandedImageHeight) {
		this.expandedImageHeight = expandedImageHeight;
	}


	
	
}
