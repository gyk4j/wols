package gyk4j.wols.text.platform.linux;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.text.StdoutParser;

public class NmcliConnectionDeleteParser implements StdoutParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NmcliConnectionDeleteParser.class);
	
	@Override
	public Map<String, String> parse(String s) {
		LOGGER.trace("s = {}", s);
		Map<String, String> out = new LinkedHashMap<>();
		
		Matcher m = Pattern.compile("^Connection '(.+)' \\(([0-9a-f\\-]+)\\) successfully deleted.$").matcher(s.trim());
		boolean matches = m.matches();
		int groupCount = m.groupCount();

		LOGGER.trace("matches = {}, groupCount = {}", matches, groupCount);

		if(matches && groupCount == 2) {
			String id = m.group(1);
			String uuid = m.group(2);
			out.put("connection.id", id);
			out.put("connection.uuid", uuid);
			
			LOGGER.trace("connection.id: {}", id);
			LOGGER.trace("connection.uuid: {}", uuid);
		}
		else {
			LOGGER.warn("Mismatched regex: {}", s);
		}
		m.reset();
		m = null;
		
		return out;
	}

}
