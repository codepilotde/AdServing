package net.mad.ads.manager;

import java.util.Properties;

import org.hibernate.SessionFactory;

public final class RuntimeContext {
	
	/*
	 * No instance of the RuntimeContext
	 */
	private RuntimeContext(){}
	
	/**
	 * Properties, die beim starten der Anwendungen geladen werden
	 */
	private static Properties properties = new Properties();
	
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		RuntimeContext.sessionFactory = sessionFactory;
	}

	public static void setProperties (Properties props) {
		properties = props;
	}
	public static Properties getProperties () {
		return properties;
	}
	
}
