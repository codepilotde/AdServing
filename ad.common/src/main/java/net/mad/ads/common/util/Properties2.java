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
package net.mad.ads.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.antlr.stringtemplate.*;
import org.antlr.stringtemplate.language.*;

/**
 * Helper for loading property-files
 * 
 * System.properties will be available to use in the files like this
 * 
 * ${user.dir}
 * 
 * @author thmarx
 * 
 */
public class Properties2 {

	private static final Logger logger = LoggerFactory
			.getLogger(Properties2.class);

	static public Properties loadProperties(URL propertiesFile)
			throws IOException {
		return loadProperties(propertiesFile.getFile());
	}

	static public Properties loadProperties(String propertiesFile)
			throws IOException {
		return loadProperties(new File(propertiesFile));
	}

	static public Properties loadProperties (File propertiesFile) throws IOException {
		InputStream is = null;
		is = new FileInputStream(propertiesFile);
		Properties prop = new Properties();
		prop.load(is);
		
		return new XProperties(prop);
	}
}
