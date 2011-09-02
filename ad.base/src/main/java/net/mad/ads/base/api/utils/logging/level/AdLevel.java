package net.mad.ads.base.api.utils.logging.level;

import java.util.logging.Level;

public class AdLevel extends Level 	{
	 // Create the new level
    public static final Level CLICK = new AdLevel("CLICK", Level.SEVERE.intValue()+1);
    public static final Level IMPRESSION = new AdLevel("IMPRESSION", Level.SEVERE.intValue()+2);

    public AdLevel(String name, int value) {
        super(name, value);
    }
    
    public static Level parse (String level) {
    	if (level.equalsIgnoreCase(CLICK.getName())) {
    		return CLICK;
    	} else if (level.equalsIgnoreCase(IMPRESSION.getName())) {
    		return IMPRESSION;
    	}
    	
    	return Level.OFF;
    }
}
