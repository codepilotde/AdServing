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
package net.mad.ads.db;

public final class AdDBConstants {

	private AdDBConstants() {
	}
	
	public static final String ADDB_BANNER_ID = "banner_id";
	public static final String ADDB_BANNER_TYPE = "banner_type";
	public static final String ADDB_BANNER_FORMAT = "banner_format";
	public static final String ADDB_BANNER_PRODUCT = "banner_product";
	public static final String ADDB_BANNER_PRODUCT_FALSE = "0";
	public static final String ADDB_BANNER_PRODUCT_TRUE = "1";
	
	public static final String ADDB_BANNER_TIME_FROM = "banner_time_from";
	public static final String ADDB_BANNER_TIME_TO = "banner_time_to";
	public static final String ADDB_BANNER_TIME_ALL = "all";
	public static final String ADDB_BANNER_DATE_FROM = "banner_date_from";
	public static final String ADDB_BANNER_DATE_TO = "banner_date_to";
	public static final String ADDB_BANNER_DATE_ALL = "all";
	public static final String ADDB_BANNER_DAY = "banner_day";
	public static final String ADDB_BANNER_DAY_ALL = "0";
	public static final String ADDB_BANNER_STATE = "banner_state";
	public static final String ADDB_BANNER_STATE_ALL = "0";
	public static final String ADDB_BANNER_COUNTRY = "banner_country";
	public static final String ADDB_BANNER_COUNTRY_ALL = "all";
	
	public static final String ADDB_BANNER_KEYWORD = "banner_keyword";
	public static final String ADDB_BANNER_KEYWORD_ALL = "all";
	public static final String ADDB_BANNER_SITE = "banner_site";
	public static final String ADDB_BANNER_SITE_ALL = "all";
	
	public static final String ADDB_BANNER_ADSLOT = "banner_adslot";
	public static final String ADDB_BANNER_ADSLOT_ALL = "all";
	
	public static final String ADDB_BANNER_SITE_EXCLUDE = "banner_site_exclude";
	// TODO: Felder für das Targeting
}
