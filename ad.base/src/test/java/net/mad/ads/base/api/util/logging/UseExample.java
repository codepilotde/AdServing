/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
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
package net.mad.ads.base.api.util.logging;

import net.mad.ads.base.api.utils.logging.LogWrapper;

public class UseExample {
    private static LogWrapper clickLogger = new LogWrapper(UseExample.class, "logger_clicks.properties");
    private static LogWrapper impLogger = new LogWrapper(UseExample.class, "logger_impression.properties");
    
    public static void main (String[] strs){
    	clickLogger.click("es gab einen Click");
    	impLogger.impression("eine impression");
    }
}

