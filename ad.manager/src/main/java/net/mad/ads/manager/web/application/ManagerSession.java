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
package net.mad.ads.manager.web.application;

import net.mad.ads.base.api.model.user.User;
import net.mad.ads.manager.RuntimeContext;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Authenticated session subclass. Note that it is derived from
 * AuthenticatedWebSession which is defined in the auth-role module.
 * 
 */
public class ManagerSession extends AuthenticatedWebSession {
	
	private static final Logger logger = LoggerFactory.getLogger(ManagerSession.class);
	
	public ManagerSession(Request request) {
		super(request);
	}

	
	@Override
	public boolean authenticate(final String username, final String password) {
//		final String WICKET = "wicket";
//
//		return WICKET.equals(username) && WICKET.equals(password);
		
		try {
			User user = RuntimeContext.getUserService().login(username, password);
			if (user != null) {
				setAttribute("user", user);
				return true;
			}
		} catch (Exception e) { 
			logger.error("", e);
		}
		return false;
	}

	
	@Override
	public Roles getRoles() {
		if (isSignedIn()) {
			// If the user is signed in, they have these roles
			return new Roles(Roles.ADMIN);
		}
		return null;
	}
}
