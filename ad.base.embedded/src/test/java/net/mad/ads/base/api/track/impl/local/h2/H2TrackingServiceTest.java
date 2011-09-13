package net.mad.ads.base.api.track.impl.local.h2;

import net.mad.ads.base.api.EmbeddedBaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.events.ClickTrackEvent;
import net.mad.ads.base.api.track.events.TrackEvent;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.h2.jdbcx.JdbcDataSource;

public class H2TrackingServiceTest {

	private static TrackingService ts;
	
	@BeforeClass
	public static void init () {
		 JdbcDataSource ds = new JdbcDataSource();
		 ds.setURL("jdbc:h2:mem:test");
		 ds.setUser("sa");
		 ds.setPassword("sa");
		 
		 EmbeddedBaseContext context = new EmbeddedBaseContext();
		 context.put(EmbeddedBaseContext.EMBEDDED_TRACKING_DATASOURCE, ds);
		 
		 ts = new H2TrackingService();
		 try {
			ts.open(context);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void close () {
		if (ts != null)  {
			try {
				ts.close();
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Test
	public void test_one () {
		TrackEvent ev = new ClickTrackEvent();
		ev.setBannerId("b1");
		ev.setCampaign("c1");
		ev.setId("id 1");
		ev.setIp("ip 1");
		ev.setSite("s1");
		ev.setTime(System.currentTimeMillis());
		ev.setUser("u1");
		
		try {
			ts.track(ev);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
