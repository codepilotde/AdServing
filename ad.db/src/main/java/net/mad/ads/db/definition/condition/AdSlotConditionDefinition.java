package net.mad.ads.db.definition.condition;


import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.definition.AdSlot;
import net.mad.ads.db.definition.ConditionDefinition;

/**
 * Steuerung auf welchen Seiten das Banner angezeigt werden soll
 * 
 * @author tmarx
 *
 */
public class AdSlotConditionDefinition implements ConditionDefinition {
	
	private List<AdSlot> slots = new ArrayList<AdSlot>();
	
	public AdSlotConditionDefinition () {
		
	}
	
	/**
	 * ID der AdSlots auf denen das Banner angezeigt werden soll
	 */
	public List<AdSlot> getSlots() {
		return this.slots;
	}
	public void addSlots (AdSlot slot) {
		this.slots.add(slot);
	}
}
