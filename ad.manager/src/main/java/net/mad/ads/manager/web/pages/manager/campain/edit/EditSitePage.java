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
package net.mad.ads.manager.web.pages.manager.campain.edit;

import org.apache.wicket.AttributeModifier;
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
import org.odlabs.wiquery.ui.button.ButtonBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.manager.RuntimeContext;
import net.mad.ads.manager.utils.DateUtil;
import net.mad.ads.manager.web.component.confirm.ConfirmLink;
import net.mad.ads.manager.web.pages.BasePage;
import net.mad.ads.manager.web.pages.manager.site.SiteManagerPage;
import net.mad.ads.manager.web.pages.manager.site.data.PlaceDataProvider;
import net.mad.ads.manager.web.pages.manager.site.data.SiteDataProvider;

public class EditSitePage extends BasePage {

	private static final Logger logger = LoggerFactory
			.getLogger(EditSitePage.class);

	private static final long serialVersionUID = -3079163120006125732L;

	public EditSitePage(final Site site) {
		super();

		add(new Label("sitename", site.getName()));

		add(new FeedbackPanel("feedback"));
		add(new InputForm("inputForm", site));

		final FeedbackPanel placeFeed = new FeedbackPanel("placeFeedback");
		add(placeFeed);

		add(new Link<Void>("newPlace") {
			@Override
			public void onClick() {
				setResponsePage(new NewPlacePage(site));
			}
		}.add(new ButtonBehavior()));

		add(new Link<Void>("backLink") {
			@Override
			public void onClick() {
				setResponsePage(new SiteManagerPage());
			}
		}.add(new ButtonBehavior()));

		DataView<Place> dataView = new DataView<Place>("pageable",
				new PlaceDataProvider(site)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Place> item) {
				final Place place = item.getModelObject();
				item.add(new Label("id", String.valueOf(place.getId())));
				item.add(new Label("name", place.getName()));
				item.add(new Label("created",
						DateUtil.format(place.getCreated())));
				item.add(new EditPanel("editPlace", item.getModel()));

				item.add(new ConfirmLink<Void>("deletePlace", getPage()
						.getString("dialog.confirm.message")) {
					@Override
					public void onClick() {
						try {
							RuntimeContext.getPlaceService().delete(place);
							setResponsePage(getPage());
						} catch (Exception e) {
							logger.error("", e);
							placeFeed.error(getPage().getString(
									"place.delete.error"));
						}
					}
				});

				item.add(AttributeModifier.replace("class",
						new AbstractReadOnlyModel<String>() {
							private static final long serialVersionUID = 1L;

							@Override
							public String getObject() {
								return (item.getIndex() % 2 == 1) ? "even"
										: "odd";
							}
						}));
			}
		};

		dataView.setItemsPerPage(5);
		add(dataView);

		add(new PagingNavigator("navigator", dataView));
	}

	private class InputForm extends Form<Site> {
		/**
		 * Construct.
		 * 
		 * @param name
		 *            Component name
		 */
		@SuppressWarnings("serial")
		public InputForm(String name, Site site) {
			super(name, new CompoundPropertyModel<Site>(site));

			add(new RequiredTextField<String>("name").setRequired(true));

			add(new TextArea<String>("description").setRequired(true));

			add(new RequiredTextField<String>("url").setRequired(true));

			add(new Button("saveButton").add(new ButtonBehavior()));
		}

		/**
		 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public void onSubmit() {
			// Form validation successful. Display message showing edited model.

			Site site = (Site) getDefaultModelObject();
			try {
				RuntimeContext.getSiteService().update(site);

				// Weiterleitung auf EditSitePage
				setResponsePage(new EditSitePage(site));
			} catch (ServiceException e) {
				logger.error("", e);
				error(getPage().getString("error.saving.site"));
			}

		}
	}

	class EditPanel extends Panel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5695018920646174059L;

		/**
		 * @param id
		 *            component id
		 * @param model
		 *            model for contact
		 */
		public EditPanel(String id, IModel<Place> model) {
			super(id, model);
			add(new Link<Void>("edit") {
				@Override
				public void onClick() {
					setResponsePage(new EditPlacePage((Place) getParent()
							.getDefaultModelObject()));
				}
			});
		}
	}
}
