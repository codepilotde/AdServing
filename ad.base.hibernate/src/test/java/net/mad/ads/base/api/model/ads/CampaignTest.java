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
package net.mad.ads.base.api.model.ads;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ads.condition.TimeCondition;
import net.mad.ads.base.api.service.HibernateService;
import net.mad.ads.base.api.service.site.HibernatePlaceService;
import net.mad.ads.base.api.service.site.HibernateSiteService;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CampaignTest {
	
	private static SessionFactory sessionFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			File hibernateConfig = new File("src/etc/hibernate.cfg.xml");
			sessionFactory = new Configuration().configure(hibernateConfig).buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		sessionFactory.close();
	}

	@Test
	public void test() {
		Campaign c = new Campaign();
		c.setCreated(new Date());
		c.setName("Test Campagne");
		
		
		TimeCondition tc = new TimeCondition(new Time(Calendar.getInstance().getTimeInMillis()), new Time(Calendar.getInstance().getTimeInMillis()));
		c.getTimeConditions().add(tc);
		tc = new TimeCondition(new Time(Calendar.getInstance().getTimeInMillis()), new Time(Calendar.getInstance().getTimeInMillis()));
		c.getTimeConditions().add(tc);
		
		Session session = null;

		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			session.save(c);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(Campaign.class).add(Restrictions.eq("id", 1L));
			Campaign cam = (Campaign) crit.list().get(0);
			
			System.out.println(cam.getName());
			System.out.println(cam.getTimeConditions().size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
