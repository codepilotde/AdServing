package net.mad.ads.server.utils.request;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.bcel.internal.generic.NEW;

import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.enums.Day;
import net.mad.ads.db.utils.geo.GeoLocation;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.server.utils.context.AdContext;
import net.mad.ads.server.utils.http.KeywordUtils;
import net.mad.ads.server.utils.renderer.impl.ImageBannerDefinitionRenderer;
import de.marx.services.geo.Location;

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
