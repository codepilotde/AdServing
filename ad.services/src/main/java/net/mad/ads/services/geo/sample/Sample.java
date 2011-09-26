/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
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
