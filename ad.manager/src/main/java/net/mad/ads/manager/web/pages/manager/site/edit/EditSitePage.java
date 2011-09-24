package net.mad.ads.manager.web.pages.manager.site.edit;



import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.ui.button.ButtonBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.manager.RuntimeContext;
import net.mad.ads.manager.utils.DateUtil;
import net.mad.ads.manager.web.pages.BasePage;

public class EditSitePage extends BasePage {
	
	private static final Logger logger = LoggerFactory.getLogger(EditSitePage.class);

	private static final long serialVersionUID = -3079163120006125732L;

	public EditSitePage(Site site) {
		super();
		
		add(new Label("sitename", site.getName()));
		add(new Label("name", site.getName()));
		add(new Label("description", site.getDescription()));
		add(new Label("created", DateUtil.format(site.getCreated())));
		add(new Label("url", site.getUrl()));
	}
}
