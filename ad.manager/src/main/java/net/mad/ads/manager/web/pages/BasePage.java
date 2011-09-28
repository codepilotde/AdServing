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
package net.mad.ads.manager.web.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import net.mad.ads.manager.web.layout.LayoutPage;

/**
 * A base page accessible by everybody - no authorization required.
 * 
 */
public class BasePage extends LayoutPage {

	public BasePage() {
		add(new Label("pageTitle", new StringResourceModel("page.title", this,
				null)));
	}

	protected void setPageTitle(IModel<?> model) {
		get("pageTitle").setDefaultModel(model);
	}

}
