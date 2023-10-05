package net.altkey12.wols.text.platform.windows;

import java.util.LinkedHashMap;
import java.util.Map;

import net.altkey12.wols.beans.speedtest.windows.OutputNetshWlanShowInterfaces;
import net.altkey12.wols.beans.speedtest.windows.OutputNetshWlanShowInterfaces.Interface;
import net.altkey12.wols.text.ColonParser;
import net.altkey12.wols.text.StdoutExtractor;
import net.altkey12.wols.text.StringParser;

public class NetshWlanShowInterfacesExtractor implements StdoutExtractor<OutputNetshWlanShowInterfaces> {

	@Override
	public OutputNetshWlanShowInterfaces extract(Map<String, String> out) {
		Map<String, String> clean = new LinkedHashMap<>();
		out.forEach((k,v) -> {
			int pos = k.indexOf(ColonParser.KEY_SEPARATOR_CHAR);
			String key = (pos == -1)?  k : k.substring(pos+1);
			clean.put(key, v);
		});
		
		Interface i = new Interface(
				clean.getOrDefault("Name", UNAVAILABLE), 
				clean.getOrDefault("Description", UNAVAILABLE), 
				StringParser.toUuid(clean.getOrDefault("GUID", "00000000-0000-0000-0000-000000000000")), 
				StringParser.toMacAddress(clean.getOrDefault("Physical address", "00:00:00:00:00:00")), 
				clean.getOrDefault("State", "disconnected").equals("connected"), 
				clean.getOrDefault("SSID", UNAVAILABLE), 
				clean.getOrDefault("BSSID", UNAVAILABLE), 
				clean.getOrDefault("Network type", UNAVAILABLE), 
				clean.getOrDefault("Radio type", UNAVAILABLE), 
				clean.getOrDefault("Authentication", UNAVAILABLE), 
				clean.getOrDefault("Cipher", UNAVAILABLE), 
				clean.getOrDefault("Connection mode", UNAVAILABLE), 
				Integer.parseInt(clean.getOrDefault("Channel", UNAVAILABLE)), 
				Integer.parseInt(clean.getOrDefault("Receive rate (Mbps)", UNAVAILABLE)), 
				Integer.parseInt(clean.getOrDefault("Transmit rate (Mbps)", UNAVAILABLE)), 
				Integer.parseInt(clean.getOrDefault("Signal", "0%").replaceAll("%", "")), 
				clean.getOrDefault("Profile", UNAVAILABLE), 
				clean.getOrDefault("Hosted network status", UNAVAILABLE));
		
		return new OutputNetshWlanShowInterfaces(new Interface[] { i });
	}

}
