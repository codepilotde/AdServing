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
package net.mad.ads.base.api.importer.job.jobs;

import net.mad.ads.base.api.importer.job.Job;
import net.mad.ads.base.api.importer.job.Job.Type;

/**
 * Import eines neuen Banners
 * 
 * @author thmarx
 *
 */
public class ImportJob extends Job {

	/*
	 * Dateiname der BannerDefinition
	 */
	private String bannerDefinition = null;
	
	public ImportJob() {
		super(Job.Type.Import);
	}

	public String getBannerDefinition() {
		return bannerDefinition;
	}

	public void setBannerDefinition(String bannerDefinition) {
		this.bannerDefinition = bannerDefinition;
	}

	
}
