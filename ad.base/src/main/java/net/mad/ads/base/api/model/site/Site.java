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
public class Site extends BasePageModel {
	/*
	 * Url of the page
	 */
	private String url;

	private Set<Zone> zones = new HashSet<Zone>();
	
	public Site () {
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * @return the zones
	 */
	public Set<Zone> getZones() {
		return zones;
	}


	/**
	 * @param zones the zones to set
	 */
	public void setZones(Set<Zone> zones) {
		this.zones = zones;
	}
	
	

}
