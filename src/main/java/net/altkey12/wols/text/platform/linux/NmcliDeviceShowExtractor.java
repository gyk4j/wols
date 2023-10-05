package net.altkey12.wols.text.platform.linux;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.altkey12.wols.beans.speedtest.linux.OutputNmcliDeviceShow;
import net.altkey12.wols.beans.speedtest.linux.OutputNmcliDeviceShow.Device;
import net.altkey12.wols.beans.speedtest.linux.OutputNmcliDeviceShow.Device.Ap;
import net.altkey12.wols.text.StdoutExtractor;
import net.altkey12.wols.text.StringParser;

public class NmcliDeviceShowExtractor implements StdoutExtractor<OutputNmcliDeviceShow> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NmcliDeviceShowExtractor.class);

	private static final Pattern AP = Pattern.compile("^AP\\[[0-9]+\\]$");
	@Override
	public OutputNmcliDeviceShow extract(Map<String, String> out) {
		
		List<Device> devices = new ArrayList<>();
		
		Set<Entry<String, String>> set = out.entrySet();
		
		set.forEach((e) -> {
			String[] ks = e.getKey().split("\\.");
			
			if(ks.length != 3) {
				LOGGER.warn("Malformed key: {}", e.getKey());
				return;
			}
			
			int di = Integer.parseInt(ks[0]);
			
			while(di >= devices.size()) {
				devices.add(new Device());
			}
			
			Device d = devices.get(di);
			
			switch(ks[1]) {
			case "GENERAL":
				fillGeneral(d, ks[2], e.getValue());
				break;
			case "WIFI-PROPERTIES":
				fillWifiProperties(d, ks[2], e.getValue());
			case "IP4":
				fillIp4(d, ks[2], e.getValue());
				break;
			case "DHCP4":
				fillDhcp4(d, ks[2], e.getValue());
				break;
			case "IP6":
				fillIp6(d, ks[2], e.getValue());
				break;
			default:
//				LOGGER.error("???? {}={}", e.getKey(), e.getValue());
			}
			
			if(AP.matcher(ks[1]).matches()) {
				fillAps(d, ks[2], e.getValue());
			}
		});
		
		OutputNmcliDeviceShow data = new OutputNmcliDeviceShow(devices.toArray(new Device[0]));
		return data;
	}
	
	protected void fillGeneral(Device device, String key, String value) {
		switch(key) {
		case "DEVICE":
			device.general.device = value;
//			LOGGER.trace("Found device: {}", device.general.device);
			break;
		case "TYPE":
			device.general.type = value;
			break;
		case "HWADDR":
			device.general.hwaddr = StringParser.toMacAddress(value);
			break;
		case "MTU":
			device.general.mtu = StringParser.toInt(value);
			break;
		case "STATE":
			device.general.state = "100 (connected)".equals(value);
			break;
		case "CONNECTION":
			device.general.connection = value;
			break;
		case "CON-PATH":
			device.general.conPath = value;
			break;
//		default:
//			LOGGER.error("???? {}={}", key, value);
		}
	}
	
	protected void fillWifiProperties(Device device, String key, String value) {
		switch (key) {
		case "2GHZ":
			device.wifiProperties._2GHz = StringParser.toBoolean(value);
			break;
		case "5GHZ":
			device.wifiProperties._5GHz = StringParser.toBoolean(value);
			break;
		}
	}
	
	protected void fillAps(Device device, String key, String value) {
		Ap ap = (device.aps.length > 0) 
				? device.aps[device.aps.length-1]: device.new Ap();
		switch(key) {
		case "IN-USE":
			ap = device.new Ap();
			ap.inUse = value.length() > 0; // "*" if in-use.
			
			// Save the AP.
			Ap[] resized = Arrays.copyOf(device.aps, device.aps.length+1);
			resized[resized.length-1] = ap;
			device.aps = resized;
			break;
		case "BSSID":
			ap.bssid = value;
			break;
		case "SSID":
			ap.ssid = value;
//			LOGGER.trace("Found AP: {}", ap.ssid);
			break;
		case "MODE":
			ap.mode = value; // Expect "Infra". Maybe adhoc?
			break;
		case "CHAN":
			ap.chan = StringParser.toInt(value);
			break;
		case "RATE":
			ap.rate = StringParser.toInt(value.replaceAll(" Mbit/s", ""));
			break;
		case "SIGNAL":
			ap.signal = StringParser.toInt(value);
			break;
		case "BARS":
			ap.bars = value;
			break;
		case "SECURITY":
			ap.security = value.trim();
			break;
		}
	}

	protected void fillIp4(Device device, String key, String value) {
		
		if(key.startsWith("ADDRESS")) {
			InetAddress a = StringParser.toInetAddress(value);
			if(a != null) { // if a valid IP address is found.
				InetAddress[] resized = Arrays.copyOf(device.ip4.address, device.ip4.address.length+1);
				resized[resized.length-1] = a;
				device.ip4.address = resized;
				
				byte[][] masks = Arrays.copyOf(device.ip4.subnetMask, device.ip4.subnetMask.length+1);
				masks[masks.length-1] = StringParser.toSubnetMask(value);
				device.ip4.subnetMask = masks;
			}
		}
		else if(key.equals("GATEWAY")) {
			InetAddress a = StringParser.toInetAddress(value);
			if(a != null) {
				device.ip4.gateway = a;
			}
			else if(!"--".equals(value)){
				LOGGER.warn("Invalid IP4 gateway: {}", value);
			}
		}
		else if(key.startsWith("DNS")) {
			InetAddress a = StringParser.toInetAddress(value);
			if(a != null) { // if a valid IP address is found.
				InetAddress[] resized = Arrays.copyOf(device.ip4.dns, device.ip4.dns.length+1);
				resized[resized.length-1] = a;
				device.ip4.dns = resized;
			}
		}
//		else {
//			LOGGER.trace("???? {}={}", key, value);
//		}
	}
	
	protected void fillDhcp4(Device device, String key, String value) {
		if (value.startsWith("dhcp_server_identifier = ")) {
			InetAddress a = StringParser.toInetAddress(value.substring(value.indexOf('=')+1).trim());
			if(a != null) {
				device.dhcp4.dhcp = a;
			}
			else {
				LOGGER.warn("Invalid DHCP: {}", value);
			}
		}
		else if (value.startsWith("dhcp_lease_time = ")) {
			long a = StringParser.toLong(value.substring(value.indexOf('=')+1).trim());
			if(a != -1) {
				device.dhcp4.dhcpLeaseTime = a;
			}
			else {
				LOGGER.warn("Invalid DHCP lease time: {}", value);
			}
		}
//		else {
//			LOGGER.trace("???? {}={}", key, value);
//		}
	}
	
	protected void fillIp6(Device device, String key, String value) {
		if(key.startsWith("ADDRESS")) {
			InetAddress a = StringParser.toInetAddress(value);
			if(a != null) { // if a valid IP address is found.
				InetAddress[] resized = Arrays.copyOf(device.ip6.address, device.ip6.address.length+1);
				resized[resized.length-1] = a;
				device.ip6.address = resized;
			}
		}
		else if(key.equals("GATEWAY")) {
			InetAddress a = StringParser.toInetAddress(value);
			if(a != null) {
				device.ip6.gateway = a;
			}
			else if (!"--".equals(value)){
				LOGGER.warn("Invalid IP6 gateway: {}", value);
			}
		}
//		else {
//			LOGGER.trace("???? {}={}", key, value);
//		}
	}
}
