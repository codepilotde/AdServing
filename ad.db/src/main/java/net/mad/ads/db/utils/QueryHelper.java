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
