package net.mad.ads.db.definition.impl.banner.flash;

import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.impl.banner.AbstractBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;

/**
 * Einfaches Flashbanner
 * 
 * @author tmarx
 *
 */
public class FlashBannerDefinition extends AbstractBannerDefinition {
	
	private String movieUrl = null;
	private int minFlashVersion = -1;
	private String fallbackImageUrl = null;
	
	public FlashBannerDefinition () {
		super(BannerType.FLASH);
	}
	
	public final String getMovieUrl() {
		return movieUrl;
	}

	public final void setMovieUrl(String movieUrl) {
		this.movieUrl = movieUrl;
	}

	public int getMinFlashVersion() {
		return minFlashVersion;
	}

	public void setMinFlashVersion(int minFlashVersion) {
		this.minFlashVersion = minFlashVersion;
	}

	public String getFallbackImageUrl() {
		return fallbackImageUrl;
	}

	public void setFallbackImageUrl(String fallbackImageUrl) {
		this.fallbackImageUrl = fallbackImageUrl;
	}
	
	
	
}
