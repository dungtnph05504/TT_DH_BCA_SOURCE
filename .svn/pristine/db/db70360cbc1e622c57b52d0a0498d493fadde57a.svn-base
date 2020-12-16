package com.nec.asia.nic.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Class to reveal java constants to JSTL Expression Language Uses reflection to
 * scan the declared fields of a Constants class Adds these fields to the Map.
 * Map is unmodifiable after initialization.
 * 
 * @author bright_zheng
 *
 */
public class JSTLConstants extends HashMap<Object,Object> {
	private static final long serialVersionUID = 4609412868114347472L;
	Logger logger = LoggerFactory.getLogger(JSTLConstants.class);
	private boolean initialised = false;

	public JSTLConstants() {
		Class<?> c = this.getClass();
		Field[] fields = c.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			int modifier = field.getModifiers();
			if (Modifier.isFinal(modifier) && !Modifier.isPrivate(modifier)){
				String name = null;
				Object value = null;
				try {
					name = field.getName();
					value = field.get(this);
					logger.debug("building constants{}: name={},value={}", 
							new Object[]{name,value});
					this.put(name, value);
				} catch (IllegalAccessException e) {
					logger.error("Errors occurred while exposing Constants[name={},value={}] to application scope:{}",
							new Object[]{name,value,e.getMessage()});
				}
			}
		}
		initialised = true;
	}

	public void clear() {
		if (!initialised)
			super.clear();
		else
			throw new UnsupportedOperationException("Cannot modify this map");
	}

	public Object put(Object key, Object value) {
		if (!initialised)
			return super.put(key, value);
		else
			throw new UnsupportedOperationException("Cannot modify this map");
	}

	public void putAll(Map<?,?> m) {
		if (!initialised)
			super.putAll(m);
		else
			throw new UnsupportedOperationException("Cannot modify this map");
	}

	public Object remove(Object key) {
		if (!initialised)
			return super.remove(key);
		else
			throw new UnsupportedOperationException("Cannot modify this map");
	}
}