package net.altkey12.wols.net;

import java.net.InetAddress;
import java.time.Duration;
import java.util.ArrayList;

import net.altkey12.wols.beans.speedtest.AmsPingTests;
import net.altkey12.wols.beans.speedtest.DeviceInformation;
import net.altkey12.wols.beans.speedtest.DiagnosticsInformation;
import net.altkey12.wols.beans.speedtest.Exceptions;
import net.altkey12.wols.beans.speedtest.GeneralPingTests;
import net.altkey12.wols.beans.speedtest.NetworkInformation;
import net.altkey12.wols.beans.speedtest.SpeedTests;
import net.altkey12.wols.beans.speedtest.WifiInformation;
import net.altkey12.wols.beans.speedtest.WifiScan;
import net.altkey12.wols.beans.speedtest.WifiScanResults;
import net.altkey12.wols.text.StdoutExtractor;
import net.altkey12.wols.text.ValueFormatter;
import net.altkey12.wols.text.ValueFormatter.Padding;
import net.altkey12.wols.text.ValueFormatter.Radix;

public class DiagnosticsMap extends ArrayList<DiagnosticsMapKeyValue> {

	private static final long serialVersionUID = 1L;
	
	private DiagnosticsMap() {
		super();
	}
	
	private void put(String key, String value) {
		add(new DiagnosticsMapKeyValue(key, value));
	}

	public static DiagnosticsMap from(DiagnosticsInformation d) {
		DiagnosticsMap map = new DiagnosticsMap();
		if (d != null) {
			map.put("Diagnostics Mode", d.getDiagnosticsMode());
			map.put("Connectivity Mode", d.getConnectivityMode());
		}
		return map;
	}

	public static DiagnosticsMap from(DeviceInformation d) {
		DiagnosticsMap map = new DiagnosticsMap();
		if (d != null) {
			map.put("Device Model", d.getDeviceModel());
			map.put("Device Make", d.getDeviceMake());
			map.put("Device OS", d.getDeviceOs());
			map.put("Device OS Version", d.getDeviceOsVersion());
			map.put("Application ID", d.getApplicationId());
			map.put("Application Version", d.getApplicationVersion());
		}
		
		return map;
	}
	
	public static DiagnosticsMap from(WifiInformation d) {
		DiagnosticsMap map = new DiagnosticsMap();
		if (d != null) {
			map.put("Service Set Identifier (SSID)", d.getSsid());
			map.put("Basic Service Set Identifier (BSSID)", d.getBssid());
			map.put("Received Signal Strength Indicator (RSSI)", Integer.toString(d.getRssi()).concat(" dBm"));
			map.put("Channel Frequency", String.format("%.3f", ((float) d.getChannelFrequency()) / 1000000).concat(" GHz"));
			map.put("Radio Link Speed", Integer.toString(d.getRadioLinkSpeed()).concat(" Mbps"));
		}
		
		return map;
	}
	
	public static DiagnosticsMap from(NetworkInformation d) {
		DiagnosticsMap map = new DiagnosticsMap();
		if (d != null) {
			map.put("WAN IP", ValueFormatter.from(d.getWanIp()));
			map.put("LAN IP", ValueFormatter.from(d.getLanIp()));
			map.put("Gateway IP", ValueFormatter.from(d.getGatewayIp()));

			String dnsServers = StdoutExtractor.UNAVAILABLE;
			if (d.getDnsServers() != null) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < d.getDnsServers().length; i++) {
					InetAddress s = d.getDnsServers()[i];

					if (i > 0) {
						sb.append(", ");
					}

					sb.append(ValueFormatter.from(s));
				}
				dnsServers = sb.toString();
			}
			
			map.put("DNS Server(s)", dnsServers);
			map.put("DHCP server", ValueFormatter.from(d.getDhcpServer()));

			Duration l = d.getDhcpLeaseTime();
			
			if(l == null)
				l = Duration.ZERO;
			
			String dhcpLeaseTime = String.format(
					"%d h %d m %d s", 
					l.toHours(), 
					l.toMinutes() % 60,
					l.getSeconds() % 60);

			map.put("DHCP lease time", dhcpLeaseTime);
			map.put("Network Mask", ValueFormatter.from(d.getNetworkMask(), Radix.Decimal, Padding.None, "."));
			map.put("MAC Address", ValueFormatter.from(d.getMacAddress(), Radix.Hexadecimal, Padding.Zero));
		}
		
		return map;
	}
	
	public static DiagnosticsMap from(WifiScanResults d) {
		DiagnosticsMap map = new DiagnosticsMap();
		
		if (d != null) {
			WifiScan[] scans = d.getResults();
			for (WifiScan s : scans) {
				String id = Integer.toString(s.getId());
				map.put("Wifi Scan", id);
				map.put("Wifi SSID", s.getSsid());
				String bssid = (s.getBssid() != null)? s.getBssid().replaceAll(":", ""): StdoutExtractor.UNAVAILABLE;
				map.put("Wifi BSSID", bssid);
				map.put("Wifi Network Type", s.getNetworkType());
				map.put("Wifi RSSI", Integer.toString(s.getRssi()).concat(" dBm"));
				map.put("Wifi Channel Freq", String.format("%,d", s.getChannelFreq()).concat(" Hz"));
				map.put("Wifi Channel Number", Integer.toString(s.getChannelNumber()));
				map.put("Wifi Channel Width", s.getChannelWidth());
				map.put("", "");
			}
		}
		
		return map;
	}
	
	public static DiagnosticsMap from(SpeedTests d) {
		DiagnosticsMap map = new DiagnosticsMap();
		
		if (d != null) {
			map.put("Download Speed Test", String.format("%.2f", d.getDownloadSpeedTest()).concat(" Mbps"));
		}
		
		return map;
	}
	
	public static DiagnosticsMap from(GeneralPingTests d) {
		DiagnosticsMap map = new DiagnosticsMap();
		
		if (d != null) {
			String value;
			value = (d.getPingTest() != -1) ? Integer.toString(d.getPingTest()).concat(" ms") : "Error";
			map.put("Ping Test", value);
		}
		
		return map;
	}
	
	public static DiagnosticsMap from(AmsPingTests d) {
		DiagnosticsMap map = new DiagnosticsMap();
		
		if (d != null) {
			String value;
			value = (d.getM1() != -1) ? Integer.toString(d.getM1()).concat(" ms") : "Error";
			map.put("M1", value);

			value = (d.getSingtel() != -1) ? Integer.toString(d.getSingtel()).concat(" ms") : "Error";
			map.put("Singtel", value);

			value = (d.getStarhub() != -1) ? Integer.toString(d.getStarhub()).concat(" ms") : "Error";
			map.put("StarHub", value);

			value = (d.getSimba() != -1) ? Integer.toString(d.getSimba()).concat(" ms") : "Error";
			map.put("SIMBA", value);
		}
		
		return map;
	}
	
	public static DiagnosticsMap from(Exceptions d) {
		DiagnosticsMap map = new DiagnosticsMap();
		
		if (d != null) {
			map.put(null, Integer.toString(d.getCount()));
		}
		
		return map;
	}
}
