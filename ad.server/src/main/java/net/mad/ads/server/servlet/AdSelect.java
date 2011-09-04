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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import net.mad.ads.base.api.track.events.ClickTrackEvent;
import net.mad.ads.base.api.track.events.ImpressionTrackEvent;
import net.mad.ads.base.api.track.events.TrackEvent;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.server.utils.context.AdContext;
import net.mad.ads.server.utils.filter.ClickExpirationFilter;
import net.mad.ads.server.utils.filter.DuplicatBannerFilter;
import net.mad.ads.server.utils.filter.ViewExpirationFilter;
import net.mad.ads.server.utils.helper.TrackingHelper;
import net.mad.ads.server.utils.http.listener.AdContextListener;
import net.mad.ads.server.utils.renderer.impl.ExternBannerDefinitionRenderer;
import net.mad.ads.server.utils.renderer.impl.FlashBannerDefinitionRenderer;
import net.mad.ads.server.utils.renderer.impl.ExpandableImageBannerDefinitionRenderer;
import net.mad.ads.server.utils.renderer.impl.ImageBannerDefinitionRenderer;
import net.mad.ads.server.utils.request.RequestHelper;
import net.mad.ads.server.utils.selection.BannerProvider;
import net.mad.ads.server.utils.selection.BannerSelector;
import net.mad.ads.server.utils.selection.impl.RandomSingleBannerSelector;

/**
 * Servlet implementation class AdSelect
 */
public class AdSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/javascript;charset=UTF-8");
		
		
		
		
		try {
			
			AdContext context = AdContextListener.ADCONTEXT.get();
			// Type
//			String type = (String)request.getParameter(RequestHelper.type);
//			if (type == null || type.equals("")) {
//				type = "1";
//			}
			/*
			 * Aus den restlichen Bannern eins auswählen
			 * 
			 * Aktuell wird dies zufällig gemacht!
			 */
			BannerDefinition banner = BannerProvider.getInstance().getBanner(context, request);
			
			StringBuilder sb = new StringBuilder();
			if (banner != null) {
				/*
				 * Hier wird der Type des Banners verwendet und nicht der Typ der im
				 * Request Übergeben wird, da bei der Auswahl des Banners evtl. ein
				 * Fallback auf einen anderen BannerType erfolgen kann.
				 * z.B. Flashbanner auf Imagebanner
				 *  
				 */
				if (banner.getType().equals(BannerType.EXTERN)) {
					sb.append(ExternBannerDefinitionRenderer.getInstance().render(banner, request));
				} else if (banner.getType().equals(BannerType.IMAGE)) {
					sb.append(ImageBannerDefinitionRenderer.getInstance().render(banner, request));
				} else if (banner.getType().equals(BannerType.FLASH)) {
					sb.append(FlashBannerDefinitionRenderer.getInstance().render(banner, request));
				} else if (banner.getType().equals(BannerType.EXPANDABLEIMAGE)) {
					sb.append(ExpandableImageBannerDefinitionRenderer.getInstance().render(banner, request));
				}
				
				TrackEvent trackEvent = new ImpressionTrackEvent();
				trackEvent.setBannerId(banner.getId());
				trackEvent.setUser(context.getUserid());
				trackEvent.setId(UUID.randomUUID().toString());
				trackEvent.setTime(System.currentTimeMillis());
				
				TrackingHelper.trackEvent(context, trackEvent);
				
				TrackingHelper.trackImpression(context, trackEvent);
				
				/*
				 * Hier merken wir uns das Banner für diesen Request um später
				 * im DuplicateBannerFilter die Information verwenden zu können
				 * 
				 * Als Request gelten alle Aufrufe, die durch den selben Pageview erzeugt werden
				 */
				RuntimeContext.getRequestBanners().put("pv" + context.getRequestid() + "_" + banner.getId(), Boolean.TRUE);
				/*
				 * Hier merken wir uns, dass ein Benutzer das Banner schon gesehen hat.
				 * Auf diese Art kann später z.B. geregelt werden, dass ein USER ein Banner maximal 5 mal sehen soll
				 * 
				 * TODO: hier muss noch die TimeToLife für das Cacheobjekte gesetzt werden
				 */
				RuntimeContext.getRequestBanners().put("u" + context.getUserid() + "_" + banner.getId(), Boolean.TRUE);
				
				/*
				 * Damit wir später die passenden Banner für die Produkte anzeigen können, merken wir uns auch das Produkt
				 */
				if (banner.isProduct()) {
					RuntimeContext.getRequestBanners().put("prod" + context.getRequestid() + "_" + banner.getProduct(), Boolean.TRUE);
				}
			}
			
			
			
			response.getWriter().write(sb.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
