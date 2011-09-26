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
package net.mad.ads.server.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.base.api.service.adserver.AdServerService;


public class AdServerServiceImpl implements AdServerService {

	private static final Logger logger = LoggerFactory.getLogger(AdServerServiceImpl.class);
	
	@Override
	public boolean add(BannerDefinition banner) {
		try {
			RuntimeContext.getAdDB().addBanner(banner);
			
			return true;
		} catch (Exception e) {
			logger.error("error add Banner: " + banner.getId(), e);
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		try {
			RuntimeContext.getAdDB().deleteBanner(id);
			
			return true;
		} catch (Exception e) {
			logger.error("error delete Banner: " + id, e);
		}
		return false;
	}

}
