/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
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
package net.mad.ads.base.api.service.banner;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.model.ads.Campaign;
import net.mad.ads.base.api.model.ads.condition.DateCondition;
import net.mad.ads.base.api.model.ads.condition.TimeCondition;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.service.HibernateService;
import net.mad.ads.base.api.service.user.HibernateUserService;
import net.mad.ads.base.api.service.user.UserService;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class HibernateCampaignServiceTest {

	private SessionFactory sessionFactory;
	private CampaignService campaigns;
	
	@Before
	public void  before () throws Exception {
		try {
			File hibernateConfig = new File("src/etc/hibernate.cfg.xml");
			
//			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().buildServiceRegistry();
//			MetadataSources metadataSources = new MetadataSources( serviceRegistry );
//			metadataSources.addResource("src/etc/hibernate.cfg.xml");
//			Metadata metadata = metadataSources.buildMetadata();
//			sessionFactory = metadata.buildSessionFactory();
			
			sessionFactory = new Configuration().configure(hibernateConfig).buildSessionFactory();
			
			
			campaigns = new HibernateCampaignService();
			BaseContext context = new BaseContext();
			context.put(HibernateService.SESSION_FACTORY, sessionFactory);
			campaigns.open(context);
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
		Campaign c1 = new Campaign();
		c1.setDescription("Das ist eine Seite zum testen");
		c1.setName("testseite");
		
		
		campaigns.add(c1);
		
		c1 = campaigns.findByPrimaryKey(c1.getId());
		
		assertNotNull(c1);
		assertEquals("wrong description", "Das ist eine Seite zum testen", c1.getDescription());
		assertEquals("wrong name", "testseite", c1.getName());
	}

	@Test
	public void testUpdate() throws Exception {
		Campaign s1 = new Campaign();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		
		
		campaigns.add(s1);
		
		s1 = campaigns.findByPrimaryKey(s1.getId());
		
		s1.setDescription("Das ist eine Seite zum demo");
		s1.setName("demoseite");
		
		
		campaigns.update(s1);
		
		assertNotNull(s1);
		assertEquals("wrong description", "Das ist eine Seite zum demo", s1.getDescription());
		assertEquals("wrong name", "demoseite", s1.getName());
	}

	@Test
	public void testDelete() throws Exception {
		Campaign s1 = new Campaign();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		
		campaigns.add(s1);
		s1 = campaigns.findByPrimaryKey(s1.getId());
		campaigns.delete(s1);
		
		s1 = campaigns.findByPrimaryKey(s1.getId());
		
		assertNull(s1);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		Campaign s1 = new Campaign();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		
		campaigns.add(s1);
		s1 = campaigns.findByPrimaryKey(s1.getId());
		
		assertNotNull(s1);
	}

	@Test
	public void testFindAll() throws Exception {
		Campaign s1 = new Campaign();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		
		campaigns.add(s1);
		
		s1 = new Campaign();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("demoseite");
		
		campaigns.add(s1);
		
		assertEquals("", 2, campaigns.findAll().size());
	}

	@Test
	public void testFindAllIntInt() throws Exception {
		Campaign s1 = new Campaign();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		
		campaigns.add(s1);
		
		s1 = new Campaign();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("demoseite");
		
		campaigns.add(s1);
		
		
		
		List<Campaign> result = campaigns.findAll(1, 1);
		
		assertEquals(1, result.size());
		assertEquals("testseite", result.get(0).getName());
		
		result = campaigns.findAll(2, 1);
		
		assertEquals(1, result.size());
		assertEquals("demoseite", result.get(0).getName());
		
		
		result = campaigns.findAll(1, 2);
		
		assertEquals(2, result.size());
	}
	
	@Test
	public void testCondition() throws Exception {
		Campaign s1 = new Campaign();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		
		s1.getDateConditions().add(new DateCondition(new Date(1l), new Date(2l)));
		s1.getDateConditions().add(new DateCondition(new Date(5l), new Date(6l)));
		
		s1.getTimeConditions().add(new TimeCondition(new Time(1l), new Time(2l)));
		s1.getTimeConditions().add(new TimeCondition(new Time(5l), new Time(6l)));
		
		campaigns.add(s1);
		
		s1 = campaigns.findByPrimaryKey(s1.getId());
		
		s1.setDescription("Das ist eine Seite zum demo");
		s1.setName("demoseite");
		
		s1.getDateConditions().add(new DateCondition(new Date(23223l), new Date(34324l)));
		
		
		campaigns.update(s1);
		
		s1 = campaigns.findByPrimaryKey(s1.getId());
		
		assertNotNull(s1);
		assertEquals("wrong description", "Das ist eine Seite zum demo", s1.getDescription());
		assertEquals("wrong name", "demoseite", s1.getName());
		assertEquals(3, s1.getDateConditions().size());
	}
	
	@Test
	public void testDeleteCondition() throws Exception {
		Campaign s1 = new Campaign();
		s1.setDescription("Das ist eine Seite zum testen");
		s1.setName("testseite");
		
		s1.getDateConditions().add(new DateCondition(new Date(1l), new Date(2l)));
		s1.getDateConditions().add(new DateCondition(new Date(5l), new Date(6l)));
		
		s1.getTimeConditions().add(new TimeCondition(new Time(1l), new Time(2l)));
		s1.getTimeConditions().add(new TimeCondition(new Time(5l), new Time(6l)));
		
		campaigns.add(s1);
		
		s1 = campaigns.findByPrimaryKey(s1.getId());
		
		s1.setDescription("Das ist eine Seite zum demo");
		s1.setName("demoseite");
		
		s1.getDateConditions().clear();
		
		
		campaigns.update(s1);
		
		s1 = campaigns.findByPrimaryKey(s1.getId());
		
		
		assertNotNull(s1);
		assertEquals("wrong description", "Das ist eine Seite zum demo", s1.getDescription());
		assertEquals("wrong name", "demoseite", s1.getName());
		assertEquals(0, s1.getDateConditions().size());
	}

}
