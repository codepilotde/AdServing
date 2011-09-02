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
