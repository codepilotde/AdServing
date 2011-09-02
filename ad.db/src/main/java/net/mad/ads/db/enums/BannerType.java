package net.mad.ads.db.enums;

import java.io.Serializable;

/**
 * Bannertypen
 * 
 * Text, Image
 * 
 * @author tmarx
 *
 */
public enum BannerType implements Serializable {
	// Einfaches Banner, das nur den Bannercode eines externen Anbieters wie Zanox oder AdSense Banners enthält
	EXTERN("Extern", 1),
	// Ein ImageBanner
	IMAGE("Image", 2),
	TEXTLINK("Textlink", 3),
	FLASH("Flash", 4),
	EXPANDABLEIMAGE ("ExpandableImage", 5);
	
	private int type = 0;
	private String name = "";
	private BannerType(String name, int type) {
		this.type = type;
		this.name = name;
	}
	public final int getType() {
		return type;
	}
	public final String getTypeAsString() {
		return String.valueOf(type);
	}
	public final String getName () {
		return this.name;
	}
	
	public static BannerType forType (int type) {
		for (BannerType t : BannerType.values()) {
			if (t.getType() == type) {
				return t;
			}
		}
		return null;
	}
}
