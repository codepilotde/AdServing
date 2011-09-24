package net.mad.ads.manager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String format (Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		
		return df.format(date);
	}
}
