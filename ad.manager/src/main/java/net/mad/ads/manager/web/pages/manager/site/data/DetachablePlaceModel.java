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
package net.mad.ads.manager.web.pages.manager.site.data;

import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.model.site.Zone;
import net.mad.ads.base.api.service.site.PlaceService;
import net.mad.ads.base.api.service.site.SiteService;
import net.mad.ads.base.api.service.site.ZoneService;
import net.mad.ads.manager.RuntimeContext;

import org.apache.wicket.model.LoadableDetachableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetachablePlaceModel extends LoadableDetachableModel<Place> {
	
	private static final Logger logger = LoggerFactory.getLogger(DetachablePlaceModel.class);
	
	private final long id;

	protected PlaceService getPlaceService() {
		return RuntimeContext.getPlaceService();
	}

	/**
	 * @param c
	 */
	public DetachablePlaceModel(Place c) {
		this(c.getId());
	}

	/**
	 * @param id
	 */
	public DetachablePlaceModel(long id) {
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
		} else if (obj instanceof DetachablePlaceModel) {
			DetachablePlaceModel other = (DetachablePlaceModel) obj;
			return other.id == id;
		}
		return false;
	}

	/**
	 * @see org.apache.wicket.model.LoadableDetachableModel#load()
	 */
	@Override
	protected Place load() {
		// loads contact from the database
		try {
			return getPlaceService().findByPrimaryKey(id);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}