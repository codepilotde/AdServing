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
package net.mad.ads.server.utils.helper;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.track.events.TrackEvent;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.server.utils.context.AdContext;

public class TrackingHelper {
	public static final Logger logger = LoggerFactory.getLogger(TrackingHelper.class);
	
	public static void trackEvent (AdContext context, TrackEvent event) {
		try {
			RuntimeContext.getTrackService().track(event);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public static void trackImpression (AdContext context, TrackEvent event) {
		try {
			JSONObject json = new JSONObject();
			json.put("id", event.getId());
			json.put("bannerid", event.getBannerId());
			json.put("time", event.getTime());
			json.put("type", event.getType().getName());
			json.put("user", event.getUser());
			RuntimeContext.impressionLogger.impression(json.toJSONString());
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public static void trackClick (AdContext context, TrackEvent event) {
		try {
			
			JSONObject json = new JSONObject();
			json.put("id", event.getId());
			json.put("bannerid", event.getBannerId());
			json.put("time", event.getTime());
			json.put("type", event.getType().getName());
			json.put("user", event.getUser());
			
			RuntimeContext.clickLogger.click(json.toJSONString());
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
