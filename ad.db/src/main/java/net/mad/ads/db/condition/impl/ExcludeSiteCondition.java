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

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
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


/**
 * Unterdrückung von Bannern auf bestimmten Seiten
 * 
 * @author tmarx
 *
 */
public class ExcludeSiteCondition implements Condition/*, Filter */{

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

	// @Override
	public Predicate<BannerDefinition> getFilterPredicate(final AdRequest request) {
		Predicate<BannerDefinition> predicate = new Predicate<BannerDefinition>() {
			@Override
			public boolean apply(BannerDefinition type) {
				
				/*
				if (true) {
					return true;
				}
				*/
				
				ExcludeSiteConditionDefinition sdef = null;
				if (type.hasConditionDefinition(ConditionDefinitions.EXCLUDE_SITE)) {
					sdef = (ExcludeSiteConditionDefinition) type.getConditionDefinition(ConditionDefinitions.EXCLUDE_SITE);
				} else {
					// keine Seitenbeschränkung
					return true;
				}
				// keine Seite im Request, aber das Banner hat eine Seitenbeschränkung
				if (Strings.isNullOrEmpty(request.getSite())) {
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
