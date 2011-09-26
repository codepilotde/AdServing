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
package net.mad.ads.base.api.importer;


import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.*;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.mad.ads.base.api.importer.job.Job;
import net.mad.ads.base.api.importer.job.JobFile;
import net.mad.ads.base.api.importer.job.jobs.DeleteJob;
import net.mad.ads.base.api.importer.job.jobs.ImportJob;
import net.mad.ads.base.api.importer.job.jobs.UpdateJob;
import net.mad.ads.base.api.importer.reader.AdXmlReader;
import net.mad.ads.common.util.Strings;
import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.definition.BannerDefinition;


public class Importer {
	
	private static final Logger logger = LoggerFactory.getLogger(Importer.class);
	
	public static final FilenameFilter JOBFILE_FILTER = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			if (name.toLowerCase().endsWith(".json")) {
				return true;
			}
			return false;
		}
	};
	
	public static GsonBuilder GSON_BUILDER = new GsonBuilder();
	static {
		GSON_BUILDER.registerTypeAdapter(JsonElement.class,
				new JsonDeserializer<JsonElement>() {

					@Override
					public JsonElement deserialize(JsonElement element, Type type,
							JsonDeserializationContext context)
							throws JsonParseException {
						return element;
					}

				});
		GSON_BUILDER.setPrettyPrinting();
	}
	
	private String basePath = null;
	private AdDB addb = null;
	private boolean running = false;
	public Importer (String basePath, AdDB addb) {
		this.basePath = basePath;
		this.addb = addb;
	}
	
	/**
	 * 1. Check auf neue JobFiles
	 * 2. Laden der JobFiles
	 * 
	 * @return true, wenn der Import geklappt hat, ansonsten false
	 */
	public boolean runImport () {
		if (running) {
			logger.info("importer already running");
			return true;
		}
		running = true;
		boolean result = true;
		
		try {
			File jobdir = new File(this.basePath + "/import");
			File[] jobfiles = jobdir.listFiles(Importer.JOBFILE_FILTER);
			
			for (File jobfile : jobfiles) {
				/*
				 * Zuerst werden für eine JobFile die einzelnen Jobs eingesammelt
				 */
				JsonElement jobfileElem = getJsonObject(jobfile);
				JobFile jobFile = getJobFile(jobfileElem);
				jobFile.setFilename(jobfile.getName());
				
				if (jobFile.getJobs().isEmpty()) {
					moveFileToDirectory(jobfile, "error");
				}
				
				if (prozessJobs(jobFile)) {
					moveFileToDirectory(jobfile, "done");
				} else {
					moveFileToDirectory(jobfile, "error");
				}
				
			}
		} catch (Exception e) {
			// Fehler Behandlung
			
		} finally {
			running = false;
		}
		
		
		return result;
	}
	
	private boolean prozessJobs (JobFile jobFile) {
		boolean result = true;
		
		for (Job job : jobFile.getJobs()) {
			try {
				if (job.getType().equals(Job.Type.Delete)) {
					addb.deleteBanner(((DeleteJob)job).getId());
				} else if (job.getType().equals(Job.Type.Import)) {
					String bannerdef = ((ImportJob)job).getBannerDefinition();
					BannerDefinition banner = AdXmlReader.readBannerDefinition(this.basePath + "/import/" + bannerdef);
					
					addb.addBanner(banner);
				} else if (job.getType().equals(Job.Type.Update)) {
					String bannerdef = ((UpdateJob)job).getBannerDefinition();
					BannerDefinition banner = AdXmlReader.readBannerDefinition(this.basePath + "/import/" + bannerdef);
					
					addb.deleteBanner(banner.getId());
					addb.addBanner(banner);
				}
			} catch (Exception e) {
				logger.error("error", e);
				jobFile.getErrorJobs().add(job);
			}
		}
		String job_errors = "";
		for (Job job : jobFile.getErrorJobs()) {
			job_errors += "error " + job.getNum() + "\r\n";
			
			if (job.getType().equals(Job.Type.Import)) {
				String bannerdef = ((ImportJob)job).getBannerDefinition();
				moveFileToDirectory(new File(this.basePath + "/import/" + bannerdef), "error");
			} else if (job.getType().equals(Job.Type.Update)) {
				String bannerdef = ((UpdateJob)job).getBannerDefinition();
				moveFileToDirectory(new File(this.basePath + "/import/" + bannerdef), "error");
			}
		}
		for (Job job : jobFile.getJobs()) {
			if (!jobFile.getErrorJobs().contains(job)) {
				if (job.getType().equals(Job.Type.Import)) {
					String bannerdef = ((ImportJob)job).getBannerDefinition();
					moveFileToDirectory(new File(this.basePath + "/import/" + bannerdef), "done");
				} else if (job.getType().equals(Job.Type.Update)) {
					String bannerdef = ((UpdateJob)job).getBannerDefinition();
					moveFileToDirectory(new File(this.basePath + "/import/" + bannerdef), "done");
				}
			}
		}
		if (!Strings.isEmpty(job_errors)) {
			try {
				String fn = this.basePath + "/error/" + jobFile.getFilename() + ".error";
				FileUtils.write(new File(fn), job_errors, "UTF-8");
				
				
				String from = this.basePath + "/import/" + jobFile.getFilename();
				String to = this.basePath + "/error/" + jobFile.getFilename();
				FileUtils.copyFile(new File(from), new File(to));
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		
		return result;
	}
	
	private JobFile getJobFile (JsonElement jobfileElem) {
		JobFile jobList = new JobFile();
		
		JsonElement jobs = jobfileElem.getAsJsonObject().get("jobs");
		if (jobs.isJsonArray()) {
			JsonArray jobsArray = (JsonArray)jobs;
			for (JsonElement job : jobsArray) {
				JsonObject jobObj = (JsonObject)job;
				
				Job theJob = getJob(jobObj);
				if (theJob != null) {
					jobList.getJobs().add(theJob);
				}
			}
		}
		
		return jobList;
	}
	
	private Job getJob (JsonObject json) {
		Job job = null;
		
		try {
			String type = json.get("type").getAsString();
			if (type.equalsIgnoreCase(Job.Type.Delete.name())) {
				job = new DeleteJob();
				((DeleteJob)job).setId(json.get("id").getAsString());
			} else if (type.equalsIgnoreCase(Job.Type.Import.name())) {
				job = new ImportJob();
				((ImportJob)job).setBannerDefinition(json.get("bannerdefinition").getAsString());
			} else if (type.equalsIgnoreCase(Job.Type.Update.name())) {
				job = new UpdateJob();
				((UpdateJob)job).setBannerDefinition(json.get("bannerdefinition").getAsString());
			}
			
			int num = json.get("num").getAsInt();
			job.setNum(num);
		} catch (Exception e) {
			logger.error("getting job from json", e);
		}
		
		return job;
	}
	
	private JsonElement getJsonObject(File jobFile) {
		
		try {
			String jsonContent = FileUtils.readFileToString(jobFile, "UTF-8");
			
			Gson gson = GSON_BUILDER.create();
			JsonElement element = gson.fromJson(jsonContent, JsonElement.class);

			return element;
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return null;
	}
	
	private void moveFileToDirectory (File file, String directory) {
		try {
			FileUtils.moveFileToDirectory(file, new File(this.basePath, directory), true);
		} catch (Exception e) {
			logger.error("moving file to directory " + directory, e);
		}
	}
}
