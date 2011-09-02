package net.mad.ads.db.definition.condition;

import java.util.EnumMap;

import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.enums.ExpirationResolution;
/**
 * Steuerung der Anzeigehäufigkeit
 * Damit kann gesteuert werden, dass ein Banner pro Tag nur 100mal angezeigt werden soll
 * 
 * @author tmarx
 *
 */
public class ViewExpirationConditionDefinition implements ConditionDefinition {

	private EnumMap<ExpirationResolution, Integer> viewExpirations = new EnumMap<ExpirationResolution, Integer>(ExpirationResolution.class);
	
	public ViewExpirationConditionDefinition () {
		
	}
	
	public EnumMap<ExpirationResolution, Integer> getViewExpirations () {
		return this.viewExpirations;
	}
}
