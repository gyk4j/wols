package net.altkey12.wols.text;

import java.util.Map;

public interface StdoutExtractor<T> {
	public static final String UNAVAILABLE = "Unavailable";
	
	public T extract(Map<String, String> out);
}
