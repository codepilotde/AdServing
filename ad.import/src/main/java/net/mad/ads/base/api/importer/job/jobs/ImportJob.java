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
