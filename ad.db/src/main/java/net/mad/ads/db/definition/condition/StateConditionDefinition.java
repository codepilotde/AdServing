package net.mad.ads.db.definition.condition;

import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.enums.State;
/**
 * Steuerung, für welches Bundesland das Banner gefunden werden soll
 * @author tmarx
 *
 */
public class StateConditionDefinition implements ConditionDefinition {
	
	private List<State> states = new ArrayList<State>();
	
	public StateConditionDefinition () {
		
	}
	
	/**
	 * in welchen Bundesländer soll das Banner angezeigt werden
	 * @return
	 */
	public List<State> getStates () {
		return this.states;
	}
	public void addState (State state) {
		this.states.add(state);
	}
}
