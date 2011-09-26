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
package net.mad.ads.db.db.request;


import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.enums.Day;
import net.mad.ads.db.enums.State;
import net.mad.ads.db.utils.geo.GeoLocation;

public class AdRequest {
	/*
	 *  Anzahl der Banner, die geladen werden soll
	 *  
	 *  -1 = alle
	 */
	private int count = -1;
	/*
	 * Liste mit Formaten, die geladen werden sollen
	 */
	private List<BannerFormat> formats = new ArrayList<BannerFormat>();
	
	private List<BannerType> types = new ArrayList<BannerType>();
	
	/*
	 * Conditions
	 * Die vor dem Aufruf gesetzt werden können
	 */
	private Day day = null;
	/*
	 * Das Bundesland, in dem sich der Aufrufer befindet 
	 */
	private State state = null;
	/*
	 * Das Land in dem sich der Aufrufer befindet
	 */
	private Country country = null;
	/*
	 * Die Zeit des Aufrufers
	 */
	private String time = null;
	/*
	 * Das Datum des Aufrufers
	 */
	private String date = null;
	/*
	 * Keywords für die Banner
	 */
	private List<String> keywords = new ArrayList<String>();
	/*
	 * ID der Seite auf der das Banner eingebunden wird
	 */
	private String site = null;

	/*
	 * Geo-Position für die ein Banner angezeigt werden soll
	 */
	private GeoLocation geoLocation = null;
	/*
	 * Radius für gültige Banner um die GeoPosition
	 */
	private int radius;
	/*
	 * Es sollen nur Produkte für diesen Request geliefert werden 
	 */
	private boolean products;
	
	private String adSlot = null;


	public AdRequest () {
		
	}

	public String getAdSlot() {
		return adSlot;
	}



	public void setAdSlot(String adSlot) {
		this.adSlot = adSlot;
	}



	public boolean isProducts() {
		return products;
	}

	public void setProducts(boolean products) {
		this.products = products;
	}
	
	public final GeoLocation getGeoLocation() {
		return geoLocation;
	}
	public final void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}



	public final String getSite () {
		return this.site;
	}
	public final void setSite (String site) {
		this.site = site;
	}
	
	public final List<String> getKeywords() {
		return keywords;
	}

	public final void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}


	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<BannerFormat> getFormats() {
		return formats;
	}

	public void setFormats(List<BannerFormat> formats) {
		this.formats = formats;
	}

	public List<BannerType> getTypes() {
		return types;
	}

	public void setTypes(List<BannerType> types) {
		this.types = types;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public boolean hasConditions () {
		return (
					day != null || state != null || time != null || 
					date != null || country != null || (keywords != null && keywords.size() > 0) || 
					site != null || geoLocation != null);
	}
}
