package net.mad.ads.manager.web.help;



import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class HelpPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HelpPage(final PageParameters parameters) {
		add(new Label("myLable", "Hallo das ist doch toll"));
		// TODO Add your page's components here
		
		
		
	}
}
