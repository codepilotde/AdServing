package net.mad.ads.base.api.service.user;


import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;
import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.model.ResultList;
import net.mad.ads.base.api.model.user.User;
import net.mad.ads.base.api.model.user.impl.AdminUser;
import net.mad.ads.base.api.service.*;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private User createUser (User user) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
						
			session.save(user);
			
			tx.commit();
			session.close();
			
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@AfterClass
	public static void after () {
		sessionFactory.close();
	}
	

	@Test
	public void testLogin() throws Exception {
		
		AdminUser user = new AdminUser();
		user.setActive(true);
		user.setCreated(new Date());
		user.setEmail("admin@adserver.org");
		user.setPassword("password");
		user.setUsername("admin1");
		user = (AdminUser) createUser(user);
		
		
		User temp = users.login("admin", "password"); 
		
		
		assertNotNull("could not login user by username and password", user);
	}

	@Test
	public void testGet() throws Exception {
		
		AdminUser user = new AdminUser();
		user.setActive(true);
		user.setCreated(new Date());
		user.setEmail("admin@adserver.org");
		user.setPassword("password");
		user.setUsername("admin2");
		user = (AdminUser) createUser(user);
		
		user = (AdminUser) users.get(user.getId());
		
		assertNotNull("could not get user by id", user);
	}

	@Test
	public void testUpdate() throws Exception {
		
		AdminUser user = new AdminUser();
		user.setActive(true);
		user.setCreated(new Date());
		user.setEmail("admin@adserver.org");
		user.setPassword("password");
		user.setUsername("admin3");
		user = (AdminUser) createUser(user);
		
		user = (AdminUser) users.get(user.getId());
		user.setEmail("admin2@adserver.org");
		
		users.update(user);
		
		user = (AdminUser) users.get(user.getId());
		assertEquals("email address not changed", "admin2@adserver.org", user.getEmail());
	}

	@Test
	public void testCreate() throws Exception {
		User user = new AdminUser();
		user.setActive(false);
		user.setCreated(new Date());
		user.setEmail("admin3@adserver.org");
		user.setPassword("password");
		user.setUsername("admin4");
		
		user = users.create(user);
		
		user = users.login("admin4", "password");
		
		assertNotNull("user not created", user);
	}

	@Test
	public void testActivate() throws Exception {
		
		AdminUser user = new AdminUser();
		user.setActive(false);
		user.setCreated(new Date());
		user.setEmail("admin@adserver.org");
		user.setPassword("password");
		user.setUsername("admin5");
		user = (AdminUser) createUser(user);
		
		users.activate(user.getId());
		
		user = (AdminUser) users.get(user.getId());
		assertTrue("user not active", user.isActive());
	}

	@Test
	public void testDeactivate() throws Exception {
		
		AdminUser user = new AdminUser();
		user.setActive(true);
		user.setCreated(new Date());
		user.setEmail("admin@adserver.org");
		user.setPassword("password");
		user.setUsername("admin6");
		user = (AdminUser) createUser(user);
		
		users.deactivate(user.getId());
		
		user = (AdminUser) users.get(user.getId());
		assertFalse("user is active", user.isActive());
	}

	@Test
	public void testCheckUsername() throws Exception {
		
		AdminUser user = new AdminUser();
		user.setActive(true);
		user.setCreated(new Date());
		user.setEmail("admin@adserver.org");
		user.setPassword("password");
		user.setUsername("admin7");
		user = (AdminUser) createUser(user);
		
		assertFalse("admin is free", users.checkUsername("admin7"));
	}

	@Test
	public void testCheckMail() throws Exception {
		
		AdminUser user = new AdminUser();
		user.setActive(true);
		user.setCreated(new Date());
		user.setEmail("admin8@adserver.org");
		user.setPassword("password");
		user.setUsername("admin8");
		user = (AdminUser) createUser(user);
		
		assertFalse("admin8@adserver.org is free", users.checkMail("admin8@adserver.org"));
	}

	@Test
	public void testChangePassword() throws Exception {
		
		AdminUser user = new AdminUser();
		user.setActive(true);
		user.setCreated(new Date());
		user.setEmail("admin8@adserver.org");
		user.setPassword("password");
		user.setUsername("admin8");
		user = (AdminUser) createUser(user);
		
		users.changePassword(user.getId(), "passwordola");
		
		user = (AdminUser) users.get(user.getId());
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
		
		assertTrue("wrong number of users found", count > 0L);
	}

}
