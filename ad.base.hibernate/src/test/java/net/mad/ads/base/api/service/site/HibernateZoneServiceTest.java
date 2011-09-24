package net.mad.ads.base.api.service.site;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.model.site.Zone;
import net.mad.ads.base.api.service.HibernateService;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateZoneServiceTest {
	
	private SessionFactory sessionFactory;
	private ZoneService zones;
	private SiteService sites;
	
	@Before
	public void  before () throws Exception {
		try {
			File hibernateConfig = new File("src/etc/hibernate.cfg.xml");
			sessionFactory = new Configuration().configure(hibernateConfig).buildSessionFactory();
			
			zones = new HibernateZoneService();
			BaseContext context = new BaseContext();
			context.put(HibernateService.SESSION_FACTORY, sessionFactory);
			zones.open(context);
			
			sites = new HibernateSiteService();
			sites.open(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@After
	public void after () {
		sessionFactory.close();
	}
	

	@Test
	public void testAdd() throws Exception {
		Site s1 = new Site();
		s1.setDescription("seite 1");
		s1.setName("name 1");
		s1.setUrl("http://eins.de");
		sites.add(s1);
		
		Zone zo = new Zone();
		zo.setDescription("Das ist eine Zone zum testen");
		zo.setName("testzone");
		zo.setSite(s1);
		
		
		zones.add(zo);
		
		zo = zones.findByPrimaryKey(zo.getId());
		
		assertNotNull(zo);
		assertEquals("wrong description", "Das ist eine Zone zum testen", zo.getDescription());
		assertEquals("wrong name", "testzone", zo.getName());
	}

	@Test
	public void testUpdate() throws Exception {
		Site s1 = new Site();
		s1.setDescription("seite 1");
		s1.setName("name 1");
		s1.setUrl("http://eins.de");
		sites.add(s1);
		
		Zone zo = new Zone();
		zo.setDescription("Das ist eine zone zum testen");
		zo.setName("testzone");
		zo.setSite(s1);
		
		zones.add(zo);
		
		zo = zones.findByPrimaryKey(zo.getId());
		
		zo.setDescription("Das ist eine Zone zum demo");
		zo.setName("demozone");
		
		zones.update(zo);
		
		assertNotNull(zo);
		assertEquals("wrong description", "Das ist eine Zone zum demo", zo.getDescription());
		assertEquals("wrong name", "demozone", zo.getName());
	}

	@Test
	public void testDelete() throws Exception {
		Site s1 = new Site();
		s1.setDescription("seite 1");
		s1.setName("name 1");
		s1.setUrl("http://eins.de");
		sites.add(s1);
		
		Zone zo = new Zone();
		zo.setDescription("Das ist eine Zone zum testen");
		zo.setName("testzone");
		zo.setSite(s1);
		
		
		zones.add(zo);
		zo = zones.findByPrimaryKey(zo.getId());
		zones.delete(zo);
		
		zo = zones.findByPrimaryKey(zo.getId());
		
		assertNull(zo);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		Site s1 = new Site();
		s1.setDescription("seite 1");
		s1.setName("name 1");
		s1.setUrl("http://eins.de");
		sites.add(s1);
		
		Zone zo = new Zone();
		zo.setDescription("Das ist eine Zone zum testen");
		zo.setName("testzone");
		zo.setSite(s1);
		
		zones.add(zo);
		zo = zones.findByPrimaryKey(zo.getId());
		
		assertNotNull(zo);
	}

	@Test
	public void testFindAll() throws Exception {
		Site s1 = new Site();
		s1.setDescription("seite 1");
		s1.setName("name 1");
		s1.setUrl("http://eins.de");
		sites.add(s1);
		
		Zone zo = new Zone();
		zo.setDescription("Das ist eine Seite zum testen");
		zo.setName("testseite");
		zo.setSite(s1);
		
		zones.add(zo);
		
		zo = new Zone();
		zo.setDescription("Das ist eine Seite zum testen");
		zo.setName("demoseite");
		zo.setSite(s1);
		
		zones.add(zo);
		
		assertEquals("", 2, zones.findAll().size());
	}

	@Test
	public void testFindAllIntInt() throws Exception {
		Site s1 = new Site();
		s1.setDescription("seite 1");
		s1.setName("name 1");
		s1.setUrl("http://eins.de");
		sites.add(s1);
		
		Zone zo = new Zone();
		zo.setDescription("Das ist eine Seite zum testen");
		zo.setName("testseite");
		zo.setSite(s1);
		
		zones.add(zo);
		
		zo = new Zone();
		zo.setDescription("Das ist eine Seite zum testen");
		zo.setName("demoseite");
		zo.setSite(s1);
		
		zones.add(zo);
		
		
		
		List<Zone> result = zones.findAll(1, 1);
		
		assertEquals(1, result.size());
		assertEquals("testseite", result.get(0).getName());
		
		result = zones.findAll(2, 1);
		
		assertEquals(1, result.size());
		assertEquals("demoseite", result.get(0).getName());
		
		
		result = zones.findAll(1, 2);
		
		assertEquals(2, result.size());
	}

	@Test
	public void testFindBySite() throws Exception {
		Site s1 = new Site();
		s1.setDescription("seite 1");
		s1.setName("name 1");
		s1.setUrl("http://eins.de");
		sites.add(s1);
		
		Site s2 = new Site();
		s2.setDescription("seite 2");
		s2.setName("name 2");
		s2.setUrl("http://zwei.de");
		sites.add(s2);
		
		Zone z1 = new Zone();
		z1.setDescription("zone 1");
		z1.setName("zona 1");
		z1.setSite(s1);
		
		zones.add(z1);
		
		z1 = new Zone();
		z1.setDescription("zone 2");
		z1.setName("zona 2");
		z1.setSite(s2);
		
		zones.add(z1);
		
		List<Zone> result = zones.findBySite(s1);
		
		assertEquals(1, result.size());
		assertEquals(s1.getId(), result.get(0).getSite().getId());
		
		result = zones.findBySite(s2);
		
		assertEquals(1, result.size());
		assertEquals(s2.getId(), result.get(0).getSite().getId());
	}

}
