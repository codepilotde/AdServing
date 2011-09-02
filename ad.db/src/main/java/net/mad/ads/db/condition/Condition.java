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
