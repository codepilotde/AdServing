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
