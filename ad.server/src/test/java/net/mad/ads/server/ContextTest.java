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
package net.mad.ads.server;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;

import net.mad.ads.server.mocks.MockServletContext;
import net.mad.ads.server.utils.listener.StartupPlugIn;

import junit.framework.TestCase;

import static org.mockito.Mockito.*;

public class ContextTest extends TestCase {
	
	public void testGetContext () {
		
		ServletContextEvent event = new ServletContextEvent(new MockServletContext());
		((MockServletContext)event.getServletContext()).getInitParameters().put("enviroment", "development");
		
		StartupPlugIn plugin = new StartupPlugIn();
		
		
		plugin.contextInitialized(event);
		
		
		plugin.contextDestroyed(event);
		
		
	}
}
