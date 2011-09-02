package net.mad.ads.base.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
	public static final SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.GERMANY);
	
	public static final String format (Date date) {
		return dformat.format(date);
	}
	
	public static Date parse (String date) throws ParseException {
		return dformat.parse(date);
	}
}
