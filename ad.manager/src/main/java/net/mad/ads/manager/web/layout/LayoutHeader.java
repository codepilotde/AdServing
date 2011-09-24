/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
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

import net.mad.ads.manager.web.pages.HomePage;
import net.mad.ads.manager.web.pages.SignOutPage;
import net.mad.ads.manager.web.pages.help.HelpPage;
import net.mad.ads.manager.web.pages.manager.ManagerPage;
import net.mad.ads.manager.web.pages.manager.site.SiteManagerPage;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.HomePageMapper;

/**
 * Navigation panel for the examples project.
 * 
 */
public final class LayoutHeader extends Panel {

	public LayoutHeader(String id, String exampleTitle, WebPage page) {
		super(id);
//		add(new Label("exampleTitle", exampleTitle));
		
		add(new BookmarkablePageLink<Void>("helpLink", HelpPage.class));
		add(new BookmarkablePageLink<Void>("logoutLink", SignOutPage.class));
		add(new BookmarkablePageLink<Void>("dashboardLink", HomePage.class));
		add(new BookmarkablePageLink<Void>("siteManagerLink", SiteManagerPage.class));
		 
	}
}
