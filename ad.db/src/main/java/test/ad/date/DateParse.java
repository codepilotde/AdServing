package test.ad.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;

public class DateParse {
	public static void main (String [] args) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date d = df.parse("2010.06.25.08.36.33");
		
		System.out.println(DateTools.dateToString(d, Resolution.SECOND));
		System.out.println(DateTools.stringToDate(DateTools.dateToString(d, Resolution.SECOND)).toString());
		System.out.println(d.toString());
		System.out.println(new Date().toString());
	}
}
