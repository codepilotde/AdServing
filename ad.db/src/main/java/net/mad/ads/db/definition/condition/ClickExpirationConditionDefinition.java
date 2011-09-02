package net.mad.ads.db.definition.condition;

import java.util.EnumMap;

import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.enums.ExpirationResolution;
/**
 * Steuerung der Anzahl an Klicks, die ein Banner nicht überschreiten soll.
 * So kann gesteuert werden, dass ein Banner pro Tag nur 1000 man angeklickt werden soll
 * 
 * @author tmarx
 *
 */
public class ClickExpirationConditionDefinition implements ConditionDefinition {

	private EnumMap<ExpirationResolution, Integer> clickExpirations = new EnumMap<ExpirationResolution, Integer>(ExpirationResolution.class);
	
	public ClickExpirationConditionDefinition () {
		
	}
	
	public EnumMap<ExpirationResolution, Integer> getClickExpirations () {
		return this.clickExpirations;
	}
}
