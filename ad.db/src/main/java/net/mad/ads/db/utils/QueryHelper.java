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
package net.mad.ads.db.utils;

import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;


import net.mad.ads.db.AdDBManager;
import net.mad.ads.db.condition.Condition;
import net.mad.ads.db.db.request.AdRequest;


public class QueryHelper {
	private static QueryHelper INSTANCE = null;
	
	private QueryHelper() {
	}
	
	public static synchronized QueryHelper getInstance () {
		if (INSTANCE == null) {
			INSTANCE = new QueryHelper();
		}
		return INSTANCE;
	}
	
	public Query getConditionalQuery (AdRequest request) {
		if (!request.hasConditions()) {
			return null;
		}
		BooleanQuery query = new BooleanQuery();
		
		for (Condition condition : AdDBManager.getInstance().getConditions()) {
			condition.addQuery(request, query);
		}
		
		if (query.getClauses() == null || query.getClauses().length == 0){
			return null;
		}
		return query;
	}
}
