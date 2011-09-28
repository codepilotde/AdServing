package net.mad.ads.manager.web.pages.dashboard;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.CssPackageResource;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;

public class Widget extends Panel {
	public Widget(String title, Panel panel) {
		super("widget");
		
		
		add(new Label("title", title));
		add(panel);
	}


	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		// TODO Auto-generated method stub
		super.renderHead(response);
		
		response.renderCSSReference(new PackageResourceReference(Widget.class, "widget.css"));
	}
	
	
}