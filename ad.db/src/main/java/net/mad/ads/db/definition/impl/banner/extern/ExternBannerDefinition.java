package net.mad.ads.db.definition.impl.banner.extern;

import net.mad.ads.db.definition.impl.banner.AbstractBannerDefinition;
import net.mad.ads.db.enums.BannerType;

public class ExternBannerDefinition extends AbstractBannerDefinition {

	private String externContent = null;
	
	public ExternBannerDefinition() {
		super(BannerType.EXTERN);
	}

	public final String getExternContent() {
		return externContent;
	}

	public final void setExternContent(String externContent) {
		this.externContent = externContent;
	}

	
}
