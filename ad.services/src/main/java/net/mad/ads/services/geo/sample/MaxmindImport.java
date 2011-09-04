package net.mad.ads.services.geo.sample;

import net.mad.ads.services.geo.IPLocationDB;
import net.mad.ads.services.geo.MaxmindIpLocationDB;




public class MaxmindImport {
	public static void main(String[] args) throws Exception {

		IPLocationDB readerhsql = new MaxmindIpLocationDB();
		readerhsql.open("data/geo/maxmind/ipinfo");

		long before = System.currentTimeMillis();
		System.out.println("start import maxmind");
		readerhsql.importCountry("data/geo/imp/");
		long after = System.currentTimeMillis();

		System.out.println("end import after: " + (after - before) + "ms");

		readerhsql.close();
	}
}
