package net.mad.ads.services.geo.sample;

import net.mad.ads.services.geo.IpinfoLocationDB;



public class Import {
	public static void main(String[] args) throws Exception {

		IpinfoLocationDB readerhsql = new IpinfoLocationDB();
		readerhsql.open("data/geo/db/ipinfo");

		long before = System.currentTimeMillis();
		System.out.println("start import ipinfodb");
		readerhsql.importCountry("data/geo/imp/IP2LOCATION-LITE-DB11.CSV");
		long after = System.currentTimeMillis();

		System.out.println("end import after: " + (after - before) + "ms");

		readerhsql.close();
	}
}
