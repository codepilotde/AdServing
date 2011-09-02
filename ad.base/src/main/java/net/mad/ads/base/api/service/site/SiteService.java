package net.mad.ads.base.api.service.site;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.site.Site;

public interface SiteService {
	
	public void open (BaseContext context) throws ServiceException;
	public void close () throws ServiceException;
	
	public String add (Site site) throws ServiceException;
	
	public void update (Site site) throws ServiceException;
	
	public void delete () throws ServiceException;
	
	public void get () throws ServiceException;
}
