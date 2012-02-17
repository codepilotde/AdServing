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
package net.mad.ads.services.geo.ant;

import net.mad.ads.services.geo.IPLocationDB;
import net.mad.ads.services.geo.MaxmindIpLocationDB;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class GeoIpImportTask extends Task {
	
	private String importDir;
	private String exportDir;
	
	
	@Override
	public void execute() throws BuildException {
		try {
			IPLocationDB readerhsql = new MaxmindIpLocationDB();
			readerhsql.open(exportDir);

			long before = System.currentTimeMillis();
			System.out.println("start import maxmind");
			readerhsql.importCountry(importDir);
			long after = System.currentTimeMillis();

			System.out.println("end import after: " + (after - before) + "ms");

			readerhsql.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new BuildException(e);
		}
	}
	/**
	 * @param importDir the importDir to set
	 */
	public void setImportDir(String importDir) {
		this.importDir = importDir;
	}


	/**
	 * @param exportDir the exportDir to set
	 */
	public void setExportDir(String exportDir) {
		this.exportDir = exportDir;
	}
	
	
	

}
