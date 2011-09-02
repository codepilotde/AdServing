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
		}
	}
	
	public final class Cookie {
		public static final String USERID = "userid";
	}
}
