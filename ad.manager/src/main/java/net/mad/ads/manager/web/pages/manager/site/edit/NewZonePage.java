package net.mad.ads.manager.web.pages.manager.site.edit;



import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.ui.button.ButtonBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.model.site.Zone;
import net.mad.ads.manager.RuntimeContext;
import net.mad.ads.manager.web.pages.BasePage;

public class NewZonePage extends BasePage {
	
	private static final Logger logger = LoggerFactory.getLogger(NewZonePage.class);

	private static final long serialVersionUID = -3079163120006125732L;

	private Site site;
	public NewZonePage(final Site site) {
		super();
		this.site = site;
		
		add(new FeedbackPanel("feedback"));
		add(new InputForm("inputForm"));
		
		add(new Link<Void>("backLink") {
			@Override
			public void onClick() {
				setResponsePage(new EditSitePage(site));
			}
		}.add(new ButtonBehavior()));
	}

	private class InputForm extends Form<Zone> {
		/**
		 * Construct.
		 * 
		 * @param name
		 *            Component name
		 */
		@SuppressWarnings("serial")
		public InputForm(String name) {
			super(name, new CompoundPropertyModel<Zone>(
					new Zone()));

			

			add(new RequiredTextField<String>("name").setRequired(true));

			add(new TextArea<String>("description").setRequired(true));

			
			add(new Button("saveButton").add(new ButtonBehavior()));

			add(new Button("resetButton") {
				@Override
				public void onSubmit() {
					setResponsePage(NewZonePage.class);
				}
			}.setDefaultFormProcessing(false).add(new ButtonBehavior()));
		}

		/**
		 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public void onSubmit() {
			// Form validation successful. Display message showing edited model.
			
			Zone zone = (Zone) getDefaultModelObject();
			zone.setSite(site);
			try {
				RuntimeContext.getZoneService().add(zone);
				
				// Weiterleitung auf EditSitePage
				setResponsePage(new EditZonePage(zone));
			} catch (ServiceException e) {
				logger.error("", e);
				error(getPage().getString("error.saving.zone"));
			}

		}
	}
}
