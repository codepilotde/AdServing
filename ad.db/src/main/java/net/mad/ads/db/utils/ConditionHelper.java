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
package net.mad.ads.db.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Collections2;

import net.mad.ads.db.AdDBManager;
import net.mad.ads.db.condition.Condition;
import net.mad.ads.db.condition.Filter;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;

public class ConditionHelper {

	public static ConditionHelper INSTANCE = new ConditionHelper();
	
	public static ConditionHelper getInstance () {
		return INSTANCE;
	}
	
	/**
	 * Führt alle definierten Filter auf das Ergebnis der Bannersuche aus
	 * 
	 * @param request
	 * @param banners
	 * @return
	 */
	public List<BannerDefinition> processFilter (AdRequest request, List<BannerDefinition> banners) {
		if (!request.hasConditions()) {
			return banners;
		}
		
		Collection<BannerDefinition> bcol = new ArrayList<BannerDefinition>();
		bcol.addAll(banners);
		for (Condition condition : AdDBManager.getInstance().getConditions()) {
			if (Filter.class.isInstance(condition) && !banners.isEmpty()) {
				bcol = (Collection<BannerDefinition>) Collections2.filter(bcol, ((Filter)condition).getFilterPredicate(request));
			}
		}
		banners.clear();
		banners.addAll(bcol);
		return banners;
	}
}
