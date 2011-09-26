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
package net.mad.ads.base.api.render;

import net.mad.ads.base.api.render.impl.freemarker.FreemarkerBannerRenderer;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;

public class ImageBannerRenderTest {
	public static void main (String [] args) throws Exception {
		
		ImageBannerDefinition banner = new ImageBannerDefinition();
		banner.setFormat(BannerFormat.HALF_BANNER);
		banner.setImageUrl("test.jpg");
		
		RenderContext context = new RenderContext();
		context.put("banner", banner);
		
		
		BannerRenderer tm = new FreemarkerBannerRenderer();
		tm.init("testdata/templates/banner");
		tm.registerTemplate("image", "image.ftl");
		
		System.out.println(tm.render("image", context));
	}
}
