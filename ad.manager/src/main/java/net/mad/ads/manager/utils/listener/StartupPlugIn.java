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
package net.mad.ads.manager.utils.listener;


import java.io.File;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.EmbeddedBaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.user.User;
import net.mad.ads.base.api.model.user.impl.AdminUser;
import net.mad.ads.base.api.service.HibernateService;
import net.mad.ads.base.api.service.site.HibernatePlaceService;
import net.mad.ads.base.api.service.site.HibernateSiteService;
import net.mad.ads.base.api.service.site.HibernateZoneService;
import net.mad.ads.base.api.service.site.PlaceService;
import net.mad.ads.base.api.service.site.SiteService;
import net.mad.ads.base.api.service.site.ZoneService;
import net.mad.ads.base.api.service.user.HibernateUserService;
import net.mad.ads.base.api.service.user.UserService;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.impl.local.h2.H2TrackingService;
import net.mad.ads.common.util.Properties2;
import net.mad.ads.manager.RuntimeContext;
import net.mad.ads.manager.utils.Constants;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author thorsten
 */
public class StartupPlugIn implements ServletContextListener {
	private static Logger logger = LoggerFactory.getLogger(StartupPlugIn.class);

	public void contextDestroyed(ServletContextEvent event) {
		logger.debug("stop application");
		
		RuntimeContext.getSessionFactory().close();

		try {
			RuntimeContext.getTrackingService().close();
		} catch (ServiceException e) {
			logger.error("", e);
		}
	}

	public void contextInitialized(ServletContextEvent event) {
		logger.info("Init Application");

		try {

			String enviroment = event.getServletContext().getInitParameter(
					"enviroment");

			String configDirectory = new File(".").getAbsolutePath(); // event.getServletContext().getInitParameter("configDirectory");
			
			
			if (System.getProperties().containsKey("mad.home")) {
				configDirectory = System.getProperty("mad.home");
			}
			
			if (!configDirectory.endsWith("/")) {
				configDirectory += "/";
			}
			
			System.setProperty("mad.home", configDirectory);
			
			
			
			configDirectory += "config/manager/";
			
			PropertyConfigurator.configure(Properties2.loadProperties(configDirectory + "log4j.properties"));
			RuntimeContext.setProperties(Properties2.loadProperties(configDirectory + "config.properties"));

			// hier können allgemeine Konfigurationen vorgenommen werden, die
			// erst
			// gemacht werden können, wenn die Anwendung läuft
			String path = event.getServletContext().getRealPath("/");

			
			File hibernateConfig = new File(configDirectory + "hibernate.cfg.xml");
			SessionFactory sessionFactory = new Configuration().configure(hibernateConfig).buildSessionFactory();
			RuntimeContext.setSessionFactory(sessionFactory);
			
			BaseContext context = new BaseContext();
			context.put(HibernateService.SESSION_FACTORY, sessionFactory);
			
			UserService users = new HibernateUserService();
			users.open(context);
			RuntimeContext.setUserService(users);
			
			SiteService sites = new HibernateSiteService();
			sites.open(context);
			RuntimeContext.setSiteService(sites);
			
			PlaceService places = new HibernatePlaceService();
			places.open(context);
			RuntimeContext.setPlaceService(places);
			
			ZoneService zones = new HibernateZoneService();
			zones.open(context);
			RuntimeContext.setZoneService(zones);
			
			User admin = users.get(1L);
			if (admin == null) {
				admin = new AdminUser();
				admin.setActive(true);
				admin.setCreated(new Date());
				admin.setUsername("admin");
				admin.setPassword("admin");
				
				users.create(admin);
			}

		} catch (Exception ex) {
			logger.error("error init application", ex);
		}
	}
	
	public void initTracking (BaseContext context) {
		try {
			String classname = RuntimeContext.getProperties().getProperty(
					Constants.CONFIG.PROPERTIES.TRACKINGSERVICE_CLASS,
					"");
			TrackingService trackService = (TrackingService) Class.forName(
					classname).newInstance();
			
			if (trackService instanceof H2TrackingService) {
				Context ctx = new InitialContext();
				ctx = (Context) ctx.lookup("java:comp/env");
				DataSource ds = (DataSource) ctx.lookup("jdbc/trackingds");

				context.put(EmbeddedBaseContext.EMBEDDED_TRACKING_DATASOURCE, ds);
			}
			
			trackService.open(context);

			RuntimeContext.setTrackingService(trackService);

		} catch (NamingException se) {
			logger.error("", se);
		} catch (ClassCastException cce) {
			logger.error("", cce);
		} catch (ServiceException e) {
			logger.error("", e);
		} catch (InstantiationException e) {
			logger.error("", e);
		} catch (IllegalAccessException e) {
			logger.error("", e);
		} catch (ClassNotFoundException e) {
			logger.error("", e);
		}
	}

}
