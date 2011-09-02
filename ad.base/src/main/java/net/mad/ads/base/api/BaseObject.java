package net.mad.ads.base.api;

import java.util.HashMap;

public abstract class BaseObject extends HashMap<String, Object> {

	protected <T> T get(String key, Class<T> to, T defaultValue) {
		Object value = get(key);
		
		if (value == null) {
			return defaultValue;
		}
		
		if (to.isAssignableFrom(value.getClass())) {
            return to.cast(value);
        }
		
		return defaultValue;
	}
}
