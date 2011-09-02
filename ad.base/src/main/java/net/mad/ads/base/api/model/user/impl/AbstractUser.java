package net.mad.ads.base.api.model.user.impl;

import java.util.Date;
import java.util.HashMap;

import net.mad.ads.base.api.BaseObject;
import net.mad.ads.base.api.model.user.User;
import net.mad.ads.base.api.model.user.UserType;

public abstract class AbstractUser extends BaseObject implements User {

	protected static final String USERNAME 		= "username";
	protected static final String PASSWORD 		= "password";
	protected static final String EMAIL 		= "email";
	protected static final String CREATED 		= "created";
	protected static final String TYPE 			= "type";
	protected static final String ID 			= "id";
	protected static final String ACTIVE		= "active";
	
	protected AbstractUser (UserType type) {
		put(TYPE, type);
	}
	
	public boolean isActive () {
		return get(ACTIVE, Boolean.class, false);
	}
	
	public void setActive (boolean active) {
		put(ACTIVE, active);
	}
	
	@Override
	public String getId() {
		return get(ID, String.class, null);
	}
	
	@Override
	public void setId(String id) {
		put(ID, id);
	}
	
	@Override
	public String getUsername() {
		return get(USERNAME, String.class, null);
	}

	@Override
	public void setUsername(String username) {
		put(USERNAME, username);
	}

	@Override
	public String getPassword() {
		return get(PASSWORD, String.class, null);
	}

	@Override
	public void setPassword(String password) {
		put(PASSWORD, password);
	}

	@Override
	public String getEmail() {
		return get(EMAIL, String.class, null);
	}

	@Override
	public void setEmail(String email) {
		put(EMAIL, email);
	}

	@Override
	public Date getCreated() {
		return get(CREATED, Date.class, null);
	}

	@Override
	public void setCreated(Date created) {
		put(CREATED, created);
	}
	
	@Override
	public UserType getType() {
		return get(TYPE, UserType.class, UserType.Unknown);
	}
}
