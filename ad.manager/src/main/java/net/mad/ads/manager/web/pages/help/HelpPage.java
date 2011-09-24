package net.mad.ads.manager.web.pages.help;



import net.mad.ads.manager.web.pages.BasePage;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.StringResourceModel;

public class HelpPage extends BasePage {
	private static final long serialVersionUID = 1L;

	public HelpPage() {
		super();
		setPageTitle(new StringResourceModel("page.title", this, null));
	}
}
