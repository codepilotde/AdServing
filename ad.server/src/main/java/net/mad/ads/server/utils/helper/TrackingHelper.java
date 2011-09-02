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
			RuntimeContext.impLogger.impression(json.toJSONString());
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
