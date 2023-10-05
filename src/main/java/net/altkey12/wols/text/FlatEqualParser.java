package net.altkey12.wols.text;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class FlatEqualParser implements StdoutParser {

	@Override
	public Map<String, String> parse(String s) {
		Map<String, String> data = new LinkedHashMap<String, String>();
		
		Properties p = new Properties();
		try {
			p.load(new StringReader(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}

}
