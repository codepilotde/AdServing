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
package net.mad.ads.manager.web.layout;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.Strings;

/**
 * Base class for all example pages.
 * 
 */
public class LayoutPage extends WebPage {
	
	private static final long serialVersionUID = 1L;

	
	public LayoutPage() {
		this(new PageParameters());
	}

	
	public LayoutPage(final PageParameters pageParameters) {
		super(pageParameters);

		final String packageName = getClass().getPackage().getName();
		add(new LayoutHeader("mainNavigation", Strings.afterLast(
				packageName, '.'), this));
		explain();
	}

	public LayoutPage(IModel<?> model) {
		super(model);
	}

	protected void explain() {
	}
}
