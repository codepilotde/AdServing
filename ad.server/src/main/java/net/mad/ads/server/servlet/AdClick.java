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
package net.mad.ads.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.base.api.track.events.ClickTrackEvent;
import net.mad.ads.base.api.track.events.ImpressionTrackEvent;
import net.mad.ads.base.api.track.events.TrackEvent;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.server.utils.context.AdContext;
import net.mad.ads.server.utils.helper.TrackingHelper;
import net.mad.ads.server.utils.http.listener.AdContextListener;

/**
 * Servlet implementation class AdClick
 */
public class AdClick extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(AdClick.class);
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdClick() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String id = (String) request.getParameter("id");
		
		BannerDefinition banner = RuntimeContext.getAdDB().getBanner(id);
		
		try {
			AdContext context = AdContextListener.ADCONTEXT.get();
			TrackEvent trackEvent = new ClickTrackEvent();
			trackEvent.setBannerId(banner.getId());
			trackEvent.setUser(context.getUserid());
			trackEvent.setId(UUID.randomUUID().toString());
			trackEvent.setTime(System.currentTimeMillis());
			
//			RuntimeContext.getTrackService().track(trackEvent);
//			RuntimeContext.clickLogger.click(banner.getId(), context.getUserid(), ""+trackEvent.getTime());
			TrackingHelper.trackEvent(context, trackEvent);
			TrackingHelper.trackClick(context, trackEvent);
		} catch (Exception e) {
			logger.error("", e);
		}
		
		response.sendRedirect(banner.getTargetUrl());
	}
}
