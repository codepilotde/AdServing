package net.mad.ads.base.api.importer.job.jobs;

import net.mad.ads.base.api.importer.job.Job;

/**
 * Job zum löschen eines Banners im AdServer
 * 
 * @author thmarx
 *
 */
public class DeleteJob extends Job {

	/*
	 * Die ID des Banners das gelöscht werden soll
	 */
	private String id;
	
	public DeleteJob() {
		super(Job.Type.Delete);
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
