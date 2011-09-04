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
package net.mad.ads.server.utils.listener.configuration.development;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;

import net.mad.ads.base.api.EmbeddedBaseContext;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.impl.local.LocalTrackService;
import net.mad.ads.base.api.track.impl.local.bdb.BDBTrackingService;
import net.mad.ads.server.utils.AdServerConstants;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.services.geo.IPLocationDB;
import net.mad.ads.services.geo.MaxmindIpLocationDB;


public class DevelopmentModule extends AbstractModule {

	private static final Logger logger = LoggerFactory.getLogger(DevelopmentModule.class);
	
	@Override
	protected void configure() {
		
		try {
			EmbeddedBaseContext baseContext = new EmbeddedBaseContext();
			baseContext.put(EmbeddedBaseContext.EMBEDDED_DB_DIR, RuntimeContext.getProperties().getProperty(AdServerConstants.CONFIG.PROPERTIES.TRACK_DIR));
			
			TrackingService trackService = new BDBTrackingService();
			trackService.open(baseContext);
			
//			bind(TrackingService.class).toInstance(new LocalTrackService());
			
			bind(TrackingService.class).toInstance(trackService);
			bind(IPLocationDB.class).toInstance(new MaxmindIpLocationDB());
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
