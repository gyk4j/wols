package gyk4j.wols.util.platform.linux;

import static org.python.lang.global.ISP_CONFIG;

import java.time.Duration;

import org.python.lang.global.Isp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.beans.speedtest.AmsPingTests;
import gyk4j.wols.beans.speedtest.DeviceInformation;
import gyk4j.wols.beans.speedtest.GeneralPingTests;
import gyk4j.wols.beans.speedtest.NetworkInformation;
import gyk4j.wols.beans.speedtest.WifiInformation;
import gyk4j.wols.beans.speedtest.WifiScan;
import gyk4j.wols.beans.speedtest.WifiScanResults;
import gyk4j.wols.beans.speedtest.linux.OutputDeviceInfoTool;
import gyk4j.wols.beans.speedtest.linux.OutputDigWanPublicIp;
import gyk4j.wols.beans.speedtest.linux.OutputNmcliDeviceShow;
import gyk4j.wols.beans.speedtest.linux.OutputPing;
import gyk4j.wols.beans.speedtest.linux.OutputNmcliDeviceShow.Device;
import gyk4j.wols.beans.speedtest.linux.OutputNmcliDeviceShow.Device.Ap;
import gyk4j.wols.net.AbstractNetworkDiagnostics;
import gyk4j.wols.net.platform.windows.Wifi;
import gyk4j.wols.text.platform.linux.DigWanPublicIpExtractor;
import gyk4j.wols.text.platform.linux.DigWanPublicIpParser;
import gyk4j.wols.text.platform.linux.NmcliDeviceShowExtractor;
import gyk4j.wols.text.platform.linux.NmcliDeviceShowParser;
import gyk4j.wols.text.platform.linux.PingExtractor;
import gyk4j.wols.text.platform.linux.PingParser;
import gyk4j.wols.util.CommandLineTool;

public class NetworkDiagnosticsLinux extends AbstractNetworkDiagnostics {
	private static Logger LOGGER = LoggerFactory.getLogger(NetworkDiagnosticsLinux.class);
	
	protected DeviceInfoTool cmdDeviceInfoTool;
	protected CommandLineTool<OutputNmcliDeviceShow> cmdNmcliDeviceShow;
	protected CommandLineTool<OutputDigWanPublicIp> cmdDigWanPublicIp;
	
	protected CommandLineTool<OutputPing> cmdPing;
	protected CommandLineTool<OutputPing> cmdAmsPingM1;
	protected CommandLineTool<OutputPing> cmdAmsPingSingtel;
	protected CommandLineTool<OutputPing> cmdAmsPingStarhub;
	protected CommandLineTool<OutputPing> cmdAmsPingSimba;
	
	protected OutputDeviceInfoTool deviceInfo;
	protected OutputNmcliDeviceShow nmcliDeviceShow;
	protected OutputDigWanPublicIp digWanPublicIp;
	protected OutputPing generalPing;
	
	protected OutputPing amsPingM1;
	protected OutputPing amsPingSingtel;
	protected OutputPing amsPingStarhub;
	protected OutputPing amsPingSimba;
	
	public NetworkDiagnosticsLinux() {
		super();
		
		cmdDeviceInfoTool =
				new DeviceInfoTool();
		
		cmdNmcliDeviceShow =
				new CommandLineTool<OutputNmcliDeviceShow>(
						new String[] { "nmcli", "-c", "no", "-f", "all", "device", "show" },
						new NmcliDeviceShowParser(),
						new NmcliDeviceShowExtractor());
		
		cmdDigWanPublicIp = 
				new CommandLineTool<OutputDigWanPublicIp>(
						new String[] { "dig", "TXT", "+short", "o-o.myaddr.l.google.com", "@ns1.google.com" },
						new DigWanPublicIpParser(),
						new DigWanPublicIpExtractor());
		
		cmdPing = 
				new CommandLineTool<OutputPing>(
						new String[] {"ping", "-c", "2", "-W", "1", "google.com"},
						new PingParser(),
						new PingExtractor());
		
		cmdAmsPingM1 = 
				new CommandLineTool<OutputPing>(
						new String[] {"ping", "-c", "2", "-W", "1", getHost(ISP_CONFIG.get("m1")) },
						new PingParser(),
						new PingExtractor());
		
		cmdAmsPingSingtel = 
				new CommandLineTool<OutputPing>(
						new String[] {"ping", "-c", "2", "-W", "1", getHost(ISP_CONFIG.get("singtel"))},
						new PingParser(),
						new PingExtractor());
		
		cmdAmsPingStarhub = 
				new CommandLineTool<OutputPing>(
						new String[] {"ping", "-c", "2", "-W", "1", getHost(ISP_CONFIG.get("starhub"))},
						new PingParser(),
						new PingExtractor());
		
		cmdAmsPingSimba = 
				new CommandLineTool<OutputPing>(
						new String[] {"ping", "-c", "2", "-W", "1", getHost(ISP_CONFIG.get("simba"))},
						new PingParser(),
						new PingExtractor());
		
		initialize();
	}
	
	private String getHost(Isp isp) {
		String host = "localhost";
		/*
		String url = isp.essaUrl;
		if(url != null && url.length() > 0) {
			try {
				URL parsed = new URL(url);
				host = parsed.getHost();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		*/
		return host;
	}
	
	private void initialize() {
		deviceInfo = executeSafely(cmdDeviceInfoTool);
		nmcliDeviceShow = executeSafely(cmdNmcliDeviceShow);
		digWanPublicIp = executeSafely(cmdDigWanPublicIp);
		generalPing = executeSafely(cmdPing);
		amsPingM1 = executeSafely(cmdAmsPingM1);
		amsPingSingtel = executeSafely(cmdAmsPingSingtel);
		amsPingStarhub = executeSafely(cmdAmsPingStarhub);
		amsPingSimba = executeSafely(cmdAmsPingSimba);
	}

	@Override
	public DeviceInformation getDeviceInformation() {
		return new DeviceInformation(
				deviceInfo.getProductName(), // device model 
				deviceInfo.getSysVendor(), // device make
				deviceInfo.getCaption(), // os 
				deviceInfo.getVersion(), // os version
				deviceInfo.getSerialNumber(), // application ID 
				deviceInfo.getBuildNumber());// application version
	}

	@Override
	public WifiInformation getWifiInformation() {
		Ap ap = null;
		for(Device device : nmcliDeviceShow.getDevices()) {
			// Search for wireless adapter that is connected.
			if("wifi".equals(device.general.type) && device.general.state) {
				for(Ap a : device.aps) {
					if(a.inUse) {
						ap = a;
					}
				}
			}
		}
		
		return new WifiInformation(
				ap.ssid,							// ssid
				ap.bssid,							// bssid
				Wifi.getRssiFromSignal(ap.signal),	// rssi
				Wifi.getChannelFrequency(ap.chan),	// channel frequency
				ap.rate);							// radio link speed
	}

	@Override
	public NetworkInformation getNetworkInformation() {
		Device d = null;
		for(Device device : nmcliDeviceShow.getDevices()) {
			// Search for wireless adapter that is connected.
			if("wifi".equals(device.general.type) && device.general.state) {
				d = device;
			}
		}
		
		LOGGER.trace("WAN IP: {}", wanIp.getWanIp());
		LOGGER.trace("WAN IP: {}", digWanPublicIp.getWanIp());
		
		return new NetworkInformation(
				digWanPublicIp.getWanIp(), 									// wan IP
				(d.ip4.address.length > 0) ? d.ip4.address[0] : null,		// lan IP
				d.ip4.gateway, 												// gateway IP
				d.ip4.dns, 													// dns servers
				d.dhcp4.dhcp, 												// dhcp servers
				Duration.ofSeconds(d.dhcp4.dhcpLeaseTime), 					// dhcp lease time
				(d.ip4.subnetMask.length > 0) ? d.ip4.subnetMask[0] : null, // network mask
				d.general.hwaddr  											// mac address
				);
	}

	@Override
	public WifiScanResults getWifiScanResults() {
		Device d = null;
		for(Device device : nmcliDeviceShow.getDevices()) {
			// Search for wireless adapter that is connected.
			if("wifi".equals(device.general.type) && device.general.state) {
				d = device;
			}
		}
		
		WifiScan[] results = new WifiScan[d.aps.length];
		for(int a=0; a < d.aps.length; a++) {
			Ap ap = d.aps[a];
			
			results[a] = new WifiScan(
					a+1,								// id
					ap.ssid,							// ssid
					ap.bssid,							// bssid
					ap.mode,							// network type Infra
					Wifi.getRssiFromSignal(ap.signal),	// rssi
					Wifi.getChannelFrequency(ap.chan),	// channel frequency
					ap.chan,							// channel number
					Wifi.getChannelWidth(
							d.wifiProperties._2GHz, 
							d.wifiProperties._5GHz));	// channel width (Twenty / TwentyOrFourty
		}
		
		return new WifiScanResults(results);
	}

	@Override
	public GeneralPingTests getGeneralPingTests() {
		float avgRoundTripTime = (generalPing != null)? 
				generalPing.getApproximateRoundTripTimes().getAverage(): -1;
		return new GeneralPingTests(Math.round(avgRoundTripTime));
	}

	@Override
	public AmsPingTests getAmsPingTests() {
		int m1 = (amsPingM1 != null)? 
				Math.round(amsPingM1.getApproximateRoundTripTimes().getAverage()): -1;
		int singtel = (amsPingSingtel != null)? 
				Math.round(amsPingSingtel.getApproximateRoundTripTimes().getAverage()): -1;
		int starhub = (amsPingStarhub != null)? 
				Math.round(amsPingStarhub.getApproximateRoundTripTimes().getAverage()): -1;
		int simba = (amsPingSimba != null)? 
				Math.round(amsPingSimba.getApproximateRoundTripTimes().getAverage()): -1;
		
		return new AmsPingTests(
				m1, 
				singtel, 
				starhub, 
				simba);
	}
}
