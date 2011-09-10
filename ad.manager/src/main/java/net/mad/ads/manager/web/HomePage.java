package net.mad.ads.manager.web;


import net.mad.ads.manager.web.help.HelpPage;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));
		// TODO Add your page's components here
		
		add(new BookmarkablePageLink<Void>("myPageLink", HelpPage.class));
		
		
	}
}
