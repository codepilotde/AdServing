package net.mad.ads.base.api.model.user;

import java.util.Date;

public interface User {

	public String getId ();
	
	public void setId (String id);
	
	public String getUsername ();
	
	public void setUsername (String username);
	
	public String getPassword ();
	
	public void setPassword (String password);
	
	public String getEmail ();
	
	public void setEmail (String email);
	
	public Date getCreated ();
	
	public void setCreated (Date created);
	
	public UserType getType ();
	
	public boolean isActive ();
	
	public void setActive (boolean active);
}
