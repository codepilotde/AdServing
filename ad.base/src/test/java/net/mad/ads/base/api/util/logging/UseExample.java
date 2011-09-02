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

