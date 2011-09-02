package net.mad.ads.db.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Collections2;

import net.mad.ads.db.AdDBManager;
import net.mad.ads.db.condition.Condition;
import net.mad.ads.db.condition.Filter;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import de.marx.common.tools.CollectionUtils;

public class ConditionHelper {

	public static ConditionHelper INSTANCE = new ConditionHelper();
	
	public static ConditionHelper getInstance () {
		return INSTANCE;
	}
	
	/**
	 * Führt alle definierten Filter auf das Ergebnis der Bannersuche aus
	 * 
	 * @param request
	 * @param banners
	 * @return
	 */
	public List<BannerDefinition> processFilter (AdRequest request, List<BannerDefinition> banners) {
		if (!request.hasConditions()) {
			return banners;
		}
		
		Collection<BannerDefinition> bcol = new ArrayList<BannerDefinition>();
		bcol.addAll(banners);
		for (Condition condition : AdDBManager.getInstance().getConditions()) {
			if (Filter.class.isInstance(condition) && !banners.isEmpty()) {
				bcol = (Collection<BannerDefinition>) Collections2.filter(bcol, ((Filter)condition).getFilterPredicate(request));
			}
		}
		banners.clear();
		banners.addAll(bcol);
		return banners;
	}
}
