/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
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
import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.model.site.Place;
import net.mad.ads.base.api.model.site.Site;
import net.mad.ads.base.api.model.site.Zone;

public interface ZoneService {
	public void open (BaseContext context) throws ServiceException;
	public void close () throws ServiceException;
	
	public void add(Zone obj) throws ServiceException;
    public void update(Zone obj) throws ServiceException;
    public void delete(Zone obj) throws ServiceException;
    public Zone findByPrimaryKey(long id) throws ServiceException;
    public List<Zone> findAll() throws ServiceException;
    public List<Zone> findAll(int page, int perPage) throws ServiceException;
    
    public List<Zone> findBySite(Site site) throws ServiceException;
    public List<Zone> findBySite(Site site, int page, int perPage) throws ServiceException;
}
