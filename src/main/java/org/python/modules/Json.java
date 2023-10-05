package org.python.modules;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.json.JSONObject;

public class Json {
	/**
	 * Parse string into JSON
	 * @return
	 */
	public static JSONObject loads(String json) {
		return new JSONObject(json);
	}
	
	/**
	 * Convert dict into JSON
	 * @return
	 */
	public static JSONObject dumps(Properties properties) {
		JSONObject jo = new JSONObject();
		
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		Iterator<Entry<Object, Object>> iter = entrySet.iterator();
		while(iter.hasNext()) {
			Entry<Object, Object> entry = iter.next();
			jo.put((String)entry.getKey(), entry.getValue());
		}
		
		return jo;
	}
}
