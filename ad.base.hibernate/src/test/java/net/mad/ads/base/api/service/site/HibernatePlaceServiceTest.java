package net.mad.ads.base.api.service.site;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.model.site.Zone;
import net.mad.ads.base.api.service.HibernateService;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernatePlaceServiceTest {
	
	private SessionFactory sessionFactory;
	private ZoneService zones;
	private PlaceService places;
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
			
			places = new HibernatePlaceService();
			places.open(context);
			
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
		
		Place p = new Place();
		p.setDescription("test place");
		p.setName("place1");
		p.setZone(zo);
		places.add(p);
	
		
		p = places.findByPrimaryKey(p.getId());
		
		assertNotNull(p);
		assertEquals("wrong description", "test place", p.getDescription());
		assertEquals("wrong name", "place1", p.getName());
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
		
		Place p = new Place();
		p.setDescription("test place");
		p.setName("place1");
		p.setZone(zo);
		places.add(p);
	
		p = places.findByPrimaryKey(p.getId());
		
		p.setDescription("demo place");
		p.setName("place 2");
		
		places.update(p);
		
		assertNotNull(p);
		assertEquals("wrong description", "demo place", p.getDescription());
		assertEquals("wrong name", "place 2", p.getName());
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
		
		Place p = new Place();
		p.setDescription("test place");
		p.setName("place1");
		p.setZone(zo);
		places.add(p);
	
		
		p = places.findByPrimaryKey(p.getId());
		places.delete(p);
		
		p = places.findByPrimaryKey(p.getId());
		
		assertNull(p);
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
		
		Place p = new Place();
		p.setDescription("test place");
		p.setName("place1");
		p.setZone(zo);
		places.add(p);
	
		
		p = places.findByPrimaryKey(p.getId());
		
		assertNotNull(p);
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
		
		
		Place p = new Place();
		p.setDescription("test place");
		p.setName("place1");
		p.setZone(zo);
		places.add(p);		
		
		
		p = new Place();
		p.setDescription("test place");
		p.setName("place1");
		p.setZone(zo);
		places.add(p);
		
		assertEquals("", 2, places.findAll().size());
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
		
		Place p = new Place();
		p.setDescription("test place");
		p.setName("place1");
		p.setZone(zo);
		places.add(p);
		p = new Place();
		p.setDescription("test place");
		p.setName("place2");
		p.setZone(zo);
		places.add(p);
		
		
		
		List<Place> result = places.findAll(1, 1);
		
		assertEquals(1, result.size());
		assertEquals("place1", result.get(0).getName());
		
		result = places.findAll(2, 1);
		
		assertEquals(1, result.size());
		assertEquals("place2", result.get(0).getName());
		
		
		result = places.findAll(1, 2);
		
		assertEquals(2, result.size());
	}

	@Test
	public void testFindByZone() throws Exception {
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
		
		Zone z2 = new Zone();
		z2.setDescription("zone 2");
		z2.setName("zona 2");
		z2.setSite(s2);
		
		zones.add(z2);
		
		Place p = new Place();
		p.setDescription("test place");
		p.setName("place1");
		p.setZone(z1);
		places.add(p);
		
		p = new Place();
		p.setDescription("test place");
		p.setName("place1");
		p.setZone(z2);
		places.add(p);
		
		List<Place> result = places.findByZone(z1);
		
		assertEquals(1, result.size());
		assertEquals(z1.getId(), result.get(0).getZone().getId());
		
		result = places.findByZone(z2);
		
		assertEquals(1, result.size());
		assertEquals(z2.getId(), result.get(0).getZone().getId());
	}
	
	@Test
	public void testCascade() throws Exception {
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
		
		Place p = new Place();
		p.setDescription("test place");
		p.setName("place1");
		p.setZone(zo);
		places.add(p);
	
		
		sites.delete(s1);
		
		assertEquals("site note deleted", 0, sites.findAll().size());
		assertEquals("zone not deleted", 0, zones.findAll().size());
		assertEquals("place note deleted", 0, places.findAll().size());
	}

	@Test
	public void testCount() throws Exception {
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
		
		Place p = new Place();
		p.setDescription("test place");
		p.setName("place1");
		p.setZone(zo);
		places.add(p);
	
		
		assertEquals(1, sites.count());
		assertEquals(1, zones.count());
		assertEquals(1, places.count());
	}
}
