package net.mad.ads.db.definition.impl.banner.image;

import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.impl.banner.AbstractBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;

/**
 * Einfaches Imagebanner
 * 
 * @author tmarx
 *
 */
public class ImageBannerDefinition extends AbstractBannerDefinition {
	
	private String imageUrl = null;
	
	/**
	 * Konstruktor für die vom ImageBanner abgeleiteten BannerTypen wie zum Beispiel das ExpandableImageBanner
	 * 
	 * @param type Der BannerType
	 */
	protected ImageBannerDefinition (BannerType type) {
		super(type);
	}
	public ImageBannerDefinition () {
		super(BannerType.IMAGE);
	}
	
	public final String getImageUrl() {
		return imageUrl;
	}

	public final void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
