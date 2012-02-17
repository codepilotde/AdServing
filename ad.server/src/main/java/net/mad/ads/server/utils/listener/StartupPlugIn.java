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
package net.mad.ads.server.utils.listener;




import java.io.File;

import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import org.apache.log4j.PropertyConfigurator;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import net.mad.ads.base.api.importer.Importer;
import net.mad.ads.base.api.importer.reader.*;
import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.utils.logging.LogWrapper;
import net.mad.ads.common.template.TemplateManager;
import net.mad.ads.common.template.impl.freemarker.FMTemplateManager;
import net.mad.ads.common.util.Properties2;
import net.mad.ads.common.util.Strings;
import net.mad.ads.common.util.XProperties;
import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.server.utils.AdServerConstants;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.server.utils.listener.configuration.AdServerModule;
import net.mad.ads.server.utils.runnable.AdDbUpdateTask;
import net.mad.ads.services.geo.IPLocationDB;


/**
 * 
 * @author thorsten
 */
public class StartupPlugIn implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(StartupPlugIn.class);
	
	private Timer timer = new Timer();
	
	private Injector injector = null;
	
	public void contextInitialized(ServletContextEvent event) {
		try {
			// Konfiguration einlesen
			String enviroment = event.getServletContext().getInitParameter("enviroment");
			
			String configDirectory = new File(".").getAbsolutePath(); // event.getServletContext().getInitParameter("configDirectory");
			
			
			if (System.getProperties().containsKey("mad.home")) {
				configDirectory = System.getProperty("mad.home");
			}
			
			if (!configDirectory.endsWith("/")) {
				configDirectory += "/";
			}
			
			System.setProperty("mad.home", configDirectory);
			
			
			
			configDirectory += "config/";
			
			
			// configure log4j
			
			PropertyConfigurator.configure(Properties2.loadProperties(configDirectory + "log4j.properties"));
			
			
			
			RuntimeContext.setEnviroment(enviroment);
			String path = event.getServletContext().getRealPath("/");
			RuntimeContext.setConfiguration(AdServerConstants.CONFIG.PATHES, AdServerConstants.PATHES.WEB, path);
//			RuntimeContext.getProperties().load(new FileReader(configDirectory + "config.properties"));
			RuntimeContext.setProperties(Properties2.loadProperties(configDirectory + "config.properties"));
			
			injector = Guice.createInjector(new AdServerModule());
			
			// Init event logging
			RuntimeContext.clickLogger = new LogWrapper();
			RuntimeContext.clickLogger.init(RuntimeContext.class, new File(configDirectory + "logger_clicks.properties"));
			RuntimeContext.impressionLogger = new LogWrapper();
			RuntimeContext.impressionLogger.init(RuntimeContext.class, new File(configDirectory + "logger_impression.properties"));
			
			// Banner-Datenbank initialisieren
			logger.info("init bannerDB");
			initBannerDB();
			// Ip-Datenbank initialisieren
			logger.info("init ipDB");
			initIpDB();
			logger.info("init trackService");
			intServices();
			
			logger.info("init banner templates");
			initBannerTemplates(path);
			
			logger.info("init templates");
			initTemplates(path + "/WEB-INF/content/templates/");
			
			timer.scheduleAtFixedRate(new AdDbUpdateTask(), AdDbUpdateTask.delay, AdDbUpdateTask.period);
			
			RuntimeContext.cacheManager = new DefaultCacheManager(configDirectory + "cluster/infinispan_config.xml");
			RuntimeContext.requestBanners = RuntimeContext.cacheManager.getCache("requestBanners");
			RuntimeContext.requestBanners.addListener(new CacheListener());
			
			RuntimeContext.setImporter(new Importer(RuntimeContext.getProperties().getProperty(AdServerConstants.CONFIG.PROPERTIES.BANNER_IMPORT_DIRECOTRY), RuntimeContext.getAdDB()));
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		try {
			RuntimeContext.getAdDB().close();
			RuntimeContext.getIpDB().close();
			RuntimeContext.getTrackService().close();
			RuntimeContext.cacheManager.stop();
			
			timer.cancel();
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	private void intServices () throws Exception {
		RuntimeContext.setTrackService(injector.getInstance(TrackingService.class));
	}
	
	private void initTemplates (String path) throws IOException {
		File tdir = new File(path);
		File[] templates = tdir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".ftl")) {
					return true;
				}
				return false;
			}
		});
		
		TemplateManager tempMan = new FMTemplateManager();
		tempMan.init(path);
		for (File template : templates) {
			String tname = Strings.removeExtension(template.getName()).toLowerCase();
			tempMan.registerTemplate(tname, template.getName());
		}
		
		RuntimeContext.setTemplateManager(tempMan);
	}
	
	private void initBannerTemplates (String path) throws IOException {
//		String templatePath = RuntimeContext.getProperties().getProperty(AdServerConstants.CONFIG.PROPERTIES.BANNER_TEMPLATE_DIR);
		String templatePath = path + "/WEB-INF/content/templates/banner";
		
		RuntimeContext.getBannerRenderer().init(templatePath);
		
		for (BannerType type : BannerType.values()) {
			RuntimeContext.getBannerRenderer().registerTemplate(type.getName().toLowerCase(), type.getName().toLowerCase()+".ftl");
		}
	}
	
	private void initIpDB () throws Exception {
		
		long before = System.currentTimeMillis();
		
		IPLocationDB db = injector.getInstance(IPLocationDB.class);
		db.open(RuntimeContext.getProperties().getProperty(AdServerConstants.CONFIG.PROPERTIES.IPDB_DIR));
		RuntimeContext.setIpDB(db);
		
		RuntimeContext.getIpDB().searchIp("213.83.37.145");
		long after = System.currentTimeMillis();
		logger.debug("finish ipDB: " + (after - before) + "ms");
	}
	
	private void initBannerDB () throws Exception {
		
		long before = System.currentTimeMillis();
		
		RuntimeContext.setAdDB(new AdDB());
		RuntimeContext.getAdDB().open();
		String bannerPath = RuntimeContext.getProperties().getProperty(AdServerConstants.CONFIG.PROPERTIES.BANNER_DATA_DIRECOTRY);
		
		File bdir = new File(bannerPath);
		if (bdir.exists() && bdir.isDirectory()) {
			String[] banners = bdir.list(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith(".xml")) {
						return true;
					}
					return false;
				}
			});
			
			for (String banner : banners) {
				BannerDefinition b = AdXmlReader.readBannerDefinition(bannerPath + File.separator + banner);
				RuntimeContext.getAdDB().addBanner(b);
			}
		}
		
		RuntimeContext.getAdDB().reopen();
		
		long after = System.currentTimeMillis();
		logger.debug("finish bannerDB: " + (after - before) + "ms");
	}
}
