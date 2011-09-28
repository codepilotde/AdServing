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
package net.mad.ads.manager.web.theme;

import org.apache.wicket.request.resource.ResourceReference;
import org.odlabs.wiquery.ui.themes.WiQueryCoreThemeResourceReference;

/**
 * @author thorsten marx
 * 
 */
public class SmoothnessTheme extends UITheme {

	private static final long serialVersionUID = 1L;

	public static final ResourceReference THEME = new WiQueryCoreThemeResourceReference("smoothness");

	private static SmoothnessTheme instance;

	/**
	 * @param name
	 */
	private SmoothnessTheme() {
		super("Smoothness");
	}

	@Override
	public ResourceReference getTheme() {
		return THEME;
	}

	public static SmoothnessTheme getInstance() {
		if (instance == null) {
			instance = new SmoothnessTheme();
		}
		return instance;
	}

}
