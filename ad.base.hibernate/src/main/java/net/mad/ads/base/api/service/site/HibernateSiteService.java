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
import net.mad.ads.base.api.model.user.User;
import net.mad.ads.base.api.model.user.impl.AdminUser;
import net.mad.ads.base.api.service.HibernateService;

public class HibernateSiteService extends HibernateService implements
		SiteService {

	@Override
	public void add(Site obj) throws ServiceException {
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
	public void update(Site obj) throws ServiceException {
		Session session = null;

		try {
			session = sessionFactory.openSession();

			Transaction tx = session.beginTransaction();

			Site temp = findByPrimaryKey(obj.getId());

			temp.setDescription(obj.getDescription());
			temp.setName(obj.getName());
			temp.setUrl(obj.getUrl());

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
	public void delete(Site obj) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			
			Site temp = findByPrimaryKey(obj.getId());
			
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
	public Site findByPrimaryKey(long id) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(Site.class).add(Restrictions.eq("id", id));
			crit.setMaxResults(1);
			
			List<Site> sites = crit.list();
			if (sites.size() == 1) {
				return sites.get(0);
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
	public List<Site> findAll() throws ServiceException {
		return findAll(0, 0);
	}

	@Override
	public List<Site> findAll(int page, int perPage)
			throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(Site.class);
			
			if (page > 0 && perPage > 0) {
				int offset = 0;
				if (page > 1) {
					offset = (page - 1) * perPage;
				}
				
				crit.setFirstResult(offset);
				crit.setMaxResults(perPage);
			}
			
			
			ResultList<Site> sites = new ResultList<Site>();
			sites.addAll(crit.list());
			sites.setCount(crit.list().size());
			sites.setPage(page);
			sites.setPerPage(perPage);
			
			return sites;
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
			
			Criteria crit = session.createCriteria(Site.class);
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
