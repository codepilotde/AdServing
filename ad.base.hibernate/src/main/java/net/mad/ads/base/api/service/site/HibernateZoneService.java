package net.mad.ads.base.api.service.site;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ResultList;
import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.model.site.Zone;
import net.mad.ads.base.api.model.user.User;
import net.mad.ads.base.api.model.user.impl.AdminUser;
import net.mad.ads.base.api.service.HibernateService;

public class HibernateZoneService extends HibernateService implements
		ZoneService {

	@Override
	public void add(Zone obj) throws ServiceException {
		Session session = null;

		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			obj.setCreated(new Date());

			session.save(obj);
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
	public void update(Zone obj) throws ServiceException {
		Session session = null;

		try {
			session = sessionFactory.openSession();

			Transaction tx = session.beginTransaction();

			Zone temp = findByPrimaryKey(obj.getId());

			temp.setDescription(obj.getDescription());
			temp.setName(obj.getName());
			temp.setSite(obj.getSite());

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
	public void delete(Zone obj) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			
			Zone temp = findByPrimaryKey(obj.getId());
			
			session.delete(temp);
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
	public Zone findByPrimaryKey(long id) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(Zone.class).add(Restrictions.eq("id", id));
			crit.setMaxResults(1);
			
			List<Zone> zones = crit.list();
			if (zones.size() == 1) {
				return zones.get(0);
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
	public List<Zone> findAll() throws ServiceException {
		return findAll(0, 0);
	}

	@Override
	public List<Zone> findAll(int page, int perPage)
			throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(Zone.class);
			
			if (page > 0 && perPage > 0) {
				int offset = 0;
				if (page > 1) {
					offset = (page - 1) * perPage;
				}
				
				crit.setFirstResult(offset);
				crit.setMaxResults(perPage);
			}
			
			ResultList<Zone> zones = new ResultList<Zone>();
			zones.addAll(crit.list());
			zones.setCount(crit.list().size());
			zones.setPage(page);
			zones.setPerPage(perPage);
			
			return zones;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	@Override
	public List<Zone> findBySite(Site site) throws ServiceException {
		return findBySite(site, 0, 0);
	}

	@Override
	public List<Zone> findBySite(Site site, int page, int perPage)
			throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(Zone.class);
			
			if (page > 0 && perPage > 0) {
				int offset = 0;
				if (page > 1) {
					offset = (page - 1) * perPage;
				}
				
				crit.setFirstResult(offset);
				crit.setMaxResults(perPage);
			}
			crit.createCriteria("site", "s").add(Restrictions.eq("s.id", site.getId()));
			
			
			ResultList<Zone> zones = new ResultList<Zone>();
			zones.addAll(crit.list());
			zones.setCount(crit.list().size());
			zones.setPage(page);
			zones.setPerPage(perPage);
			
			return zones;
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
			
			Criteria crit = session.createCriteria(Zone.class);
			crit.setProjection(Projections.rowCount());
			List<Long> result = crit.list();
			
			if (!result.isEmpty()) {
				return result.get(0);
			}
			
			return 0;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
	}
	
}
