package net.mad.ads.base.api;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class BaseContext extends HashMap<String, Object> {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseContext.class);

	
	
	public BaseContext () {
		super();
	}
	
	
	public <T> T get(String key, Class<T> to, T defaultValue) {
		Object value = get(key);
		
		if (to.isAssignableFrom(value.getClass())) {
            return to.cast(value);
        }
		
		return defaultValue;
	}
}
