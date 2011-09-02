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
