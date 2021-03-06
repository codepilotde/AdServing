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
package net.mad.ads.base.api.model.site;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.mad.ads.base.api.model.ExtendedBaseModel;

/**
 * A simpe page object to manage different pages
 * 
 * pages are divided into zones and zones are divided into places
 * the combination of page, zone and place is ad AdSlot
 * 
 * 
 * @author thorsten
 *
 */
public class Site extends ExtendedBaseModel {
	/*
	 * Url of the page
	 */
	private String url;

	private Set<Place> places = new HashSet<Place>();
	
	public Site () {
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * @return the places
	 */
	public Set<Place> getPlaces() {
		return places;
	}


	/**
	 * @param places the places to set
	 */
	public void setPlaces(Set<Place> places) {
		this.places = places;
	}
	
	

}
