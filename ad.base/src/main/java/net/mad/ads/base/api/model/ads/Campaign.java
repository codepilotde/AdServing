package net.mad.ads.base.api.model.ads;

import java.util.EnumMap;

import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;

/**
 * Die Kampagne ist das Mutter-Objekt der Banner. Jedes
 * Banner liegt in einer Kampagne und übernimmt die Einstellung, die dort 
 * gemacht werden. So k�nnen Bedingung, wann die Banner angezeigt werden sollen
 * global konfiguriert werden.
 * 
 * 
 * @author thorsten
 *
 */
public class Campaign {
	
	private String id;
	private String name;
	
	/*
	 * Bedingungen für die Anzeige des Banners
	 */
	private EnumMap<ConditionDefinitions, ConditionDefinition> conditionDefinitions = new EnumMap<ConditionDefinitions, ConditionDefinition>(ConditionDefinitions.class);

	/**
	 * prüft, diese Kampagne irgendwelche Bedingungen hat
	 * 
	 * @return
	 */
	public boolean hasConditionDefinitions() {
		return this.conditionDefinitions.isEmpty();
	}
	/**
	 * Prüft, ob die Kampagne eine bestimmte Bedingung hat
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasConditionDefinition(ConditionDefinitions key) {
		return this.conditionDefinitions.containsKey(key);
	}

	/**
	 * Liefert eine bestimmte Bedingung dieser Kampagne
	 * 
	 * @param key
	 * @return
	 */
	public ConditionDefinition getConditionDefinition(ConditionDefinitions key) {
		return this.conditionDefinitions.get(key);
	}
	/**
	 * Fügt der Kampagne eine neue Bedingugn hinzu
	 * 
	 * @param key
	 * @param value
	 */
	public void addConditionDefinition(ConditionDefinitions key, ConditionDefinition value) {
		this.conditionDefinitions.put(key, value);
	}
}
