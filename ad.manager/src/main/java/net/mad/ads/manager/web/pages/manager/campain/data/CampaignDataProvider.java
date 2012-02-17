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
package net.mad.ads.manager.web.pages.manager.campain.data;

import java.util.Iterator;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ads.Campaign;
import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.manager.RuntimeContext;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CampaignDataProvider  implements IDataProvider<Campaign> {
	
	private static final Logger logger = LoggerFactory.getLogger(CampaignDataProvider.class);

	private Site site;
	public CampaignDataProvider (Site site) {
		this.site = site;
	}
	
	@Override
	public void detach() {}

	@Override
	public Iterator<? extends Campaign> iterator(int first, int count) {
		// TODO Auto-generated method stub
		try {
			return RuntimeContext.getCampaignService().findAll(first, count).iterator();
		} catch (ServiceException e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public int size() {
		try {
			return RuntimeContext.getPlaceService().findBySite(site).size();
		} catch (ServiceException e) {
			logger.error("", e);
		}
		return 0;
		
	}

	@Override
	public IModel<Campaign> model(Campaign object) {
		// TODO Auto-generated method stub
		return new DetachableCampaignModel(object);
	}

}
