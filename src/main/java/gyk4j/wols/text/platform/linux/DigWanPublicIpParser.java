package gyk4j.wols.text.platform.linux;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.text.StdoutExtractor;
import gyk4j.wols.text.StdoutParser;

public class DigWanPublicIpParser implements StdoutParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DigWanPublicIpParser.class);

	@Override
	public Map<String, String> parse(String s) {
		Map<String, String> out = new LinkedHashMap<>();
		
		if(s != null) {
			out.put("ip", s.replace('"', ' ').trim());
			
		}
		
		LOGGER.trace("ip = {}", out.getOrDefault("ip", StdoutExtractor.UNAVAILABLE));
		
		return out;
	}

}
