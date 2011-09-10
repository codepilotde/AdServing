package net.mad.ads.manager.utils.listener;


import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.mad.ads.common.util.Properties2;
import net.mad.ads.manager.RuntimeContext;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author thorsten
 */
public class StartupPlugIn implements ServletContextListener {
	private static Logger logger = LoggerFactory.getLogger(StartupPlugIn.class);

	public void contextDestroyed(ServletContextEvent event) {
		logger.debug("stop application");
		
		RuntimeContext.getSessionFactory().close();

	}

	public void contextInitialized(ServletContextEvent event) {
		logger.info("Init Application");

		try {

			String enviroment = event.getServletContext().getInitParameter(
					"enviroment");

			String configDirectory = new File(".").getAbsolutePath(); // event.getServletContext().getInitParameter("configDirectory");
			
			
			if (System.getProperties().containsKey("mad.home")) {
				configDirectory = System.getProperty("mad.home");
			}
			
			if (!configDirectory.endsWith("/")) {
				configDirectory += "/";
			}
			
			System.setProperty("mad.home", configDirectory);
			
			
			
			configDirectory += "config/manager/";
			
			PropertyConfigurator.configure(Properties2.loadProperties(configDirectory + "log4j.properties"));
			RuntimeContext.setProperties(Properties2.loadProperties(configDirectory + "config.properties"));

			// hier können allgemeine Konfigurationen vorgenommen werden, die
			// erst
			// gemacht werden können, wenn die Anwendung läuft
			String path = event.getServletContext().getRealPath("/");

			
			File hibernateConfig = new File(configDirectory + "hibernate.cfg.xml");
			SessionFactory sessionFactory = new Configuration().configure(hibernateConfig).buildSessionFactory();
			RuntimeContext.setSessionFactory(sessionFactory);

		} catch (Exception ex) {
			logger.error("error init application", ex);
		}
	}

}
