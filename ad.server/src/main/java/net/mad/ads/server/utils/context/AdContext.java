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
package net.mad.ads.server.utils.context;

import net.mad.ads.base.api.BaseObject;
import net.mad.ads.db.definition.AdSlot;

/**
 * Der AdContext enthält die wichtigsten Information zu einem Request.
 * Alle Informationen, die häufig benötigt werden sind hier enthalten
 * 
 * UserID 	= 	über die Benutzer-ID wird ein Benutzer markiert. Sie wird in einem 
 * 				Cookie gespeichert und ist 24 Stunden gültig.
 * 
 * RequestID = 	Die ID des Requests ist bei allen AdServer-Aufrufen, die von einer PageImpression
 * 				erzeugt werden identisch. Sie dient unter anderem dazu doppelte Einblendung zu vermeiden 
 * 				
 * ClientUUID = Die AdUUID enthält die Informationen über die Site, Zone und Places
 * 
 * @author thmarx
 *
 */
public class AdContext extends BaseObject {
	
	private static final String USER_ID = "userid";
	private static final String REQUEST_ID = "requestid";
	private static final String SLOT = "slot";
	private static final String IP = "ip";
	
	public AdContext () {
		
	}
	
	public String getIp () {
		return get(AdContext.IP, String.class, null);
	}
	public void setIp (String ip) {
		put(AdContext.IP, ip);
	}
	public String getUserid () {
		return get(AdContext.USER_ID, String.class, null);
	}
	public void setUserid (String userid) {
		put(AdContext.USER_ID, userid);
	}
	public String getRequestid () {
		return get(AdContext.REQUEST_ID, String.class, null);
	}
	public void setRequestid (String requestid) {
		put(AdContext.REQUEST_ID, requestid);
	}
	
	public void setSlot (AdSlot slot) {
		put(AdContext.SLOT, slot);
	}
	public AdSlot getSlot () {
		return get(AdContext.SLOT, AdSlot.class, null);
	}
}
