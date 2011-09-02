package net.mad.ads.db.enums;

import java.io.Serializable;


/*
 * Bundesländer
 * 
 * TODO: wahrscheinlich ist es sinnvoll, für Bundesländer einen andere Form
 * zu verwenden, da in der aktuellen Umsetzung nur deutsche Bundesländer verwendet werden
 * und es sehr schwer wird, Bundesländer oder Ähnliches für andere Länder zu 
 * implementieren 
 */
public enum State implements Serializable {
//	Baden-Württemberg	
	BW(1),
//	Bayern	
	BY(2),
//	Berlin	
	BE(3),
//	Brandenburg	
	BB(4),
//	Bremen	
	HB(5),
//	Hamburg	
	HH(6),
//	Hessen	
	HE(7),
//	Mecklenburg-Vorpommern	
	MV(8),
//	Niedersachsen	
	NI(9),
//	Nordrhein-Westfalen	
	NW(10),
//	Rheinland-Pfalz	
	RP(11),
//	Saarland	
	SL(12),
//	Sachsen	
	SN(13),
//	Sachsen-Anhalt	
	ST(14),
//	Schleswig-Holstein	
	SH(15),
	//Thüringen  
	TH(16),
	All(0),
	
	UNKNOWN(-1);
	
	private int state = 0;
	private State (int state) {
		this.state = state;
	}
	
	public int getState () {
		return state;
	}
	public static State getStateForInt (int state) throws IllegalArgumentException{
		for (State s : State.values()) {
			if (s.getState() == state) {
				return s;
			}
		}
		
		return UNKNOWN;
	}
}
