package net.mad.ads.manager.web.pages.manager.site.edit;



import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.ui.button.ButtonBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.manager.RuntimeContext;
import net.mad.ads.manager.utils.DateUtil;
import net.mad.ads.manager.web.pages.BasePage;
import net.mad.ads.manager.web.pages.manager.site.SiteManagerPage;
import net.mad.ads.manager.web.pages.manager.site.data.SiteDataProvider;
import net.mad.ads.manager.web.pages.manager.site.data.ZoneDataProvider;

public class EditPlacePage extends BasePage {
	
	private static final Logger logger = LoggerFactory.getLogger(EditPlacePage.class);

	private static final long serialVersionUID = -3079163120006125732L;
	
	public EditPlacePage(final Place place) {
		super();
		
		add(new Label("placename", place.getName()));

		add(new FeedbackPanel("feedback"));
		add(new InputForm("inputForm", place));
		
		add(new Link<Void>("backLink") {
			@Override
			public void onClick() {
				setResponsePage(new EditZonePage(place.getZone()));
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
		public InputForm(String name, final Place place) {
			super(name, new CompoundPropertyModel<Place>(
					place));

			add(new RequiredTextField<String>("name").setRequired(true));

			add(new TextArea<String>("description").setRequired(true));
			
			add(new Button("saveButton").add(new ButtonBehavior()));
		}

		/**
		 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public void onSubmit() {
			// Form validation successful. Display message showing edited model.
			
			Place place = (Place) getDefaultModelObject();
			try {
				RuntimeContext.getPlaceService().update(place);
				
				// Weiterleitung auf EditSitePage
				setResponsePage(new EditPlacePage(place));
			} catch (ServiceException e) {
				logger.error("", e);
				error(getPage().getString("error.saving.place"));
			}

		}
	}
}
