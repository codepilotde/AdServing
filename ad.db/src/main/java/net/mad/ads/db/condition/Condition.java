
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
package net.mad.ads.db.condition;


import java.io.Serializable;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;

/**
 * Bedingung die ein Banner erfüllen kann oder muss
 * 
 * Eine Condition erweitert zum einen das Dokument, das im Index gespeichert wird
 * zum anderen wird das Query zum suchen passender Banner erweitert um die entsprechende 
 * Condition zu erfüllen 
 * 
 * @author thmarx
 *
 */
public interface Condition {
	/**
	 * Aufbereitung des Queries
	 * 
	 * @param request
	 * @param mainQuery
	 */
	public void addQuery (AdRequest request, BooleanQuery mainQuery);
	/**
	 * Erweitert das Dokument um die f�r diese Bedingung benötigten Felder
	 * 
	 * @param bannerDoc
	 * @param bannerDefinition
	 */
	public void addFields (Document bannerDoc, BannerDefinition bannerDefinition);
}
