package net.mad.ads.base.api.service.site;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.service.HibernateService;
import net.mad.ads.base.api.service.user.HibernateUserService;
import net.mad.ads.base.api.service.user.UserService;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernateSiteServiceTest {

	private SessionFactory sessionFactory;
	private SiteService sites;
	
	@Before
	public void  before () throws Exception {
		try {
			File hibernateConfig = new File("src/etc/hibernate.cfg.xml");
			sessionFactory = new Configuration().configure(hibernateConfig).buildSessionFactory();
			
			sites = new HibernateSiteService();
			BaseContext context = new BaseContext();
			context.put(HibernateService.SESSION_FACTORY, sessionFactory);
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
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		s1.setUrl("http://testseite.de");
		
		sites.add(s1);
		
		s1 = sites.findByPrimaryKey(s1.getId());
		
		assertNotNull(s1);
		assertEquals("wrong description", "Das ist eine Seite zum testen", s1.getDescription());
		assertEquals("wrong name", "testseite", s1.getName());
		assertEquals("wrong url", "http://testseite.de", s1.getUrl());
	}

	@Test
	public void testUpdate() throws Exception {
		Site s1 = new Site();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		s1.setUrl("http://testseite.de");
		
		sites.add(s1);
		
		s1 = sites.findByPrimaryKey(s1.getId());
		
		s1.setDescription("Das ist eine Seite zum demo");
		s1.setName("demoseite");
		s1.setUrl("http://demoseite.de");
		
		sites.update(s1);
		
		assertNotNull(s1);
		assertEquals("wrong description", "Das ist eine Seite zum demo", s1.getDescription());
		assertEquals("wrong name", "demoseite", s1.getName());
		assertEquals("wrong url", "http://demoseite.de", s1.getUrl());
	}

	@Test
	public void testDelete() throws Exception {
		Site s1 = new Site();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		s1.setUrl("http://testseite.de");
		
		sites.add(s1);
		s1 = sites.findByPrimaryKey(s1.getId());
		sites.delete(s1);
		
		s1 = sites.findByPrimaryKey(s1.getId());
		
		assertNull(s1);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		Site s1 = new Site();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		s1.setUrl("http://testseite.de");
		
		sites.add(s1);
		s1 = sites.findByPrimaryKey(s1.getId());
		
		assertNotNull(s1);
	}

	@Test
	public void testFindAll() throws Exception {
		Site s1 = new Site();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		s1.setUrl("http://testseite.de");
		
		sites.add(s1);
		
		s1 = new Site();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("demoseite");
		s1.setUrl("http://testseite.de");
		
		sites.add(s1);
		
		assertEquals("", 2, sites.findAll().size());
	}

	@Test
	public void testFindAllIntInt() throws Exception {
		Site s1 = new Site();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		s1.setUrl("http://testseite.de");
		
		sites.add(s1);
		
		s1 = new Site();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("demoseite");
		s1.setUrl("http://testseite.de");
		
		sites.add(s1);
		
		
		
		List<Site> result = sites.findAll(1, 1);
		
		assertEquals(1, result.size());
		assertEquals("testseite", result.get(0).getName());
		
		result = sites.findAll(2, 1);
		
		assertEquals(1, result.size());
		assertEquals("demoseite", result.get(0).getName());
		
		
		result = sites.findAll(1, 2);
		
		assertEquals(2, result.size());
	}

}
