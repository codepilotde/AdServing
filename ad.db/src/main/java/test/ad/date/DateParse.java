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
