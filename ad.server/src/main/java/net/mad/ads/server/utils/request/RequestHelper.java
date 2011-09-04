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
package net.mad.ads.server.utils.request;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.enums.Day;
import net.mad.ads.db.utils.geo.GeoLocation;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.server.utils.context.AdContext;
import net.mad.ads.server.utils.http.KeywordUtils;
import net.mad.ads.services.geo.Location;

/**
 * Helper zum erzeugen des AdRequest aus dem HttpRequest
 * 
 * @author tmarx
 * 
 */
public class RequestHelper {
	private static final Logger logger = LoggerFactory.getLogger(RequestHelper.class);

	public static final String format = "_p1";
	public static final String type = "_p2";
	public static final String offset = "_p3";
	public static final String requestId = "_p4";
	public static final String flash = "_p5";
	public static final String slot = "_p6";
	public static final String keywords = "_p7";
	public static final String referrer = "_p8";
	
//	public static final String hour = "_p3";
//	public static final String minute = "_p4";
//	public static final String day = "_p5";
//	public static final String date_day = "_p6";
//	public static final String date_month = "_p7";
//	public static final String date_year = "_p8";

	private static final Pattern integerPattern = Pattern.compile("^\\d+$");

	public static AdRequest getAdRequest(AdContext context, HttpServletRequest request) {
		AdRequest adRequest = new AdRequest();

		// TODO: Uhrzeit auf länge und Integer prüfen
		// Matcher matchesInteger = integerPattern.matcher (temp);
		// boolean isInteger = matchesInteger.matches ();

		try {
			String clientIp = request.getRemoteAddr();
			Location loc = RuntimeContext.getIpDB().searchIp(clientIp);
			if (loc != null) {
				try {
					GeoLocation geo = new GeoLocation(Double.parseDouble(loc.getLatitude()), Double.parseDouble(loc.getLongitude()));
					
					adRequest.setGeoLocation(geo);
				} catch (NumberFormatException nfe) {
//					logger.error("", nfe);
				}
			}

			// Format
			String format = request.getParameter(RequestHelper.format);
			// Type
			String type = (String) request.getParameter(RequestHelper.type);

			adRequest.getFormats().add(BannerFormat.fromCompoundName(format));
			adRequest.getTypes().add(BannerType.forType(Integer.valueOf(type)));
			
			adRequest.setKeywords(KeywordUtils.getKeywords(request));
			
			if (context.getSlot() != null) {
				adRequest.setSite(context.getSlot().getSite());
				adRequest.setAdSlot(context.getSlot().toString());
			}

			addTimeCondition(request, adRequest);
		} catch (Exception e) {
			logger.error("", e);
		}

		return adRequest;
	}

	/*
	 * Durch das übergebene Offset des Browsers können hier die Tageszeit und 
	 * das Datum gesetzt werden
	 */
	private static void addTimeCondition(HttpServletRequest request,
			AdRequest adRequest) {
		String strOffSet = request.getParameter(RequestHelper.offset);
		
		int offset = Integer.parseInt(strOffSet);
		offset = offset * 60000;
		String [] ids = TimeZone.getAvailableIDs(offset);
		
		Calendar temp = Calendar.getInstance();
		temp.setTimeZone(TimeZone.getTimeZone(ids[0]));
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat timeFormat = new SimpleDateFormat("HHmm");
		
		// aktuelle Uhrzeit setzen
		adRequest.setTime(timeFormat.format(temp.getTime()));
		// aktuelles Datum setzen
		adRequest.setDate(dateFormat.format(temp.getTime()));
		// Tag der Woche setzten
		adRequest.setDay(Day.getDayForJava(temp.get(Calendar.DAY_OF_WEEK)));
	}
}
