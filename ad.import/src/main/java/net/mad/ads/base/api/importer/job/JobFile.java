package net.mad.ads.base.api.importer.job;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author thmarx
 *
 */
public class JobFile {
	private List<Job> jobs = new ArrayList<Job>();
	
	private List<Job> errorJobs = new ArrayList<Job>();
	
	private String filename = null;
	
	public JobFile () {
		
	}
	
	public List<Job> getJobs () {
		return this.jobs;
	}

	public List<Job> getErrorJobs() {
		return errorJobs;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
