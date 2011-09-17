package net.mad.ads.base.api.service;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;

import org.hibernate.SessionFactory;

public class HibernateService {
	
	public static final String SESSION_FACTORY = "session_factory";

	protected SessionFactory sessionFactory;

	
	public void open(BaseContext context) throws ServiceException {
		this.sessionFactory = context.get(SESSION_FACTORY, SessionFactory.class, null);
	}

	
	public void close() throws ServiceException {
		this.sessionFactory = null;
	}
	
}
