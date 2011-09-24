/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
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

import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.service.site.SiteService;
import net.mad.ads.manager.RuntimeContext;

import org.apache.wicket.model.LoadableDetachableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetachableSiteModel extends LoadableDetachableModel<Site> {
	
	private static final Logger logger = LoggerFactory.getLogger(DetachableSiteModel.class);
	
	private final long id;

	protected SiteService getSiteService() {
		return RuntimeContext.getSiteService();
	}

	/**
	 * @param c
	 */
	public DetachableSiteModel(Site c) {
		this(c.getId());
	}

	/**
	 * @param id
	 */
	public DetachableSiteModel(long id) {
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
		} else if (obj instanceof DetachableSiteModel) {
			DetachableSiteModel other = (DetachableSiteModel) obj;
			return other.id == id;
		}
		return false;
	}

	/**
	 * @see org.apache.wicket.model.LoadableDetachableModel#load()
	 */
	@Override
	protected Site load() {
		// loads contact from the database
		try {
			return getSiteService().findByPrimaryKey(id);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}