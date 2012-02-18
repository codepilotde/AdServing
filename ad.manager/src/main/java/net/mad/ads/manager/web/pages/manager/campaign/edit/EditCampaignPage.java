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

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
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
import org.apache.wicket.model.PropertyModel;
import org.odlabs.wiquery.ui.button.ButtonBehavior;
import org.odlabs.wiquery.ui.tabs.Tabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ads.Campaign;
import net.mad.ads.base.api.model.ads.condition.DateCondition;
import net.mad.ads.base.api.model.ads.condition.TimeCondition;
import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.manager.RuntimeContext;
import net.mad.ads.manager.utils.DateUtil;
import net.mad.ads.manager.web.component.confirm.ConfirmLink;
import net.mad.ads.manager.web.component.listeditor.ListEditor;
import net.mad.ads.manager.web.component.listeditor.ListItem;
import net.mad.ads.manager.web.component.listeditor.RemoveButton;
import net.mad.ads.manager.web.pages.BasePage;
import net.mad.ads.manager.web.pages.manager.campaign.CampaignManagerPage;

public class EditCampaignPage extends BasePage {

	private static final Logger logger = LoggerFactory
			.getLogger(EditCampaignPage.class);

	private static final long serialVersionUID = -3079163120006125732L;

	// private final ListView<TimeCondition> tcListView;
//	private final WebMarkupContainer tcs;

	private final InputForm inputForm;
	
	private final Campaign campaign;

	public EditCampaignPage(final Campaign campaign) {
		super();

		this.campaign = campaign;
		this.inputForm = new InputForm("inputForm", campaign);

		add(new Label("campaignname", campaign.getName()));

		add(inputForm);

		add(new Link<Void>("backLink") {
			@Override
			public void onClick() {
				setResponsePage(new CampaignManagerPage());
			}
		}.add(new ButtonBehavior()));

		

		/*
		 * tcs = new WebMarkupContainer("timeConditions"); tcs.add(tcListView =
		 * new ListView<TimeCondition>("timeConditions", new
		 * PropertyModel<List<TimeCondition>>(this, "timeConditionsList")) {
		 * 
		 * @Override public void populateItem(final ListItem<TimeCondition>
		 * listItem) { final TimeCondition condition =
		 * listItem.getModelObject();
		 * 
		 * listItem.add(new TextField<Time>("from", new Model<Time>(condition
		 * .getFrom()))); listItem.add(new TextField<Time>("to", new
		 * Model<Time>(condition .getFrom())));
		 * 
		 * listItem.add(new DeleteAjaxLink("deleteTimeLink",
		 * listItem.getIndex(), tcs)); } });
		 * tabs.add(tcs.setOutputMarkupId(true));
		 * 
		 * tabs.add(new AjaxLink<Void>("addButton") {
		 * 
		 * @Override public void onClick(AjaxRequestTarget target) {
		 * campaign.getTimeConditions().add(new TimeCondition());
		 * 
		 * target.add(tcs); }
		 * 
		 * }.add(new ButtonBehavior()));
		 */

	}

	private class InputForm extends Form<Campaign> {
		
		private final ListEditor<TimeCondition> timeEditor;
		/**
		 * Construct.
		 * 
		 * @param name
		 *            Component name
		 */
		@SuppressWarnings("serial")
		public InputForm(String name, Campaign campaign) {
			super(name, new CompoundPropertyModel<Campaign>(campaign));

			add(new RequiredTextField<String>("name").setRequired(true));

			add(new TextArea<String>("description").setRequired(true));

			add(new Button("saveButton").add(new ButtonBehavior()));
			
			add(new FeedbackPanel("feedback"));
			
			Tabs tabs = new Tabs("tabs");
			add(tabs);

			timeEditor = new ListEditor<TimeCondition>("timeConditions", new PropertyModel(
					this, "timeConditionsList")) {
				@Override
				protected void onPopulateItem(ListItem<TimeCondition> item) {
					final TimeCondition condition = item.getModelObject();
					item.setModel(new CompoundPropertyModel(item.getModel()));

					item.add(new TextField<Time>("from", new Model<Time>(condition
							.getFrom())));
					item.add(new TextField<Time>("to", new Model<Time>(condition
							.getFrom())));

					item.add(new RemoveButton("remove"));
				}
			};
			
			tabs.add(new Button("addTimeButton")
	        {
	            @Override
	            public void onSubmit()
	            {
	                timeEditor.addItem(new TimeCondition());
	            }
	        }.setDefaultFormProcessing(false));
	        tabs.add(timeEditor);
		}

		/**
		 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public void onSubmit() {
			// Form validation successful. Display message showing edited model.

			Campaign campaign = (Campaign) getDefaultModelObject();
			try {
				RuntimeContext.getCampaignService().update(campaign);

				// Weiterleitung auf EditCampaignPage
				setResponsePage(new EditCampaignPage(campaign));
			} catch (ServiceException e) {
				logger.error("", e);
				error(getPage().getString("error.saving.campaign"));
			}

		}
		
		public List<TimeCondition> getTimeConditionsList() {
			return campaign.getTimeConditions();
		}

		public List<DateCondition> getDateConditionsList() {
			return campaign.getDateConditions();
		}
	}

	/*
	 * private class DeleteAjaxLink extends AjaxLink<Void> {
	 * 
	 * private int index = 0; private Component component;
	 * 
	 * public DeleteAjaxLink(String id, int index, Component component) {
	 * super(id); this.index = index; this.component = component; }
	 * 
	 * @Override public void onClick(AjaxRequestTarget target) {
	 * campaign.getTimeConditions().remove(index);
	 * target.add(tcListView.get(index));
	 * tcListView.remove(tcListView.get(index)); } }
	 */
}
