package net.mad.ads.base.api.service.user;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ResultList;
import net.mad.ads.base.api.model.user.Message;
import net.mad.ads.base.api.model.user.Notification;
import net.mad.ads.base.api.model.user.User;

public interface NotificationService {
	/**
	 * Initialisiert der Service
	 * @param context
	 * @throws ServiceException
	 */
	public void open (BaseContext context) throws ServiceException;
	public void close () throws ServiceException;
	/**
	 * Schreibt eine Notification in die Datenbank
	 * @param notificatin
	 * @throws ServiceException
	 */
	public void add (Notification notificatin) throws ServiceException;
	/**
	 * Liest eine Notification aus der Datenbank
	 * @param id
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	public Notification get (String id, User user) throws ServiceException;
	/**
	 * Löscht eine Notificaion aus der Datenbank
	 * @param id
	 * @param user
	 * @throws ServiceException
	 */
	public void delete (String id, User user) throws ServiceException;
	/**
	 * Liefert Notifications eines Benutzers
	 * @param page
	 * @param perPage
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	public ResultList<Notification> list (int page, int perPage, User user) throws ServiceException;
}
