package net.mad.ads.services.geo.sample;

import net.mad.ads.services.geo.IpinfoLocationDB;
import net.mad.ads.services.geo.Location;




public class Sample {
	
public static void main(String[] args) throws Exception {
		
		IpinfoLocationDB readerhsql = new IpinfoLocationDB();
		readerhsql.open("data/geo/db/ipinfo");
		
		long before = System.currentTimeMillis();
		Location loc = readerhsql.searchIp("66.211.160.129");
		System.out.println("1: " + loc.getCountry() + " - " + loc.getRegionName() + " - " + loc.getCity());
		long after = System.currentTimeMillis();
		System.out.println((after - before) + "ms");
		
		before = System.currentTimeMillis();
		loc = readerhsql.searchIp("213.83.37.145");
		System.out.println("2: " + loc.getCountry() + " - " + loc.getRegionName() + " - " + loc.getCity());
		after = System.currentTimeMillis();
		System.out.println((after - before) + "ms");
		
		before = System.currentTimeMillis();
		loc = readerhsql.searchIp("88.153.215.174");
		System.out.println("3: " + loc.getCountry() + " - " + loc.getRegionName() + " - " + loc.getCity());
		after = System.currentTimeMillis();
		System.out.println((after - before) + "ms");
		
		
		int c = 10;
		int comp = 0;
		for (int i = 0; i < c; i++) {
			before = System.currentTimeMillis();
			loc = readerhsql.searchIp("213.83.37.145");
//			System.out.println(loc.getCountry() + " - " + loc.getRegionName() + " - " + loc.getCity());
			after = System.currentTimeMillis();
			long dur = (after - before);
//			System.out.println(dur + "ms");
			comp += dur;
		}
		System.out.println("durchschnitt: " + (comp));
		System.out.println("durchschnitt: " + (comp / c));
		
		readerhsql.close();
	}
}
