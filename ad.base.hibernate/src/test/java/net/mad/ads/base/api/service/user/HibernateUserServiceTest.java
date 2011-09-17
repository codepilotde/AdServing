package net.mad.ads.base.api.service.user;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;
import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.model.ResultList;
import net.mad.ads.base.api.model.user.User;
import net.mad.ads.base.api.model.user.impl.AdminUser;
import net.mad.ads.base.api.service.HibernateService;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernateUserServiceTest {
	
	private static SessionFactory sessionFactory;
	private static UserService users;
	
	@BeforeClass
	public static void  before () throws Exception {
		try {
			File hibernateConfig = new File("src/etc/hibernate.cfg.xml");
			sessionFactory = new Configuration().configure(hibernateConfig).buildSessionFactory();
			
			users = new HibernateUserService();
			BaseContext context = new BaseContext();
			context.put(HibernateService.SESSION_FACTORY, sessionFactory);
			users.open(context);
			
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			
			AdminUser user = new AdminUser();
			user.setActive(true);
			user.setCreated(new Date());
			user.setEmail("admin@adserver.org");
			user.setPassword("password");
			user.setUsername("admin");
			
			
			session.save(user);
//			session.flush();
			
			session.createCriteria(AdminUser.class).list();
			
			System.out.println(user.getId());
			
			tx.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@AfterClass
	public static void after () {
		sessionFactory.close();
	}
	

	@Test
	public void testLogin() throws Exception {
		
		Session session = sessionFactory.openSession();
		
		
		User user = users.login("admin", "password"); 
		
		
		assertNotNull("could not login user by username and password", user);
	}

	@Test
	public void testGet() throws Exception {
		User user = users.get(1L);
		
		assertNotNull("could not get user by id", user);
	}

	@Test
	public void testUpdate() throws Exception {
		User user = users.get(1L);
		user.setEmail("admin2@adserver.org");
		
		users.update(user);
		
		user = users.get(1L);
		assertEquals("email address not changed", "admin2@adserver.org", user.getEmail());
	}

	@Test
	public void testCreate() throws Exception {
		User user = new AdminUser();
		user.setActive(false);
		user.setCreated(new Date());
		user.setEmail("admin3@adserver.org");
		user.setPassword("password");
		user.setUsername("admin1");
		
		user = users.login("admin1", "password");
		
		assertNotNull("user not created", user);
	}

	@Test
	public void testActivate() throws Exception {
		users.activate(2L);
		
		User user = users.get(2l);
		assertTrue("user not active", user.isActive());
	}

	@Test
	public void testDeactivate() throws Exception {
		users.deactivate(2L);
		
		User user = users.get(2l);
		assertFalse("user is active", user.isActive());
	}

	@Test
	public void testCheckUsername() throws Exception {
		assertFalse("admin is free", users.checkUsername("admin"));
	}

	@Test
	public void testCheckMail() throws Exception {
		assertFalse("admin2@adserver.org is free", users.checkMail("admin2@adserver.org"));
	}

	@Test
	public void testChangePassword() throws Exception {
		users.changePassword(2l, "passwordola");
		
		User user = users.get(2L);
		assertEquals("password not changed", "passwordola", user.getPassword());
	}

	@Test
	public void testList() throws Exception {
		ResultList<User> result = users.list(1, 10, null);
		
		assertTrue("user list empty", result.size() > 0);
	}

	@Test
	public void testCount() throws Exception {
		long count = users.count();
		
		assertTrue("wrong number of users found", count == 2);
	}

}
