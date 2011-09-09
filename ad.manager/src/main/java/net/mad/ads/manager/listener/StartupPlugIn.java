

package net.mad.ads.manager.listener;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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

		
	}

	public void contextInitialized(ServletContextEvent event) {
		logger.info("Init Application");
		
		try {
			
			String enviroment = event.getServletContext().getInitParameter("enviroment");
			
			logger.info("loading configuration: " + enviroment);
			
			// hier können allgemeine Konfigurationen vorgenommen werden, die erst
			// gemacht werden können, wenn die Anwendung läuft
			String path = event.getServletContext().getRealPath("/");
			
			SessionFactory sessionFactory = new Configuration()
	        .configure() // configures settings from hibernate.cfg.xml
	        .buildSessionFactory();
			
		} catch (Exception ex) {
			logger.error("error init application", ex);
		}
	}

	
}
