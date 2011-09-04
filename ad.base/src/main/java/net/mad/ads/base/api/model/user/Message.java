
/*
 * Mad-Advertisement Copyright (C) 2011 Thorsten Marx
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
package net.mad.ads.base.api.model.user;

import java.util.Date;

public interface Message {

	public String getId ();
	public void setId (String id);
	
	public String getMessage ();
	public void setMessage (String message);
	
	public String getSubject ();
	public void setSubject (String subject);
	
	public String getSourceUserId ();
	public void setSourceUserId (String userid);
	
	public String getDestUserId ();
	public void setDestUserId (String userid);
	
	public MessageType getTyp ();
	public void setType (MessageType type);
	
	public Date getCreated ();
	public void setCreated (Date created);
}
