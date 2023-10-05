package gyk4j.wols.util.platform.dummy;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import gyk4j.wols.beans.speedtest.AmsPingTests;
import gyk4j.wols.beans.speedtest.DeviceInformation;
import gyk4j.wols.beans.speedtest.DiagnosticsInformation;
import gyk4j.wols.beans.speedtest.Exceptions;
import gyk4j.wols.beans.speedtest.GeneralPingTests;
import gyk4j.wols.beans.speedtest.NetworkInformation;
import gyk4j.wols.beans.speedtest.SpeedTests;
import gyk4j.wols.beans.speedtest.WifiInformation;
import gyk4j.wols.beans.speedtest.WifiScan;
import gyk4j.wols.beans.speedtest.WifiScanResults;
import gyk4j.wols.net.NetworkDiagnostics;

public class NetworkDiagnosticsDummy implements NetworkDiagnostics {
	
	@Override
	public DiagnosticsInformation getDiagnosticsInformation() {
		return new DiagnosticsInformation(
				"Full", 
				"Wifi");
	}
	

	@Override
	public DeviceInformation getDeviceInformation() {
		return new DeviceInformation(
				"HP EliteBook 2560p", 
				"Hewlett-Packard", 
				System.getProperty("os.name"), 
				System.getProperty("os.version"), 
				UUID.randomUUID().toString(),
				"3.0.3.1001");
	}
	
	@Override
	public WifiInformation getWifiInformation() {
		return new WifiInformation(
				"Wireless@SGx", 
				"C4:B9:CD:F7:70:C0", 
				-72, 
				2,    // Ghz
				13); // Mbps
	}

	@Override
	public NetworkInformation getNetworkInformation() {
		NetworkInformation networkInformation = null;
		
		byte[] ipv4 = new byte[4];
		try {
			networkInformation = new NetworkInformation(
					InetAddress.getByAddress("129.126.10.4", ipv4), // WAN
					InetAddress.getByAddress("10.194.8.2", ipv4), // LAN
					InetAddress.getByAddress("10.194.8.1", ipv4), // GW
					new InetAddress[] { // DNS
							InetAddress.getByAddress("8.8.8.8", ipv4),
							InetAddress.getByAddress("8.8.4.4", ipv4), 
					}, 
					InetAddress.getByAddress("1.1.1.1", ipv4), // DHCP
					Duration.of(60000, ChronoUnit.SECONDS), // DHCP lease
					new byte[] { (byte) 255, (byte) 255, (byte) 252, (byte) 0 }, // Network Mask
					ipv4);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return networkInformation;
	}

	@Override
	public WifiScanResults getWifiScanResults() {
		return new WifiScanResults(new WifiScan[] {
				new WifiScan(
						1, 
						"Wireless@SGx", 
						"c4b9cdf770c0", 
						"RSNA CCMP / RSNA_PSK CCMP", 
						-68, 
						2462000, 
						11, 
						"Twenty / TwentyOrForty")
		});
	}

	@Override
	public SpeedTests getSpeedTests() {
		return new SpeedTests(0.573f);
	}

	@Override
	public GeneralPingTests getGeneralPingTests() {
		return new GeneralPingTests(149);
	}

	@Override
	public AmsPingTests getAmsPingTests() {
		return new AmsPingTests(
				238, 
				-1, // Error
				8, 
				213);
	}

	@Override
	public Exceptions getExceptions() {
		return new Exceptions(3);
	}

}
