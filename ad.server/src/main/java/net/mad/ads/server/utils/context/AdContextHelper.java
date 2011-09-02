package net.mad.ads.server.utils.context;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.db.definition.AdSlot;
import net.mad.ads.server.utils.AdServerConstants;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.server.utils.http.CookieUtils;
import net.mad.ads.server.utils.request.RequestHelper;
import de.marx.common.tools.Strings;

public class AdContextHelper {

	private static final Logger logger = LoggerFactory.getLogger(AdContextHelper.class);
	
	public static AdContext getAdContext (HttpServletRequest request, HttpServletResponse response) {
		AdContext context = new AdContext();
		
		String userID = null;
		Cookie cookie = CookieUtils.getCookie(request.getCookies(), AdServerConstants.Cookie.USERID);
		if (cookie != null) {
			userID = cookie.getValue();
		}
		if (Strings.isEmpty(userID)) {
			userID = UUID.randomUUID().toString();
			CookieUtils.addCookie(response, AdServerConstants.Cookie.USERID, userID, CookieUtils.ONE_YEAR, RuntimeContext.getProperties().getProperty(AdServerConstants.CONFIG.PROPERTIES.COOKIE_DOMAIN));
		}
		context.setUserid(userID);
		
		String requestID = (String)request.getParameter(RequestHelper.requestId);
		if (Strings.isEmpty(requestID)) {
			requestID = UUID.randomUUID().toString();
		}
		context.setRequestid(requestID);
		
		String slot = (String)request.getParameter(RequestHelper.slot);
		if (!Strings.isEmpty(slot)) {
			try {
				AdSlot aduuid = AdSlot.fromString(slot);
				context.setSlot(aduuid);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		
		
		
		return context;
	}
}
