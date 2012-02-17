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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.ResultList;
import net.mad.ads.base.api.model.ads.Campaign;
import net.mad.ads.base.api.service.HibernateService;
import net.mad.ads.base.api.service.banner.CampaignService;

public class HibernateCampaignService extends HibernateService implements
		CampaignService {

	@Override
	public void add(Campaign obj) throws ServiceException {
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
	public void update(Campaign obj) throws ServiceException {
		Session session = null;

		try {
			session = sessionFactory.openSession();

			Transaction tx = session.beginTransaction();

			Campaign temp = findByPrimaryKey(obj.getId());

			temp.setDescription(obj.getDescription());
			temp.setName(obj.getName());
			temp.getDateConditions().clear();
			temp.getDateConditions().addAll(obj.getDateConditions());
			temp.getTimeConditions().clear();
			temp.getTimeConditions().addAll(obj.getTimeConditions());

			session.saveOrUpdate(temp);

			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void delete(Campaign obj) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			
			Campaign temp = findByPrimaryKey(obj.getId());
			
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
	public Campaign findByPrimaryKey(long id) throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(Campaign.class).add(Restrictions.eq("id", id));
			crit.setMaxResults(1);
			
			List<Campaign> campaigns = crit.list();
			if (campaigns.size() == 1) {
				return campaigns.get(0);
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
	public List<Campaign> findAll() throws ServiceException {
		return findAll(0, 0);
	}

	@Override
	public List<Campaign> findAll(int page, int perPage)
			throws ServiceException {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(Campaign.class);
			
			if (page > 0 && perPage > 0) {
				int offset = 0;
				if (page > 1) {
					offset = (page - 1) * perPage;
				}
				
				crit.setFirstResult(offset);
				crit.setMaxResults(perPage);
			}
			
			
			ResultList<Campaign> campaigns = new ResultList<Campaign>();
			campaigns.addAll(crit.list());
			campaigns.setCount(crit.list().size());
			campaigns.setPage(page);
			campaigns.setPerPage(perPage);
			
			return campaigns;
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
			
			Criteria crit = session.createCriteria(Campaign.class);
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
