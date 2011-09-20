package net.mad.ads.base.api.service.user;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ResultList;
import net.mad.ads.base.api.model.user.User;
import net.mad.ads.base.api.model.user.UserType;
import net.mad.ads.base.api.model.user.impl.AdminUser;
import net.mad.ads.base.api.service.HibernateService;

public class HibernateUserService extends HibernateService implements UserService {

	@Override
	public User login(String username, String password) throws ServiceException {
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(AdminUser.class).add(Restrictions.eq("username", username)).add(Restrictions.eq("password", password));
			crit.setMaxResults(1);
			
			List<AdminUser> users = crit.list();
			if (users.size() == 1) {
				return users.get(0);
			}
			return null;
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public User get(Long id) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(AdminUser.class).add(Restrictions.eq("id", id));
			crit.setMaxResults(1);
			
			List<AdminUser> users = crit.list();
			if (users.size() == 1) {
				return users.get(0);
			}
			return null;
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void update(User user) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Transaction tx = session.beginTransaction();
			
			User temp = get(user.getId());
			
			temp.setActive(user.isActive());
			temp.setCreated(user.getCreated());
			temp.setEmail(user.getEmail());
			temp.setPassword(user.getPassword());
			temp.setUsername(user.getUsername());
			
			session.update(temp);
			
			tx.commit();
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public User create(User user) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			
			user.setCreated(new Date());
			
			session.save(user);
			tx.commit();
			
			return user;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void activate(Long id) throws ServiceException {
		setActive(id, true);
	}

	@Override
	public void deactivate(Long id) throws ServiceException {
		setActive(id, false);
	}
	
	private void setActive (Long id, boolean active) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			
			User temp = get(id);
			
			temp.setActive(active);
						
			session.update(temp);
			
			tx.commit();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean checkUsername(String username) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(AdminUser.class).add(Restrictions.eq("username", username));
			crit.setMaxResults(1);
			
			return crit.list().size() > 0 ? false : true;
			
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean checkMail(String mail) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(AdminUser.class).add(Restrictions.eq("email", mail));
			crit.setMaxResults(1);
			
			return crit.list().size() > 0 ? false : true;
			
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void changePassword(Long id, String password)
			throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			
			User temp = get(id);
			
			temp.setPassword(password);
						
			session.update(temp);
			tx.commit();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public ResultList<User> list(int page, int perPage, UserType type)
			throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(AdminUser.class);
			
			int offset = 0;
			if (page > 1) {
				offset = (page - 1) * perPage;
			}
			
			crit.setFirstResult(offset);
			crit.setMaxResults(perPage);
			
			ResultList<User> users = new ResultList<User>();
			users.addAll(crit.list());
			users.setCount(crit.list().size());
			users.setPage(page);
			users.setPerPage(perPage);
			
			return users;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public long count() throws ServiceException {
	    Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(AdminUser.class);
			
			crit.setProjection(Projections.rowCount());
		    long count = (Long) crit.uniqueResult();
			
			return count;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
