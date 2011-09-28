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
package net.mad.ads.server.utils;

public abstract class AdServerConstants {

	
	public final class PATHES {
		public static final String WEB = "web";
	}
	
	public final class CONFIG {
		public static final String PATHES = "pathes";
		
		public final class PROPERTIES {
			public static final String BANNER_IMPORT_DIRECOTRY = "banner.import.dir";
			public static final String BANNER_DATA_DIRECOTRY = "banner.data.dir";
			public static final String CLICK_URL = "click.url";
			public static final String STATIC_URL = "static.url";
			public static final String IPDB_DIR = "ipdb.dir";
			public static final String TRACK_DIR = "track.dir";
			public static final String BANNER_TEMPLATE_DIR = "banner.template.dir";
			
			public static final String ADSERVER_URL = "adserver.url";
			public static final String ADSERVER_SELECT_URL = "adserver.select.url";
			
			public static final String SERVICES_TRACKING_CASSANDRA_HOST = "services.tracking.cassandra.host";
			public static final String SERVICES_TRACKING_CASSANDRA_CLUSTER = "services.tracking.cassandra.cluster";			
			
			public static final String COOKIE_DOMAIN = "cookie.domain";
			
			public static final String TRACKINGSERVICE_CLASS = "tracking.service.class";
			public static final String IPLOCATIONSERVICE_CLASS = "iplocation.service.class";
		}
	}
	
	public final class Cookie {
		public static final String USERID = "userid";
	}
}
