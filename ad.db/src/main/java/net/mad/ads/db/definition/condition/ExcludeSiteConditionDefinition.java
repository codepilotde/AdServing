package net.mad.ads.db.definition.condition;

import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.definition.ConditionDefinition;

/**
 * Steuerung auf welchen Seiten das Banner NICHT angezeigt werden soll
 * 
 * @author tmarx
 *
 */
public class ExcludeSiteConditionDefinition implements ConditionDefinition {
	
	private List<String> sites = new ArrayList<String>();
	
	public ExcludeSiteConditionDefinition () {
		
	}
	
	/**
	 * ID der Seiten auf denen das Banner NICHT angezeigt werden soll
	 */
	public List<String> getSites() {
		return this.sites;
	}
	public void addSite (String site) {
		this.sites.add(site);
	}
}
