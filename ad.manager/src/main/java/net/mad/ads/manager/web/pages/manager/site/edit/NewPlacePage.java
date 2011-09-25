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
import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.model.site.Zone;
import net.mad.ads.manager.RuntimeContext;
import net.mad.ads.manager.web.pages.BasePage;

public class NewPlacePage extends BasePage {
	
	private static final Logger logger = LoggerFactory.getLogger(NewPlacePage.class);

	private static final long serialVersionUID = -3079163120006125732L;

	private Zone zone;
	public NewPlacePage(final Zone zone) {
		super();
		this.zone = zone;
		
		add(new FeedbackPanel("feedback"));
		add(new InputForm("inputForm"));
		
		add(new Link<Void>("backLink") {
			@Override
			public void onClick() {
				setResponsePage(new EditZonePage(zone));
			}
		}.add(new ButtonBehavior()));
	}

	private class InputForm extends Form<Place> {
		/**
		 * Construct.
		 * 
		 * @param name
		 *            Component name
		 */
		@SuppressWarnings("serial")
		public InputForm(String name) {
			super(name, new CompoundPropertyModel<Place>(
					new Place()));

			add(new RequiredTextField<String>("name").setRequired(true));

			add(new TextArea<String>("description").setRequired(true));

			
			add(new Button("saveButton").add(new ButtonBehavior()));

			add(new Button("resetButton") {
				@Override
				public void onSubmit() {
					setResponsePage(NewPlacePage.class);
				}
			}.setDefaultFormProcessing(false).add(new ButtonBehavior()));
		}

		/**
		 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public void onSubmit() {
			// Form validation successful. Display message showing edited model.
			
			Place place = (Place) getDefaultModelObject();
			place.setZone(zone);
			try {
				RuntimeContext.getPlaceService().add(place);
				
				setResponsePage(new EditPlacePage(place));
			} catch (ServiceException e) {
				logger.error("", e);
				error(getPage().getString("error.saving.place"));
			}

		}
	}
}
