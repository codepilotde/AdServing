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
package net.mad.ads.services.geo;

public class Location {
	
	public static final Location UNKNOWN = new Location("", "", "");
	
	private String regionName = "";
	private String country = "";
	private String city = "";
	
	private String latitude = "";
	private String longitude = "";
	

	public Location (String country, String regionName, String city) {
		this.country = country;
		this.regionName = regionName;
		this.city = city;
	}
	
	public Location (String country, String regionName, String city, String latitude, String lonitude) {
		this(country, regionName, city);
		this.latitude = latitude;
		this.longitude = lonitude;
	}
	
	
	
	
	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getCountry() {
		return country;
	}

	public String getRegionName() {
		return regionName;
	}

	public String getCity() {
		return city;
	}
	
	
	
}
