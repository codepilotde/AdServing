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
package net.mad.ads.manager;

import java.util.Properties;

import net.mad.ads.base.api.service.site.PlaceService;
import net.mad.ads.base.api.service.site.SiteService;
import net.mad.ads.base.api.service.site.ZoneService;
import net.mad.ads.base.api.service.user.UserService;
import net.mad.ads.base.api.track.TrackingService;

import org.hibernate.SessionFactory;

public final class RuntimeContext {
	
	/*
	 * No instance of the RuntimeContext
	 */
	private RuntimeContext(){}
	
	/**
	 * Properties, die beim starten der Anwendungen geladen werden
	 */
	private static Properties properties = new Properties();
	
	private static SessionFactory sessionFactory;
	
	private static UserService userService;
	private static SiteService siteService;
	private static ZoneService zoneService;
	private static PlaceService placeService;
	private static TrackingService trackingService;
	
	
	
	/**
	 * @return the siteService
	 */
	public static SiteService getSiteService() {
		return siteService;
	}

	/**
	 * @param siteService the siteService to set
	 */
	public static void setSiteService(SiteService siteService) {
		RuntimeContext.siteService = siteService;
	}

	/**
	 * @return the zoneService
	 */
	public static ZoneService getZoneService() {
		return zoneService;
	}

	/**
	 * @param zoneService the zoneService to set
	 */
	public static void setZoneService(ZoneService zoneService) {
		RuntimeContext.zoneService = zoneService;
	}

	/**
	 * @return the placeService
	 */
	public static PlaceService getPlaceService() {
		return placeService;
	}

	/**
	 * @param placeService the placeService to set
	 */
	public static void setPlaceService(PlaceService placeService) {
		RuntimeContext.placeService = placeService;
	}

	/**
	 * @return the trackingService
	 */
	public static TrackingService getTrackingService() {
		return trackingService;
	}

	/**
	 * @param trackingService the trackingService to set
	 */
	public static void setTrackingService(TrackingService trackingService) {
		RuntimeContext.trackingService = trackingService;
	}

	public static UserService getUserService() {
		return userService;
	}

	public static void setUserService(UserService userService) {
		RuntimeContext.userService = userService;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		RuntimeContext.sessionFactory = sessionFactory;
	}

	public static void setProperties (Properties props) {
		properties = props;
	}
	public static Properties getProperties () {
		return properties;
	}
	
}
