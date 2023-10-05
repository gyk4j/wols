package net.altkey12.wols.text.platform.linux;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.altkey12.wols.text.StdoutParser;

public class NmcliDeviceShowParser implements StdoutParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NmcliDeviceShowParser.class); 

	@Override
	public Map<String, String> parse(String s) {
		Map<String, String> m = new LinkedHashMap<>();
		int prefix=-1;
		
		String[] lines = s.split(System.lineSeparator());
		for(int l=0; l < lines.length; l++) {
			String line = lines[l];
			
			// Skip empty line
			if(line.trim().length() == 0) {
				continue;
			}
			
			int colon = line.indexOf(':');
			
			// All lines should be key : value format.
			if(colon == -1){
				LOGGER.warn("Unhandled line: {}", line);
				continue;
			}
			
			String k = line.substring(0, colon).trim();
			String v = line.substring(colon+1, line.length()).trim();
			
			if("GENERAL.DEVICE".equals(k)) {
				prefix++;
			}
			
			// Add prefix to make it unique.
			k = Integer.toString(prefix).concat(".").concat(k);
			
			if(m.putIfAbsent(k, v) != null) {
				LOGGER.error("Duplicate: " + k);
			}
		}
		
		return m;
	}

}
