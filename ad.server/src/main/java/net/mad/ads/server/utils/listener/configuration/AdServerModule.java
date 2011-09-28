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
package net.mad.ads.server.utils.listener.configuration;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;

import net.mad.ads.base.api.EmbeddedBaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.impl.local.bdb.BDBTrackingService;
import net.mad.ads.base.api.track.impl.local.h2.H2TrackingService;
import net.mad.ads.common.util.Strings;
import net.mad.ads.server.utils.AdServerConstants;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.services.geo.IPLocationDB;
import net.mad.ads.services.geo.MaxmindIpLocationDB;

public class AdServerModule extends AbstractModule {

	private static final Logger logger = LoggerFactory
			.getLogger(AdServerModule.class);

	@Override
	protected void configure() {

		try {
			EmbeddedBaseContext baseContext = new EmbeddedBaseContext();
			baseContext.put(
					EmbeddedBaseContext.EMBEDDED_DB_DIR,
					RuntimeContext.getProperties().getProperty(
							AdServerConstants.CONFIG.PROPERTIES.TRACK_DIR));

			String classname = RuntimeContext
					.getProperties()
					.getProperty(
							AdServerConstants.CONFIG.PROPERTIES.IPLOCATIONSERVICE_CLASS,
							"");
			IPLocationDB iplocDB = (IPLocationDB) Class.forName(classname)
					.newInstance();

			bind(IPLocationDB.class).toInstance(iplocDB);

			initTracking(baseContext);

		} catch (ClassCastException cce) {
			logger.error("", cce);
		} catch (InstantiationException e) {
			logger.error("", e);
		} catch (IllegalAccessException e) {
			logger.error("", e);
		} catch (ClassNotFoundException e) {
			logger.error("", e);
		}
	}

	private void initTracking(EmbeddedBaseContext context) {

		try {
			String classname = RuntimeContext.getProperties().getProperty(
					AdServerConstants.CONFIG.PROPERTIES.TRACKINGSERVICE_CLASS,
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

			bind(TrackingService.class).toInstance(trackService);

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
