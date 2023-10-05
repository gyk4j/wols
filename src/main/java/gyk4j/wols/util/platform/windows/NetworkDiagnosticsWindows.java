package gyk4j.wols.util.platform.windows;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import gyk4j.wols.beans.speedtest.AmsPingTests;
import gyk4j.wols.beans.speedtest.DeviceInformation;
import gyk4j.wols.beans.speedtest.GeneralPingTests;
import gyk4j.wols.beans.speedtest.NetworkInformation;
import gyk4j.wols.beans.speedtest.WifiInformation;
import gyk4j.wols.beans.speedtest.WifiScan;
import gyk4j.wols.beans.speedtest.WifiScanResults;
import gyk4j.wols.beans.speedtest.windows.OutputIpconfigAll;
import gyk4j.wols.beans.speedtest.windows.OutputNetshWlanShowInterfaces;
import gyk4j.wols.beans.speedtest.windows.OutputNetshWlanShowNetworksModeBssid;
import gyk4j.wols.beans.speedtest.windows.OutputPing;
import gyk4j.wols.beans.speedtest.windows.OutputWmicComputerSystemGetManufacturerModel;
import gyk4j.wols.beans.speedtest.windows.OutputWmicOsGetCaptionBuildNumberVersionSerialNumber;
import gyk4j.wols.beans.speedtest.windows.OutputIpconfigAll.Connection;
import gyk4j.wols.beans.speedtest.windows.OutputNetshWlanShowInterfaces.Interface;
import gyk4j.wols.beans.speedtest.windows.OutputNetshWlanShowNetworksModeBssid.Bssid;
import gyk4j.wols.beans.speedtest.windows.OutputNetshWlanShowNetworksModeBssid.Network;
import gyk4j.wols.net.AbstractNetworkDiagnostics;
import gyk4j.wols.net.platform.windows.Wifi;
import gyk4j.wols.text.ColonParser;
import gyk4j.wols.text.StdoutExtractor;
import gyk4j.wols.text.TableParser;
import gyk4j.wols.text.platform.windows.IpconfigAllExtractor;
import gyk4j.wols.text.platform.windows.NetshWlanShowInterfacesExtractor;
import gyk4j.wols.text.platform.windows.NetshWlanShowNetworksModeBssidExtractor;
import gyk4j.wols.text.platform.windows.PingExtractor;
import gyk4j.wols.text.platform.windows.WmicComputerSystemGetManufacturerModelExtractor;
import gyk4j.wols.text.platform.windows.WmicOsGetCaptionBuildNumberVersionExtractor;
import gyk4j.wols.util.CommandLineTool;

public class NetworkDiagnosticsWindows extends AbstractNetworkDiagnostics {
	protected CommandLineTool<OutputWmicComputerSystemGetManufacturerModel> cmdWmicComputerSystemGetManufacturerModel;
	protected CommandLineTool<OutputWmicOsGetCaptionBuildNumberVersionSerialNumber> cmdWmicOsGetCaptionBuildNumberVersionSerialNumber;
	
	protected CommandLineTool<OutputIpconfigAll> cmdIpconfigAll;
	protected CommandLineTool<OutputNetshWlanShowInterfaces> cmdNetshWlanShowInterfaces;
	protected CommandLineTool<OutputNetshWlanShowNetworksModeBssid> cmdNetshWlanShowNetworksModeBssid;
	protected CommandLineTool<OutputPing> cmdPing;
	
	protected CommandLineTool<OutputPing> cmdAmsPingM1;
	protected CommandLineTool<OutputPing> cmdAmsPingSingtel;
	protected CommandLineTool<OutputPing> cmdAmsPingStarhub;
	protected CommandLineTool<OutputPing> cmdAmsPingSimba;
	
	protected OutputWmicComputerSystemGetManufacturerModel wmicComputerSystemGetManufacturerModel;
	protected OutputWmicOsGetCaptionBuildNumberVersionSerialNumber wmicOsGetCaptionBuildNumberVersionSerialNumber;
	protected OutputIpconfigAll ipconfigAll;
	protected OutputNetshWlanShowInterfaces netshWlanShowInterfaces;
	protected OutputNetshWlanShowNetworksModeBssid netshWlanShowNetworksModeBssid;
	protected OutputPing generalPing;
	
	protected OutputPing amsPingM1;
	protected OutputPing amsPingSingtel;
	protected OutputPing amsPingStarhub;
	protected OutputPing amsPingSimba;
	
	public NetworkDiagnosticsWindows() {
		super();
		
		cmdWmicComputerSystemGetManufacturerModel = 
				new CommandLineTool<OutputWmicComputerSystemGetManufacturerModel>(
						new String[] {"wmic", "computersystem", "get", "Manufacturer,Model"},
						new TableParser(),
						new WmicComputerSystemGetManufacturerModelExtractor());
		
		cmdWmicOsGetCaptionBuildNumberVersionSerialNumber = 
				new CommandLineTool<OutputWmicOsGetCaptionBuildNumberVersionSerialNumber>(
						new String[] {"wmic", "os", "get", "Caption,BuildNumber,Version,SerialNumber"},
						new TableParser(),
						new WmicOsGetCaptionBuildNumberVersionExtractor());		
		
		cmdIpconfigAll = 
				new CommandLineTool<OutputIpconfigAll>(
						new String[] {"ipconfig", "/all"},
						new ColonParser(),
						new IpconfigAllExtractor());
		
		cmdNetshWlanShowInterfaces = 
				new CommandLineTool<OutputNetshWlanShowInterfaces>(
						new String[] {"netsh", "wlan", "show", "interfaces"},
						new ColonParser(),
						new NetshWlanShowInterfacesExtractor());
		
		cmdNetshWlanShowNetworksModeBssid = 
				new CommandLineTool<OutputNetshWlanShowNetworksModeBssid>(
						new String[] {"netsh", "wlan", "show", "networks", "mode=bssid"},
						new ColonParser(),
						new NetshWlanShowNetworksModeBssidExtractor());
		
		cmdPing = 
				new CommandLineTool<OutputPing>(
						new String[] {"ping", "-n", "2", "-w", "1000", "google.com"},
						new ColonParser(),
						new PingExtractor());
		
		cmdAmsPingM1 = 
				new CommandLineTool<OutputPing>(
						new String[] {"ping", "-n", "2", "-w", "1000", "www.m1.com.sg"},
						new ColonParser(),
						new PingExtractor());
		
		cmdAmsPingSingtel = 
				new CommandLineTool<OutputPing>(
						new String[] {"ping", "-n", "2", "-w", "1000", "www.singtel.com"},
						new ColonParser(),
						new PingExtractor());
		
		cmdAmsPingStarhub = 
				new CommandLineTool<OutputPing>(
						new String[] {"ping", "-n", "2", "-w", "1000", "www.starhub.com"},
						new ColonParser(),
						new PingExtractor());
		
		cmdAmsPingSimba = 
				new CommandLineTool<OutputPing>(
						new String[] {"ping", "-n", "2", "-w", "1000", "simba.sg"},
						new ColonParser(),
						new PingExtractor());
		
		initialize();
	}
	
	private void initialize() {		
		wmicComputerSystemGetManufacturerModel = executeSafely(cmdWmicComputerSystemGetManufacturerModel);
		wmicOsGetCaptionBuildNumberVersionSerialNumber = executeSafely(cmdWmicOsGetCaptionBuildNumberVersionSerialNumber);
		ipconfigAll = executeSafely(cmdIpconfigAll);
		netshWlanShowInterfaces = executeSafely(cmdNetshWlanShowInterfaces);
		netshWlanShowNetworksModeBssid = executeSafely(cmdNetshWlanShowNetworksModeBssid);
		generalPing = executeSafely(cmdPing);
		amsPingM1 = executeSafely(cmdAmsPingM1);
		amsPingSingtel = executeSafely(cmdAmsPingSingtel);
		amsPingStarhub = executeSafely(cmdAmsPingStarhub);
		amsPingSimba = executeSafely(cmdAmsPingSimba);
	}

	@Override
	public DeviceInformation getDeviceInformation() {
		
		String model, manufacturer, caption, version, serialNumber, buildNumber;
		
		if(wmicComputerSystemGetManufacturerModel != null) {
			model = wmicComputerSystemGetManufacturerModel.getModel();
			manufacturer = wmicComputerSystemGetManufacturerModel.getManufacturer();
		}
		else {
			model = StdoutExtractor.UNAVAILABLE;
			manufacturer = StdoutExtractor.UNAVAILABLE;
		}
		
		if(wmicOsGetCaptionBuildNumberVersionSerialNumber != null) {
			caption = wmicOsGetCaptionBuildNumberVersionSerialNumber.getCaption();
			version = wmicOsGetCaptionBuildNumberVersionSerialNumber.getVersion();
			serialNumber = wmicOsGetCaptionBuildNumberVersionSerialNumber.getSerialNumber();
			buildNumber = wmicOsGetCaptionBuildNumberVersionSerialNumber.getBuildNumber();
		}
		else {
			caption = StdoutExtractor.UNAVAILABLE;
			version = StdoutExtractor.UNAVAILABLE;
			serialNumber = StdoutExtractor.UNAVAILABLE;
			buildNumber = StdoutExtractor.UNAVAILABLE;
		}
		
		return new DeviceInformation(
				model, 
				manufacturer,
				caption, 
				version, 
				serialNumber, 
				buildNumber);
	}

	@Override
	public WifiInformation getWifiInformation() {
		Interface[] interfaces = netshWlanShowInterfaces.getInterfaces();
		Interface wlan0 = null;
		
		// Use the Wifi interface with connection
		for(Interface i: interfaces) {
			if(i.getState()) {
				wlan0 = i;
				break;
			}
		}
		
		// No active connection. 
		// Just use any disconnected wifi interface found.
		// Usually only 1 interface. So just assume first one.
		if(interfaces != null && interfaces.length > 0 && wlan0 == null) {
			wlan0 = interfaces[0];
		}
		
		return new WifiInformation(
				(wlan0 != null)? wlan0.getSsid(): StdoutExtractor.UNAVAILABLE, 
				(wlan0 != null)? wlan0.getBssid(): StdoutExtractor.UNAVAILABLE, 
				Wifi.getRssiFromSignal((wlan0 != null)? wlan0.getSignal(): 0), 
				Wifi.getChannelFrequency((wlan0 != null)? wlan0.getChannel(): 0), 
				(wlan0 != null)? wlan0.getReceiveRateMbps() : -1);
	}

	@Override
	public NetworkInformation getNetworkInformation() {		
		Connection connected = null;
		Connection[] cs = ipconfigAll.getConnections();
		for(Connection c: cs) {
			if(c.isMediaState()) {
				connected = c;
				break;
			}
		}
		
		Duration dhcpLeaseTime = Duration.ZERO;
		
		if (connected != null && connected.getLeaseObtained() != null && connected.getLeaseExpires() != null) {
			dhcpLeaseTime = Duration.between(connected.getLeaseObtained(), connected.getLeaseExpires());
		}
		
		return new NetworkInformation(
				(wanIp != null)? wanIp.getWanIp() : null, 
				(connected != null)? connected.getIpv4Address() : null, 
				(connected != null)? connected.getDefaultGateway() : null, 
				(connected != null)? connected.getDnsServers() : null,
				(connected != null)? connected.getDhcpServer() : null, 
				dhcpLeaseTime, 
				(connected != null)? connected.getSubnetMask() : null, 
				(connected != null)? connected.getPhysicalAddress() : null);
	}

	@Override
	public WifiScanResults getWifiScanResults() {
		List<WifiScan> results = new ArrayList<>();
		Network[] networks = (netshWlanShowNetworksModeBssid != null)?
				netshWlanShowNetworksModeBssid.getNetworks(): new Network[0];
		
		for(int i=0; i < networks.length; i++) {
			Network network = networks[i];
			
			if(network.getSsid().isEmpty()) {
				network.setSsid(StdoutExtractor.UNAVAILABLE);
			}
			
			Bssid bssid = (network.getBssids().length > 0)? 
					network.getBssids()[0]: 
						new Bssid(
								StdoutExtractor.UNAVAILABLE, 
								0, 
								StdoutExtractor.UNAVAILABLE, 
								0, 
								new float[] { 0f }, 
								new float[] { 0f });
			
			String networkType = Wifi.getNetworkType(
					network.getAuthentication(), 
					network.getEncryption());
			
			int rssi = Wifi.getRssiFromSignal(bssid.getSignal());
			
			int channelFreq = Wifi.getChannelFrequency(bssid.getChannel());
			
			String channelWidth = Wifi.getChannelWidth(bssid.getRadioType());
			
			WifiScan s = new WifiScan(
					i, 
					network.getSsid(), 
					bssid.getBssid(), 
					networkType,
					rssi,
					channelFreq, 
					bssid.getChannel(), 
					channelWidth);
			results.add(s);
		}
		
		return new WifiScanResults(results.toArray(new WifiScan[0]));
	}

	@Override
	public GeneralPingTests getGeneralPingTests() {
		int avgRoundTripTime = (generalPing != null)? 
				generalPing.getApproximateRoundTripTimes().getAverage(): -1;
		return new GeneralPingTests(avgRoundTripTime);
	}

	@Override
	public AmsPingTests getAmsPingTests() {
		int m1 = (amsPingM1 != null)? 
				amsPingM1.getApproximateRoundTripTimes().getAverage(): -1;
		int singtel = (amsPingSingtel != null)? 
				amsPingSingtel.getApproximateRoundTripTimes().getAverage(): -1;
		int starhub = (amsPingStarhub != null)? 
				amsPingStarhub.getApproximateRoundTripTimes().getAverage(): -1;
		int simba = (amsPingSimba != null)? 
				amsPingSimba.getApproximateRoundTripTimes().getAverage(): -1;
		
		return new AmsPingTests(
				m1, 
				singtel, 
				starhub, 
				simba);
	}

}
