/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
