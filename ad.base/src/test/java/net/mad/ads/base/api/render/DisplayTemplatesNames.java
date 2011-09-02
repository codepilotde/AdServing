package net.mad.ads.base.api.render;

import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;

public class DisplayTemplatesNames {
	public static void main (String [] args) {
		BannerFormat[] formats = BannerFormat.values();
		for (BannerFormat format : formats) {
			BannerType[] types = BannerType.values();
			for (BannerType type : types) {
				System.out.println(getTemplateName(format, type));
			}
		}
	}

	public static String getTemplateName (BannerFormat format, BannerType type) {
		String f = format.getCompoundName();
		String t = type.getName();
		
		return (f  + "_" + t).toLowerCase();
	}
}
