package net.mad.ads.db.definition.condition;

import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.definition.Keyword;
/**
 * Steuerung, für welche Keywords das Banner angezeigt werden soll
 * 
 * @author tmarx
 *
 */
public class KeywordConditionDefinition implements ConditionDefinition {

	private List<Keyword> keywords = new ArrayList<Keyword>();
	
	public KeywordConditionDefinition () {
		
	}
	
	/**
	 * Keywords für die das Banner angezeigt werden soll
	 */
	public List<Keyword> getKeywords () {
		return this.keywords;
	}
	public void addKeyword (Keyword keyword) {
		this.keywords.add(keyword);
	}
}
