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
package net.mad.ads.server.utils;




import java.util.HashMap;
import java.util.Properties;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

import net.mad.ads.base.api.importer.Importer;
import net.mad.ads.base.api.render.BannerRenderer;
import net.mad.ads.base.api.render.impl.freemarker.FreemarkerBannerRenderer;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.utils.logging.LogWrapper;
import net.mad.ads.common.template.TemplateManager;
import net.mad.ads.db.db.AdDB;
import net.mad.ads.services.geo.IPLocationDB;


public class RuntimeContext {
	private static HashMap<String, HashMap<String, Object>> configuration = new HashMap<String, HashMap<String,Object>>();
	/**
	 * Properties, die beim starten der Anwendungen geladen werden
	 */
	private static Properties properties = new Properties();
	
	public static LogWrapper clickLogger; // = new LogWrapper(RuntimeContext.class, "logger_clicks.properties");
    public static LogWrapper impressionLogger; // = new LogWrapper(RuntimeContext.class, "logger_impression.properties");
	
	private static BannerRenderer bannerRenderer = new FreemarkerBannerRenderer();
	
	private static TemplateManager templateManager = null;
	
	
	private static String enviroment = null; 
	
	// Banner-Datenbank
	private static AdDB adDB = null;
	
	private static IPLocationDB ipDB = null;
	
	private static TrackingService trackService = null;
	
	private static Importer importer = null;
	
	

	/*
	 * Der Cache enthält Eine Liste mit allen BannerIDs f�r einen einzelnen Request, 
	 * dadruch werden doppelte Anzeigen eines Banners auf einer Seite verhindert.
	 * 
	 * Achtung: Diese einfach Implementierung verlangt, dass alle Request die von einer Seite kommen
	 * von dem selben AdServer behandelt werden, da die Daten über die angezeigten Banner nicht
	 * verteilt werden.
	 * In Zukunft könnte man eine Implementierung über den TrackingService anstreben
	 */
//	private static SimpleCache<List<String>> requestBanners = new SimpleCache<List<String>>(5);
//	
//	public static SimpleCache<List<String>> getRequestBanners () {
//		return requestBanners;
//	}
	
	public static Importer getImporter() {
		return importer;
	}


	public static void setImporter(Importer importer) {
		RuntimeContext.importer = importer;
	}

	/*
	 * Neue Implementierung auf basis der Infinispan Cache Implementierung.
	 * Infinispan bietet einen verteilten Cache
	 */
	public static EmbeddedCacheManager cacheManager = null;
	public static Cache<String, Boolean> requestBanners = null;
			
	public static Cache<String, Boolean> getRequestBanners () {
		return requestBanners;
	}
	
	
	public static TemplateManager getTemplateManager() {
		return templateManager;
	}
	public static void setTemplateManager(TemplateManager templateManager) {
		RuntimeContext.templateManager = templateManager;
	}
	public static TrackingService getTrackService() {
		return trackService;
	}
	public static void setTrackService(TrackingService trackService) {
		RuntimeContext.trackService = trackService;
	}
	public static BannerRenderer getBannerRenderer() {
		return bannerRenderer;
	}
	public static IPLocationDB getIpDB() {
		return ipDB;
	}
	public static void setIpDB(IPLocationDB ipDB) {
		RuntimeContext.ipDB = ipDB;
	}
	public static final AdDB getAdDB() {
		return adDB;
	}
	public static final void setAdDB(AdDB adDB) {
		RuntimeContext.adDB = adDB;
	}
	public static String getEnviroment() {
		return enviroment;
	}
	public static void setEnviroment(String enviroment) {
		RuntimeContext.enviroment = enviroment;
	}

	public static void setProperties (Properties props) {
		properties = props;
	}
	public static Properties getProperties () {
		return properties;
	}
	public static int getIntProperty (String key, int defaultValue) {
		if (properties.containsKey(key)) {
			return Integer.valueOf(properties.getProperty(key));
		}

		return defaultValue;
	}
	
	public static boolean getBooleanProperty (String key, boolean defaultValue) {
		if (properties.containsKey(key)) {
			return Boolean.parseBoolean(properties.getProperty(key));
		}

		return defaultValue;
	}
	
	public static void setConfiguration(String config, String key, Object value) {
		if (!configuration.containsKey(config)) {
			configuration.put(config, new HashMap<String, Object>());
		}
		configuration.get(config).put(key, value);
	}

	public static Object getConfiguration (String config, String key) {
		if (configuration.containsKey(config)) {
			return configuration.get(config).get(key);
		}
		return null;
	}

	
}
