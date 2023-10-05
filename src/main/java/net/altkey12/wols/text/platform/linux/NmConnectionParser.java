package net.altkey12.wols.text.platform.linux;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import net.altkey12.wols.text.StdoutParser;

public class NmConnectionParser implements StdoutParser {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(NmConnectionParser.class);
	
	protected enum Section {
		CONNECTION("connection"),
		WIFI("wifi"),
		WIFI_SECURITY("wifi-security"),
		_802_1X("802-1x"),
		IPV4("ipv4"),
		IPV6("ipv6"),
		PROXY("proxy");
		
		String label;
		
		Section(String label){
			this.label = label;
		}
		
		String getLabel() {
			return this.label;
		}
	}

	@Override
	public Map<String, String> parse(String o) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		try(Scanner sc = new Scanner(o)) {
			Section section = Section.CONNECTION;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				
				int equalPos = line.indexOf('=');
				if(		line != null
						&& line.length() >2 
						&& line.charAt(0) == '[' 
						&& line.charAt(line.length()-1) == ']') {
					String sectionName = line.substring(1, line.length()-1);
					for(Section s: Section.values()) {
						if(s.getLabel().equals(sectionName)) {
							section = s;
						}
					}
				}
				else if(equalPos != -1) {
					String key = line.substring(0, equalPos);
					String value = line.substring(equalPos+1, line.length());
					
					if(section == Section._802_1X) {
						switch(key) {
						case "identity":
							map.put("identity", value);
//							LOGGER.trace("*** identity = {}", value);
							break;
						case "password":
							map.put("password", value);
//							LOGGER.trace("*** password = {}", value);
							break;
//						default:
//							LOGGER.trace("Ignoring {} = {}", key, value);
						}
					}
//					else {
//						LOGGER.trace("Ignoring {} = {}", key, value);
//					}
				}
			}
		}
		
		return map;
	}

}
