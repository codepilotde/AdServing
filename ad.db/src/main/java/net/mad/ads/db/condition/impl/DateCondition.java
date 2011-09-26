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
package net.mad.ads.db.condition.impl;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.BooleanClause.Occur;

import net.mad.ads.db.AdDBConstants;
import net.mad.ads.db.condition.Condition;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.condition.DateConditionDefinition;
import net.mad.ads.db.definition.condition.TimeConditionDefinition;
import net.mad.ads.db.definition.condition.DateConditionDefinition.Period;
import net.mad.ads.db.enums.ConditionDefinitions;

/**
 * Datums Bedingung an denen ein Banner angezeigt werden soll.
 * So ist es z.B. möglich ein Banner nur in der Weihnachtszeit anzuzeigten
 * 
 * @author thmarx
 *
 */
public class DateCondition implements Condition {

	@Override
	public void addQuery(AdRequest request, BooleanQuery mainQuery) {
		if (request.getDate() != null) { 
			BooleanQuery tempquery = new BooleanQuery();
			
			/*
			 * Prefix der Indizieren beachten.
			 */
			for (int i = 0; i < TimeConditionDefinition.MAX_PERIOD_COUNT; i++) {
				BooleanQuery query = new BooleanQuery();
				
				BooleanQuery temp = new BooleanQuery();
				TermRangeQuery tQuery = new TermRangeQuery(AdDBConstants.ADDB_BANNER_DATE_FROM + i, null, request.getDate(), true, true);
				temp.add(tQuery, Occur.SHOULD);
				temp.add(new TermQuery(new Term(AdDBConstants.ADDB_BANNER_DATE_FROM + i, AdDBConstants.ADDB_BANNER_DATE_ALL)), Occur.SHOULD);
				
				query.add(temp, Occur.MUST);
				
				temp = new BooleanQuery();
				tQuery = new TermRangeQuery(AdDBConstants.ADDB_BANNER_DATE_TO + i, request.getDate(), null, true, true);
				temp.add(tQuery, Occur.SHOULD);
				temp.add(new TermQuery(new Term(AdDBConstants.ADDB_BANNER_DATE_TO + i, AdDBConstants.ADDB_BANNER_DATE_ALL)), Occur.SHOULD);
				
				query.add(temp, Occur.MUST);
				
				tempquery.add(query, Occur.SHOULD);
			}	
			mainQuery.add(tempquery, Occur.MUST);
		}

	}

	@Override
	public void addFields(Document bannerDoc, BannerDefinition bannerDefinition) {
		
		DateConditionDefinition ddef = null;
		if (bannerDefinition.hasConditionDefinition(ConditionDefinitions.DATE)) {
			ddef = (DateConditionDefinition) bannerDefinition.getConditionDefinition(ConditionDefinitions.DATE);
		}
		
		if (ddef != null && !ddef.getPeriods().isEmpty()) {
			/*
			 * 	Um die Paare von/zu der Perioden zu erhalten, werden die jeweilige Felder geprefixt.
			 *  Dadurch können bei der Suche die einzelnen Perioden unterschieden werden
			 */
			int count = 0;
			for (Period p : ddef.getPeriods()) {
				if (p.getFrom() != null) {
					bannerDoc.add(new Field(AdDBConstants.ADDB_BANNER_DATE_FROM + count, p.getFrom(), Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
				} else {
					bannerDoc.add(new Field(AdDBConstants.ADDB_BANNER_DATE_FROM + count, AdDBConstants.ADDB_BANNER_DATE_ALL, Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
				}
				
				if (p.getFrom() != null) {
					bannerDoc.add(new Field(AdDBConstants.ADDB_BANNER_DATE_TO + count, p.getTo(), Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
				} else {
					bannerDoc.add(new Field(AdDBConstants.ADDB_BANNER_DATE_TO + count, AdDBConstants.ADDB_BANNER_DATE_ALL, Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
				}
				count++;
			}
		} else {
			bannerDoc.add(new Field(AdDBConstants.ADDB_BANNER_DATE_FROM + 0, AdDBConstants.ADDB_BANNER_DATE_ALL, Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
			bannerDoc.add(new Field(AdDBConstants.ADDB_BANNER_DATE_TO + 0, AdDBConstants.ADDB_BANNER_DATE_ALL, Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
		}
	}

}
