package net.mad.ads.db.definition.impl.banner.text;

import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.impl.banner.AbstractBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;

/**
 * Ein einfacher Werbelink
 * 
 * @author tmarx
 *
 */
public class TextlinkBannerDefinition extends AbstractBannerDefinition {
	
	private String text = null;
	
	public TextlinkBannerDefinition () {
		super(BannerType.TEXTLINK);
	}
	
	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}
}
