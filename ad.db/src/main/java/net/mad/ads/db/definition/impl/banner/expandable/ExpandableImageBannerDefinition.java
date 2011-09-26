/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
