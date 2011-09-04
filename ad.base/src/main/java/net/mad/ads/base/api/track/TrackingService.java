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
package net.mad.ads.base.api.track;

import java.util.Date;
import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.track.events.TrackEvent;

public interface TrackingService {
	/**
	 * öffnet die Datenbank zum Speichern des Trackings
	 * @param context
	 * @throws ServiceException
	 */
	public void open (BaseContext context) throws ServiceException;
	/**
	 * Schließt die Tracking Datenbank
	 * @throws ServiceException
	 */
	public void close () throws ServiceException;
	/**
	 * Speichert eine TrackEvent in der Datenbank
	 * @param event
	 * @throws ServiceException
	 */
	public void track (TrackEvent event) throws ServiceException;
	/**
	 * Listet die TrackEvents einer Seite für einen bestimmten Zeitraum
	 * @param site
	 * @param from
	 * @param to
	 * @return
	 * @throws ServiceException
	 */
	public List<TrackEvent> list (String site, Date from, Date to) throws ServiceException;
	/**
	 * Liefert die Anzahl von TrackEvents f�r einen bestimmten Zeitraum
	 * @param site
	 * @param from
	 * @param to
	 * @return
	 * @throws ServiceException
	 */
	public int count (String site, Date from, Date to) throws ServiceException;
	/**
	 * Löscht die TackEvents in einem bestimmten Zeitraum
	 * @param site
	 * @param from
	 * @param to
	 * @throws ServiceException
	 */
	public void delete (String site, Date from, Date to) throws ServiceException;
	/**
	 * Löscht alle TrackEvents für eine Seite
	 * @param site
	 * @throws ServiceException
	 */
	public void clear (String site) throws ServiceException;
	/**
	 * Liefert die Clicks für eine Banner
	 * @param bannerId
	 * @param from
	 * @param to
	 * @return
	 * @throws ServiceException
	 */
	public int countClicks (String bannerId, Date from, Date to) throws ServiceException;
	public int countClicks (String user, String bannerId, Date from, Date to) throws ServiceException;
	/**
	 * Liefert die Impressionen für ein Banner
	 * @param bannerId
	 * @param from
	 * @param to
	 * @return
	 * @throws ServiceException
	 */
	public int countImpressions (String bannerId, Date from, Date to) throws ServiceException;
	public int countImpressions (String user, String bannerId, Date from, Date to) throws ServiceException;
	/**
	 * Löscht die Impressions für ein Banner
	 * @param bannerId
	 * @param from
	 * @param to
	 * @throws ServiceException
	 */
	public void deleteImpressions(String bannerId, Date from, Date to) throws ServiceException;
	/**
	 * Löscht die Clicks für ein Banner
	 * @param bannerId
	 * @param from
	 * @param to
	 * @throws ServiceException
	 */
	public void deleteClicks(String bannerId, Date from, Date to) throws ServiceException;
}
