package net.altkey12.wols.net.platform.windows;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import net.altkey12.wols.text.StdoutExtractor;

public class Wifi {
	public static final Map<Integer, Integer> FREQUENCY = new HashMap<>(); // In kilohertz
	
	static {
		
		// 2.4 GHz
		for (int i = 1; i <= 13; i++) {
			FREQUENCY.put(i, (2412 + ((i - 1) * 5)) * 1000);
		}
		
		// 5 GHz
		for(int i=32; i <=196; i++) {
			FREQUENCY.put(i, (5160 + ((i - 32) * 5)) * 1000);
		}
	}
	
	public static enum NetworkSpeedUnit {
		Bps(1),
		Kbps(1000),
		Mbps(1000000),
		Gbps(1000000000);
		
		private int unitSize;
		
		NetworkSpeedUnit(int unitSize) {
			this.unitSize = unitSize;
		}

		public int getUnitSize() {
			return unitSize;
		}

		public void setUnitSize(int unitSize) {
			this.unitSize = unitSize;
		}
	}
	
	/**
	 * 
	 * @param authentication
	 * @param encryption
	 * @return
	 */
	public static String getNetworkType(String authentication, String encryption) {
		StringBuilder sb = new StringBuilder();
		if(authentication == null) {
			sb.append(StdoutExtractor.UNAVAILABLE);
		}
		else if(authentication.startsWith("WPA2")) {
			sb.append("RSNA");
			
			if(authentication.endsWith("Enterprise")) {
				sb.append("");
			}
			else if(authentication.endsWith("Personal")) {
				sb.append("_PSK");
			}
		}			
		else if(authentication.equals("Open")) {
			sb.append("OPEN");
		}
		
		sb.append(" ");
		
		if(encryption != null) {
			sb.append(encryption.toUpperCase());
		}
		else {
			sb.append(StdoutExtractor.UNAVAILABLE);
		}
		
		return sb.toString();
	}
	
	/**
	 * Convert Microsoft Windows signal quality to RSSI in dBm.
	 * 
	 * @param signal Signal quality in percentage.
	 * @return rssi in dBm
	 */
	public static int getRssiFromSignal(int signal) {
		return (signal / 2) - 100; // in dBm
	}
	
	public static int getChannelFrequency(int channel) {
		Integer frequency = FREQUENCY.get(channel);
		
		if(frequency == null) {
			frequency = -1;
		}
		return frequency;
	}
	
	/**
	 * 
	 * @param radioType
	 * @return
	 */
	public static String getChannelWidth(String radioType) {
		String channelWidth;
		switch (radioType) {
		case "802.11":
		case "802.11a":
		case "802.11b":
		case "802.11g":
			channelWidth = "Twenty";
			break;
		case "802.11n":
		case "802.11ac":
		case "802.11ax":
		case "802.11be":
			channelWidth = "Twenty / TwentyOrForty";
			break;
		default:
			channelWidth = StdoutExtractor.UNAVAILABLE;
			break;
		}
		return channelWidth;
	}
	
	public static String getChannelWidth(boolean _2GHz, boolean _5GHz) {
		return (_5GHz)? "Twenty / TwentyOrForty" : "Twenty";
	}
	
	/**
	 * 
	 * @param size
	 * @param startMs start time in millisec
	 * @param endMs end time in millisec
	 * @param unit bps/kbps/mbps/gbps
	 * @return
	 */
	public static float getNetworkSpeed(long size, long startMs, long endMs, NetworkSpeedUnit unit) {
		Duration duration = Duration.ofMillis(Math.max(endMs - startMs, 100));
		float mbps = (float) (size*8) / (float) (duration.getSeconds() * unit.getUnitSize());
		return mbps;
	}
}
