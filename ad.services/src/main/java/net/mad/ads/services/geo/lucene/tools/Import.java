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
