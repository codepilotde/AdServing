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
package net.mad.ads.base.api.utils.logging.handler;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

/**
 * File handler that supports different kind of rolling than
 * java.util.logging.FileHandler. Supported rolling methods are: by date (day).
 * 
 * Example of entries in the logging file (system property
 * "java.util.logging.config.file"):
 * 
 * <table align="center" bgcolor="#ddddff" border=1 cellpadding="10" cellspacing="0">
 * <tr>
 * <td>
 * 
 * <pre>
 * logging.RollingFileHandler.level = FINEST
 * logging.RollingFileHandler.prefix = MyApp_
 * logging.RollingFileHandler.dateFormat = yyyyMMdd
 * logging.RollingFileHandler.suffix = .log
 * logging.RollingFileHanlder.cycle=day
 * logging.RollingFileHandler.formatter = java.util.logging.SimpleFormatter
 * </pre>
 * 
 * </td>
 * </tr>
 * </table>
 * 
 * @version $Revision:$ ($Date:$)
 * @author $Author:$
 */
public class RollingFileHandler extends StreamHandler {
	/** File prefix. */
	private static String prefix = null;
	/** Date format to use in file name. */
	private static String dateFormat = "yyyy-MM-dd"; // default
	/** File suffix. */
	private static String suffix = null;
	/** Time in milliseconds for the next cycle */
	private static long nextCycle = 0;
	/** Time cycle (for file roling) */
	private static String cycle = "day"; // default

	
	public RollingFileHandler(String prefix, String suffix) {
		this.suffix = suffix;
		this.prefix = prefix;
		
		openFile();
	}
	/**
	 * Constructor.
	 */
	public RollingFileHandler() {
		super();
		LogManager manager = LogManager.getLogManager();
		String className = RollingFileHandler.class.getName();
		prefix = manager.getProperty(className + ".prefix");
		String dfs = manager.getProperty(className + ".dateFormat");
		suffix = manager.getProperty(className + ".suffix");
		String c = manager.getProperty(className + ".cycle");
		String formatter = manager.getProperty(className + ".formatter");
		if (dfs != null) {
			dateFormat = dfs;
		}
		if (c != null) {
			if (c.equalsIgnoreCase("day") || c.equalsIgnoreCase("week")
					|| c.equalsIgnoreCase("month")
					|| c.equalsIgnoreCase("year")) {
				cycle = c;
			}
		}
		if (formatter != null) {
			try {
				setFormatter((Formatter) Class.forName(formatter).newInstance());
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		openFile();
	}// RollingFileHandler()

	/**
	 * Open existing or create new log file.
	 */
	private synchronized void openFile() {
		// create file name:
		String dateString = dateFormat; // default (to note error in file name)
		Date currentDate = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat,
					Locale.getDefault());
			dateString = sdf.format(currentDate);
		} catch (IllegalArgumentException iae) {
			/* ignore wrong date format */
		}
		// compute next cycle:
		Date nextDate = null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(currentDate);
		if (cycle.equalsIgnoreCase("week")) {
			gc.add(Calendar.WEEK_OF_YEAR, 1);
			nextDate = gc.getTime();
		} else if (cycle.equalsIgnoreCase("month")) {
			gc.add(Calendar.MONTH, 1);
			int month = gc.get(Calendar.MONTH);
			int year = gc.get(Calendar.YEAR);
			GregorianCalendar gc2 = new GregorianCalendar(year, month, 1);
			nextDate = gc2.getTime();
		} else if (cycle.equalsIgnoreCase("year")) {
			gc.add(Calendar.YEAR, 1);
			int year = gc.get(Calendar.YEAR);
			GregorianCalendar gc2 = new GregorianCalendar(year, 0, 1);
			nextDate = gc2.getTime();
		} else { // day by default
			gc.add(Calendar.DAY_OF_MONTH, 1);
			nextDate = gc.getTime();
		}
		// to zero time:
		gc = new GregorianCalendar();
		gc.setTime(nextDate);
		gc.set(Calendar.HOUR, 0);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);
		nextDate = gc.getTime();
		nextCycle = nextDate.getTime();
		// create new file:
		String fileName = prefix + dateString + suffix;
		File file = new File(fileName);
		// create file:
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException ioe) {
				ioe.printStackTrace(System.err);
			}
		}
		// set log file as OutputStream:
		try {
			FileOutputStream fos = new FileOutputStream(file, true);
			setOutputStream(fos);
		} catch (FileNotFoundException fnfe) {
			reportError(null, fnfe, ErrorManager.OPEN_FAILURE);
			fnfe.printStackTrace(System.err);
			setOutputStream(System.out); // fallback stream
		}
	}// openFile()

	/**
	 * Overwrites super.
	 */
	public synchronized void publish(LogRecord record) {
		if (!isLoggable(record)) {
			return;
		}
		super.publish(record);
		flush();
		// check if we need to rotate
		if (System.currentTimeMillis() >= nextCycle) { // next cycle?
			role();
		}
	}// publish()

	/**
	 * Role file. Close current file and possibly create new file.
	 */
	final private synchronized void role() {
		Level oldLevel = getLevel();
		setLevel(Level.OFF);
		super.close();
		openFile();
		setLevel(oldLevel);
	}// rotate()
}// RollingFileHandler