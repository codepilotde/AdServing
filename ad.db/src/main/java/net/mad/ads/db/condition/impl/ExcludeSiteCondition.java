package net.mad.ads.db.condition.impl;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;

import com.google.common.base.Predicate;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import net.mad.ads.db.AdDBConstants;
import net.mad.ads.db.condition.Condition;
import net.mad.ads.db.condition.Filter;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.Keyword;
import net.mad.ads.db.definition.condition.DistanceConditionDefinition;
import net.mad.ads.db.definition.condition.ExcludeSiteConditionDefinition;
import net.mad.ads.db.definition.condition.SiteConditionDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.Country;
import de.marx.common.tools.Strings;

/**
 * Unterdrückung von Bannern auf bestimmten Seiten
 * 
 * @author tmarx
 *
 */
public class ExcludeSiteCondition implements Condition, Filter {

	@Override
	public void addQuery(AdRequest request, BooleanQuery mainQuery) {
		if (request.getSite() == null) {
			return;
		}
		
		
		BooleanQuery query = new BooleanQuery();
		
		BooleanQuery temp = new BooleanQuery();
		
		// Seite einfügen
		temp.add(new TermQuery(new Term(AdDBConstants.ADDB_BANNER_SITE_EXCLUDE, request.getSite())), Occur.SHOULD);
		
		query.add(temp, Occur.MUST);
		mainQuery.add(query, Occur.MUST_NOT);
//		
//		System.out.println(mainQuery.toString());
	}

	@Override
	public void addFields(Document bannerDoc, BannerDefinition bannerDefinition) {
		
		ExcludeSiteConditionDefinition sdef = null;
		if (bannerDefinition.hasConditionDefinition(ConditionDefinitions.EXCLUDE_SITE)) {
			sdef = (ExcludeSiteConditionDefinition) bannerDefinition.getConditionDefinition(ConditionDefinitions.EXCLUDE_SITE);
		}
		
		if (sdef != null && !sdef.getSites().isEmpty()) {
			// Sites im Dokument speichern
			List<String> sites = sdef.getSites();
			for (String site : sites) {
				bannerDoc.add(new Field(AdDBConstants.ADDB_BANNER_SITE_EXCLUDE, site, Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
			}
		}
	}

	@Override
	public Predicate<BannerDefinition> getFilterPredicate(final AdRequest request) {
		Predicate<BannerDefinition> predicate = new Predicate<BannerDefinition>() {
			@Override
			public boolean apply(BannerDefinition type) {
				
				if (true) {
					return true;
				}
				
				ExcludeSiteConditionDefinition sdef = null;
				if (type.hasConditionDefinition(ConditionDefinitions.EXCLUDE_SITE)) {
					sdef = (ExcludeSiteConditionDefinition) type.getConditionDefinition(ConditionDefinitions.EXCLUDE_SITE);
				} else {
					// keine Seitenbeschränkung
					return true;
				}
				// keine Seite im Request, aber das Banner hat eine Seitenbeschränkung
				if (Strings.isEmpty(request.getSite())) {
					return false;
				}
				
				if (sdef != null) {
					if (sdef.getSites().contains(request.getSite())) {
						return false;
					}
				}
				
				return true;
			}
		};
		return predicate;
	}

}
