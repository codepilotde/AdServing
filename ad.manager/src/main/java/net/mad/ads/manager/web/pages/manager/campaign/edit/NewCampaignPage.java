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
package net.mad.ads.manager.web.pages.manager.campaign.edit;



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
import net.mad.ads.base.api.model.ads.Campaign;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.manager.RuntimeContext;
import net.mad.ads.manager.web.pages.BasePage;
import net.mad.ads.manager.web.pages.manager.site.SiteManagerPage;

public class NewCampaignPage extends BasePage {
	
	private static final Logger logger = LoggerFactory.getLogger(NewCampaignPage.class);

	private static final long serialVersionUID = -3079163120006125732L;

	public NewCampaignPage() {
		super();
		
		add(new FeedbackPanel("feedback"));
		add(new InputForm("inputForm"));
		
		add(new Link<Void>("backLink") {
			@Override
			public void onClick() {
				setResponsePage(new SiteManagerPage());
			}
		}.add(new ButtonBehavior()));
	}

	private class InputForm extends Form<Campaign> {
		/**
		 * Construct.
		 * 
		 * @param name
		 *            Component name
		 */
		@SuppressWarnings("serial")
		public InputForm(String name) {
			super(name, new CompoundPropertyModel<Campaign>(
					new Campaign()));

			
			add(new RequiredTextField<String>("name").setRequired(true));

			add(new TextArea<String>("description").setRequired(true));
			
			add(new Button("saveButton").add(new ButtonBehavior()));

			add(new Button("resetButton") {
				@Override
				public void onSubmit() {
					setResponsePage(NewCampaignPage.class);
				}
			}.setDefaultFormProcessing(false).add(new ButtonBehavior()));
		}

		/**
		 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public void onSubmit() {
			// Form validation successful. Display message showing edited model.
			
			Campaign campaign = (Campaign) getDefaultModelObject();
			try {
				RuntimeContext.getCampaignService().add(campaign);
				
				// Weiterleitung auf EditCampaignPage
				setResponsePage(new EditCampaignPage(campaign));
			} catch (ServiceException e) {
				logger.error("", e);
				error(getPage().getString("error.saving.campaign"));
			}

		}
	}
}
