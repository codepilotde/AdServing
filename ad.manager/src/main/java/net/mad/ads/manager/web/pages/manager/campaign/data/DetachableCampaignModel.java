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
package net.mad.ads.manager.web.pages.manager.campaign.data;

import net.mad.ads.base.api.model.ads.Campaign;
import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.service.banner.CampaignService;
import net.mad.ads.base.api.service.site.PlaceService;
import net.mad.ads.base.api.service.site.SiteService;
import net.mad.ads.manager.RuntimeContext;

import org.apache.wicket.model.LoadableDetachableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetachableCampaignModel extends LoadableDetachableModel<Campaign> {
	
	private static final Logger logger = LoggerFactory.getLogger(DetachableCampaignModel.class);
	
	private final long id;

	protected CampaignService getCampaignService() {
		return RuntimeContext.getCampaignService();
	}

	/**
	 * @param c
	 */
	public DetachableCampaignModel(Campaign c) {
		this(c.getId());
	}

	/**
	 * @param id
	 */
	public DetachableCampaignModel(long id) {
		if (id == 0) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Long.valueOf(id).hashCode();
	}

	/**
	 * used for dataview with ReuseIfModelsEqualStrategy item reuse strategy
	 * 
	 * @see org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof DetachableCampaignModel) {
			DetachableCampaignModel other = (DetachableCampaignModel) obj;
			return other.id == id;
		}
		return false;
	}

	/**
	 * @see org.apache.wicket.model.LoadableDetachableModel#load()
	 */
	@Override
	protected Campaign load() {
		// loads contact from the database
		try {
			return getCampaignService().findByPrimaryKey(id);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}