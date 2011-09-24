package net.mad.ads.base.api.service.site;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ResultList;
import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.model.site.Zone;
import net.mad.ads.base.api.model.user.User;
import net.mad.ads.base.api.model.user.impl.AdminUser;
import net.mad.ads.base.api.service.HibernateService;

public class HibernatePlaceService extends HibernateService implements
		PlaceService {

	@Override
	public void add(Place obj) throws ServiceException {
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
	public void update(Place obj) throws ServiceException {
		Session session = null;

		try {
			session = sessionFactory.openSession();

			Transaction tx = session.beginTransaction();

			Place temp = findByPrimaryKey(obj.getId());

			temp.setDescription(obj.getDescription());
			temp.setName(obj.getName());
			temp.setZone(obj.getZone());

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
	public void delete(Place obj) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			
			Place temp = findByPrimaryKey(obj.getId());
			
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
	public Place findByPrimaryKey(long id) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(Place.class).add(Restrictions.eq("id", id));
			crit.setMaxResults(1);
			
			List<Place> places = crit.list();
			if (places.size() == 1) {
				return places.get(0);
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
	public List<Place> findAll() throws ServiceException {
		return findAll(0, 0);
	}

	@Override
	public List<Place> findAll(int page, int perPage)
			throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(Place.class);
			
			if (page > 0 && perPage > 0) {
				int offset = 0;
				if (page > 1) {
					offset = (page - 1) * perPage;
				}
				
				crit.setFirstResult(offset);
				crit.setMaxResults(perPage);
			}
			
			
			ResultList<Place> places = new ResultList<Place>();
			places.addAll(crit.list());
			places.setCount(crit.list().size());
			places.setPage(page);
			places.setPerPage(perPage);
			
			return places;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Place> findByZone(Zone zone) throws ServiceException {
		return findByZone(zone, 0, 0);
	}

	@Override
	public List<Place> findByZone(Zone zone, int page, int perPage)
			throws ServiceException {
Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(Place.class);
			
			if (page > 0 && perPage > 0) {
				int offset = 0;
				if (page > 1) {
					offset = (page - 1) * perPage;
				}
				
				crit.setFirstResult(offset);
				crit.setMaxResults(perPage);
			}
			crit.createCriteria("zone", "z").add(Restrictions.eq("z.id", zone.getId()));
			
			
			ResultList<Place> places = new ResultList<Place>();
			places.addAll(crit.list());
			places.setCount(crit.list().size());
			places.setPage(page);
			places.setPerPage(perPage);
			
			return places;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
