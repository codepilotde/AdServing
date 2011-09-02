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
