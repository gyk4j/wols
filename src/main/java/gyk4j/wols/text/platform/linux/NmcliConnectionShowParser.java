package gyk4j.wols.text.platform.linux;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.text.StdoutParser;

public class NmcliConnectionShowParser implements StdoutParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NmcliConnectionShowParser.class);

	@Override
	public Map<String, String> parse(String s) {
		Map<String, String> out = new LinkedHashMap<>();
		
		for(String l: s.split(System.lineSeparator())) {
			int colon = l.indexOf(':');
			
			if(colon == -1) {
				LOGGER.warn("Skipped line = {}", l);
				continue;
			}
			
			String key = l.substring(0, colon).trim();
			String value = l.substring(colon+1).trim();
			
			switch(key) {
				case "connection.id":
				case "connection.uuid":
				case "connection.interface-name":
				case "802-11-wireless.ssid":
				case "802-11-wireless-security.auth-alg":
				case "802-11-wireless-security.key-mgmt":
				case "802-1x.ca-cert":
				case "802-1x.eap":
				case "802-1x.identity":
				case "802-1x.password":
				case "802-1x.phase2-auth":
					out.put(key, value);
					break;
				default:
					LOGGER.warn("Skipped {} = {}", key, value);
					break;
			}
		}
		
		return out;
	}

}
