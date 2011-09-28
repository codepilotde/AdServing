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
package net.mad.ads.server.utils.context;


import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.common.util.Strings;
import net.mad.ads.db.definition.AdSlot;
import net.mad.ads.server.utils.AdServerConstants;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.server.utils.http.CookieUtils;
import net.mad.ads.server.utils.request.RequestHelper;


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

		// gets the ip
		String clientIP = request.getRemoteAddr();
		
		/*
		 * if we are behind a proxy or loadbalancer
		 * the the X-Real-ip header should be set
		 */
		if (request.getHeader("X-Real-IP") != null) {
			clientIP = request.getHeader("X-Real-IP");
		}
		context.setIp(clientIP);
		
		
		return context;
	}
}
