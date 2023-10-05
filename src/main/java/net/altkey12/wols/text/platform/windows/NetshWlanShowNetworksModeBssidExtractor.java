package net.altkey12.wols.text.platform.windows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.altkey12.wols.beans.speedtest.windows.OutputNetshWlanShowNetworksModeBssid;
import net.altkey12.wols.beans.speedtest.windows.OutputNetshWlanShowNetworksModeBssid.Bssid;
import net.altkey12.wols.beans.speedtest.windows.OutputNetshWlanShowNetworksModeBssid.Network;
import net.altkey12.wols.text.ColonParser;
import net.altkey12.wols.text.StdoutExtractor;

public class NetshWlanShowNetworksModeBssidExtractor implements StdoutExtractor<OutputNetshWlanShowNetworksModeBssid> {

	@Override
	public OutputNetshWlanShowNetworksModeBssid extract(Map<String, String> out) {
		List<Network> networks = new ArrayList<>();
		List<Bssid> bssids = new ArrayList<>();
		
		out.forEach((k, v) -> { 
//			System.out.println("[NetshWlanShowNetworksModeBssidExtractor] " + k + " = " + v);
			if(k.startsWith("SSID") && ColonParser.isTopLevelKey(k)) {
				// To extract out BSSIDs
				int bssidIndex = 1;
				boolean bssidExists = true;
				do {
					String ssidKey = k + ":" + v;
					String bssidId = "BSSID " + Integer.toString(bssidIndex);
					String bssidKey = ColonParser.buildKey(ssidKey, bssidId);
					String bssidMac = out.get(bssidKey);
					
					if(bssidMac != null) {
						bssidKey = bssidKey.concat(":").concat(bssidMac);
						
						String[] basicRatesMbpsStr = out.get(bssidKey + "/Basic rates (Mbps)").split("\\s");
						String[] otherRatesMbpsStr = out.get(bssidKey + "/Other rates (Mbps)").split("\\s");
					
						float[] basicRatesMbps = new float[basicRatesMbpsStr.length];
						for(int r=0; r < basicRatesMbps.length; r++) {
							basicRatesMbps[r] = Float.parseFloat(basicRatesMbpsStr[r]);
						}
						
						float[] otherRatesMbps = new float[otherRatesMbpsStr.length];
						for(int r=0; r < otherRatesMbps.length; r++) {
							otherRatesMbps[r] = Float.parseFloat(otherRatesMbpsStr[r]);
						}
						
						bssids.add(new Bssid(
								bssidMac, 
								Integer.parseInt(out.get(bssidKey + "/Signal").replace("%", "")), 
								out.getOrDefault(bssidKey + "/Radio type", UNAVAILABLE), 
								Integer.parseInt(out.get(bssidKey + "/Channel")), 
								basicRatesMbps, 
								otherRatesMbps));
					}
					
					bssidExists = (bssidMac != null);
					
					bssidIndex++;
				} while(bssidExists);
				
				
				Network n = new Network(
						v, 
						out.get(k + ":" + v + "/Network type"), 
						out.get(k + ":" + v + "/Authentication"), 
						out.get(k + ":" + v + "/Encryption"), 
						bssids.toArray(new Bssid[0])
				);
				networks.add(n);
				bssids.clear();
			}
		});
		
		return new OutputNetshWlanShowNetworksModeBssid(
				out.getOrDefault("Interface name", StdoutExtractor.UNAVAILABLE), 
				networks.toArray(new Network[0]));
	}

}
