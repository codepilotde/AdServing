package net.mad.ads.services.geo.lucene.tools;

import net.mad.ads.services.geo.lucene.GeoIpIndex;



public class Import {
	public static void main(String[] args) throws Exception {

		GeoIpIndex ciIndex = new GeoIpIndex("data/geo/index/");
		

		long before = System.currentTimeMillis();
		System.out.println("start import cityIndex");
		ciIndex.importIPs("data/geo/imp/");
		long after = System.currentTimeMillis();

		System.out.println("end import after: " + (after - before) + "ms");
	}
}
