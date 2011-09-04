/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
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
package net.mad.ads.base.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CompareTest {
	public static void main (String []args) throws Exception {
		List<Float> fl = new ArrayList<Float>();
		fl.add(0.1f);
		fl.add(0.0f);
		fl.add(0.0f);
		fl.add(0.2f);
		fl.add(-1f);
		
		System.out.println(fl);
		
		Collections.sort(fl, new Comparator<Float>() {

			@Override
			public int compare(Float o1, Float o2) {
				if (o1 > o2) {
					return -1;
				} else if (o2 > o1) {
					return 1;
				}
				return 0;
			}
		});
		
		System.out.println(fl);
		Collections.reverse(fl);
		System.out.println(fl);
		
		float sma = -2f;
		for (Float f : fl) {
			if (f == -1f) {
				System.out.print(f);
			} else if (sma == -2f) {
				System.out.print(f);
				sma = f;
			} else if (f == sma) {
				System.out.print(f);
			} else if (f > sma) {
				break;
			}
			System.out.print(" ");
		}
	}
}
