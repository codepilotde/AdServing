package net.mad.ads.manager;

import java.util.Properties;

import net.mad.ads.base.api.service.user.UserService;

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
	
	private static UserService userService;
	
	

	public static UserService getUserService() {
		return userService;
	}

	public static void setUserService(UserService userService) {
		RuntimeContext.userService = userService;
	}

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
