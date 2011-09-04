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
package net.mad.ads.base.api.track.impl.local.helper.test;



import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;


import net.mad.ads.base.api.track.impl.local.helper.TrackKey;
import net.mad.ads.base.api.track.impl.local.helper.TrackTree;
import net.mad.ads.base.api.track.impl.local.helper.UUIDHelper;



public class TrackTreeTest {
	public static void main (String [] args) throws Exception {
		TrackTree<String> tree = new TrackTree<String>(10);
		
		TrackKey key = new TrackKey(java.util.UUID.randomUUID().toString(), 1l);
		tree.put(key, "eins"); // alt
		key = new TrackKey(java.util.UUID.randomUUID().toString(), 2l);
		tree.put(key, "zwei");
		key = new TrackKey(java.util.UUID.randomUUID().toString(), 3l);
		tree.put(key, "drei");
		key = new TrackKey(java.util.UUID.randomUUID().toString(), 4l);
		tree.put(key, "vier");
		TrackKey key5 = new TrackKey(java.util.UUID.randomUUID().toString(), 5l);
		tree.put(key5, "fünf");
		key = new TrackKey(java.util.UUID.randomUUID().toString(), 6l);
		tree.put(key, "sechs");
		key = new TrackKey(java.util.UUID.randomUUID().toString(), 7l);
		tree.put(key, "sieben");
		key = new TrackKey(java.util.UUID.randomUUID().toString(), 8l);
		tree.put(key, "acht");
		TrackKey key9 = new TrackKey(java.util.UUID.randomUUID().toString(), 9l);
		tree.put(key9, "neun");
		key = new TrackKey(java.util.UUID.randomUUID().toString(), 10l);
		tree.put(key, "zehn"); // neu
		
		
		int count = tree.count(key5, key9);
		System.out.println(count);
		count = tree.count(key5, key9);
		System.out.println(count);
		
		
		List<String> result = tree.list(key5, key9);
		printResult(result);
		
	}
	
	private static void printResult (List<String> result) {
		for (String st : result) {
			System.out.println(st);
		}
	}
}
