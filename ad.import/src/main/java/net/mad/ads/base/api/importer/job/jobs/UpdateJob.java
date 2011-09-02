package net.mad.ads.base.api.importer.job.jobs;

import net.mad.ads.base.api.importer.job.Job;
import net.mad.ads.base.api.importer.job.Job.Type;

/**
 * Update eines Banners
 * 
 * @author thmarx
 *
 */
public class UpdateJob extends Job {

	private String bannerDefinition = null;
	
	public UpdateJob() {
		super(Job.Type.Update);
	}

	public String getBannerDefinition() {
		return bannerDefinition;
	}

	public void setBannerDefinition(String bannerDefinition) {
		this.bannerDefinition = bannerDefinition;
	}

	
}
