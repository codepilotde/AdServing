package net.mad.ads.base.api.service.user;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ResultList;
import net.mad.ads.base.api.model.user.Message;
import net.mad.ads.base.api.model.user.User;

public interface MessageService {

	/**
	 * öffnet die Datenbank
	 * @param context
	 * @throws ServiceException
	 */
	public void open (BaseContext context) throws ServiceException;
	public void close () throws ServiceException;
	
	public void add (Message message) throws ServiceException;
	
	public Message get (String id, User user) throws ServiceException;
	
	public void delete (String id, User user) throws ServiceException;
	
	public ResultList<Message> list (int page, int perPage, User user) throws ServiceException;
}
