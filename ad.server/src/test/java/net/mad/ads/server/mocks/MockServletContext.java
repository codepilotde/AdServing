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
package net.mad.ads.server.mocks;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.FileTypeMap;
import javax.servlet.Filter;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MockServletContext implements ServletContext {

	private Map<String,String> initParameters = new HashMap<String, String>();
	private Map<String,Object> attributes = new HashMap<String, Object>();
	
	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletContext getContext(String uripath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMajorVersion() {
		return 2;
	}

	@Override
	public int getMinorVersion() {
		return 5;
	}

	@Override
	public String getMimeType(String file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getResourcePaths(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDispatcher getNamedDispatcher(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Servlet getServlet(String name) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getServlets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getServletNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void log(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void log(Exception exception, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void log(String message, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRealPath(String path) {
		File base = new File(".");
		String realPath = base.getAbsolutePath();
		
		if (!realPath.endsWith("/")) {
			realPath += "/";
		}
		realPath += "src/main/webapp";
		
		return realPath;
	}

	@Override
	public String getServerInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInitParameter(String name) {
		return initParameters.get(name);
	}
	public Map<String, String> getInitParameters () {
		return initParameters;
	}

	@Override
	public Enumeration getInitParameterNames() {
		return null;
	}

	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return attributes.get(name);
	}

	@Override
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String name, Object object) {
		attributes.put(name, object);
	}

	@Override
	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	@Override
	public String getServletContextName() {
		// TODO Auto-generated method stub
		return null;
	}

	

}