package org.python.lang;

import org.json.JSONObject;

public class Dict {
	protected JSONObject object;
	
	public static Dict from(JSONObject object) {
		Dict newDict = new Dict();
		newDict.setObject(object);
		return newDict;
	}
	
	public static Dict from(Dict dataNode) {
		Dict newDict = new Dict();
		newDict.setObject(dataNode.getObject());
		return newDict;
	}
	
	public boolean contains(String key) {
		return object.has(key);
	}
	
	public Dict get(String key) {
		return (contains(key))? Dict.from(object.getJSONObject(key)): null;
	}
	
	public int getInt(String key) {
		return object.getInt(key);
	}
	
	public String getString(String key) {
		return object.getString(key);
	}

	protected JSONObject getObject() {
		return object;
	}

	protected void setObject(JSONObject object) {
		this.object = object;
	}
}
