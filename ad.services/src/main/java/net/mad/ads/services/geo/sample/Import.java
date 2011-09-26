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
