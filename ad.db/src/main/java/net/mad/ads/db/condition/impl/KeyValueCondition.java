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

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;

import net.mad.ads.db.AdDBConstants;
import net.mad.ads.db.condition.Condition;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.KeyValue;
import net.mad.ads.db.definition.Keyword;
import net.mad.ads.db.definition.condition.KeyValueConditionDefinition;
import net.mad.ads.db.definition.condition.KeywordConditionDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.Country;

/**
 * Keyword-Bedingung
 * 
 * Die Klasse erweitert das Document und das Query
 * 
 * @author tmarx
 *
 */
public class KeyValueCondition implements Condition {

	@Override
	public void addQuery(AdRequest request, BooleanQuery mainQuery) {
		if (request.getKeyValues() == null || request.getKeyValues().isEmpty()) {
			return;
		}
		
		BooleanQuery query = new BooleanQuery();
		
		BooleanQuery temp = new BooleanQuery();
		
		// keyvalues einfügen
		for (String k : request.getKeyValues().keySet()) {
			temp.add(new TermQuery(new Term(AdDBConstants.ADDB_BANNER_KEYVALUE + "_" + k , request.getKeyValues().get(k))), Occur.SHOULD);
		}
		// all keyvalues einfügen
		temp.add(new TermQuery(new Term(AdDBConstants.ADDB_BANNER_KEYVALUE, AdDBConstants.ADDB_BANNER_KEYVALUE_ALL)), Occur.SHOULD);
		
		query.add(temp, Occur.MUST);
		mainQuery.add(query, Occur.MUST);
	}

	@Override
	public void addFields(Document bannerDoc, BannerDefinition bannerDefinition) {
		
		KeyValueConditionDefinition kdef = null;
		if (bannerDefinition.hasConditionDefinition(ConditionDefinitions.KEYVALUE)) {
			kdef = (KeyValueConditionDefinition) bannerDefinition.getConditionDefinition(ConditionDefinitions.KEYVALUE);
		}
		
		if (kdef != null && !kdef.getKeyValues().isEmpty()) {
			// keyvalues im Dokument speichern
			List<KeyValue> kws = kdef.getKeyValues();
			for (KeyValue k : kws) {
				bannerDoc.add(new Field(AdDBConstants.ADDB_BANNER_KEYVALUE + "_" + k.key, k.value, Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
			}
		} else {
			/*
			 * für alle Banner ohne angegebenem KeyValue wird das default ALL-KeyValue gesetzt
			 */
			bannerDoc.add(new Field(AdDBConstants.ADDB_BANNER_KEYVALUE, AdDBConstants.ADDB_BANNER_KEYVALUE_ALL, Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
		}
	}

}
