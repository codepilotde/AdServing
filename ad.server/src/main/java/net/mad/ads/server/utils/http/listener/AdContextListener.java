package net.mad.ads.server.utils.http.listener;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mad.ads.server.utils.context.AdContext;
import net.mad.ads.server.utils.context.AdContextHelper;

/**
 * Der AdContextLister stellt den späteren Servlets den AdContext
 * über ein ThreadLocal Variable zur Verfügung
 * 
 * @author tmarx
 *
 */
public class AdContextListener implements Filter {

	public final static ThreadLocal<AdContext> ADCONTEXT = new ThreadLocal<AdContext>();
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		
		AdContext context = AdContextHelper.getAdContext(req, res);
		ADCONTEXT.set(context);
		
		chain.doFilter(request, response);
		
		ADCONTEXT.remove();
	}

	@Override
	public void init(FilterConfig config) throws ServletException {}
	@Override
	public void destroy() {}

}
