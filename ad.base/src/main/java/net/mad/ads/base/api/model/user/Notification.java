package net.mad.ads.base.api.model.user;

import java.util.Date;

public interface Notification {
	
	public String getId ();
	public void setId (String id);
	
	public String getMessage ();
	public void setMessage (String message);
	
	public String getDestUserId ();
	public void setDestUserId (String userid);
	
	public NotificationType getTyp ();
	public void setType (NotificationType type);
	
	public Date getCreated ();
	public void setCreated (Date created);
}
