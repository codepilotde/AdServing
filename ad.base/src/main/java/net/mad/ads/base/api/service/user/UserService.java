/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
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

import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ResultList;
import net.mad.ads.base.api.model.user.User;
import net.mad.ads.base.api.model.user.UserType;

public interface UserService {
	
	/**
	 * öffnet die Datenbank
	 * @param context
	 * @throws ServiceException
	 */
	public void open (BaseContext context) throws ServiceException;
	public void close () throws ServiceException;
	/**
	 * Einen Benutzer anmelden
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public User login (String username, String password) throws ServiceException;
	
	/**
	 * Liefert einen Benutzer
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public User get (String id) throws ServiceException;
	
	/**
	 * Aktualisiert einen Benutzer
	 * @param user
	 * @throws ServiceException
	 */
	public void update (User user) throws ServiceException;
	
	/**
	 * Erzeugt einen Benutzer
	 * @param user
	 * @return Die ID des neuen User
	 * @throws ServiceException
	 */
	public String create (User user) throws ServiceException;
	
	/**
	 * Aktiviert eine Benutzer
	 * @param id
	 * @throws ServiceException
	 */
	public void activate (String id) throws ServiceException;
	
	/**
	 * Deaktiviert einen Benutzer
	 * @param is
	 * @throws ServiceException
	 */
	public void deactivate (String id) throws ServiceException;
	
	/**
	 * überprüft ob ein Benutzername noch frei ist
	 * @param username
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkUsername (String username) throws ServiceException;
	/**
	 * überprüft, ob ein Email-Adresse noch frei ist
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkMail (String mail) throws ServiceException;
	
	/**
	 * �ndert das Passwort eines Benutzers
	 * 
	 * @throws ServiceException
	 */
	public void changePassword (String userid, String password) throws ServiceException;
	
	/**
	 * Listet die Benutzer
	 * @param page Die Seite die geliefert werden soll
	 * @param count Die Anzahl der Treffer pro Seite
	 * @param type Der UserType der geladen werden soll oder null wenn alle Typen geladen werden sollen
	 * @return
	 * @throws ServiceException
	 */
	public ResultList<User> list (int page, int perPage, UserType type) throws ServiceException;
	
	public long count () throws ServiceException;
}
