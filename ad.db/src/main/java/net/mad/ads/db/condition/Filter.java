package net.mad.ads.db.condition;


import com.google.common.base.Predicate;

import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;

/**
 * Filter werden nach der Suche �ber das Ergebnis laufen gelassen. Dadurch können 
 * noch Banner gefiltert werden, die eine Bestimmte Eigenschaft nicht erfüllen
 * 
 * @author thmarx
 *
 */
public interface Filter {
	/**
	 * Die Eigenschaft, die ein Banner erfüllen muss um nicht aus der List herrausgefiltert zu werden
	 * 
	 * @param request
	 * @return
	 */
	public Predicate<BannerDefinition> getFilterPredicate (AdRequest request);
}
