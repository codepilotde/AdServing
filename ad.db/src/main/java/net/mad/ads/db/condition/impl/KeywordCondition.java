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
import net.mad.ads.db.definition.Keyword;
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
public class KeywordCondition implements Condition {

	@Override
	public void addQuery(AdRequest request, BooleanQuery mainQuery) {
		if (request.getKeywords() == null || request.getKeywords().size() == 0) {
			return;
		}
		
		BooleanQuery query = new BooleanQuery();
		
		BooleanQuery temp = new BooleanQuery();
		
		// keywords einfügen
		for (String k : request.getKeywords()) {
			temp.add(new TermQuery(new Term(AdDBConstants.ADDB_BANNER_KEYWORD, k)), Occur.SHOULD);
		}
		// all keywords einfügen
		temp.add(new TermQuery(new Term(AdDBConstants.ADDB_BANNER_KEYWORD, AdDBConstants.ADDB_BANNER_KEYWORD_ALL)), Occur.SHOULD);
		
		query.add(temp, Occur.MUST);
		mainQuery.add(query, Occur.MUST);
	}

	@Override
	public void addFields(Document bannerDoc, BannerDefinition bannerDefinition) {
		
		KeywordConditionDefinition kdef = null;
		if (bannerDefinition.hasConditionDefinition(ConditionDefinitions.KEYWORD)) {
			kdef = (KeywordConditionDefinition) bannerDefinition.getConditionDefinition(ConditionDefinitions.KEYWORD);
		}
		
		if (kdef != null && kdef.getKeywords().size() > 0) {
			// keywords im Dokument speichern
			List<Keyword> kws = kdef.getKeywords();
			for (Keyword k : kws) {
				bannerDoc.add(new Field(AdDBConstants.ADDB_BANNER_KEYWORD, k.getWord(), Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
			}
		} else {
			/*
			 * für alle Banner ohne angegebenem Keyword wird das default ALL-Keyword gesetzt
			 */
			bannerDoc.add(new Field(AdDBConstants.ADDB_BANNER_KEYWORD, AdDBConstants.ADDB_BANNER_KEYWORD_ALL, Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
		}
	}

}
