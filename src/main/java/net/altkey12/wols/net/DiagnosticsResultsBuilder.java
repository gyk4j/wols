package net.altkey12.wols.net;

import net.altkey12.wols.beans.speedtest.AmsPingTests;
import net.altkey12.wols.beans.speedtest.DeviceInformation;
import net.altkey12.wols.beans.speedtest.DiagnosticsInformation;
import net.altkey12.wols.beans.speedtest.DiagnosticsResults;
import net.altkey12.wols.beans.speedtest.Exceptions;
import net.altkey12.wols.beans.speedtest.GeneralPingTests;
import net.altkey12.wols.beans.speedtest.NetworkInformation;
import net.altkey12.wols.beans.speedtest.SpeedTests;
import net.altkey12.wols.beans.speedtest.WifiInformation;
import net.altkey12.wols.beans.speedtest.WifiScanResults;

public class DiagnosticsResultsBuilder {
	protected DiagnosticsResults diagnosticsResults;
	
	public DiagnosticsResultsBuilder() {
		this.diagnosticsResults = new DiagnosticsResults();
	}
	
	public DiagnosticsResultsBuilder diagnosticsInformation(DiagnosticsInformation diagnosticsInformation) {
		diagnosticsResults.setDiagnosticsInformation(diagnosticsInformation);
		return this;
	}
	
	public DiagnosticsResultsBuilder deviceInformation(DeviceInformation deviceInformation) {
		diagnosticsResults.setDeviceInformation(deviceInformation);
		return this;
	}
	
	public DiagnosticsResultsBuilder wifiInformation(WifiInformation wifiInformation) {
		diagnosticsResults.setWifiInformation(wifiInformation);
		return this;
	}
	
	public DiagnosticsResultsBuilder networkInformation(NetworkInformation networkInformation) {
		diagnosticsResults.setNetworkInformation(networkInformation);
		return this;
	}
	
	public DiagnosticsResultsBuilder wifiScanResults(WifiScanResults wifiScanResults) {
		diagnosticsResults.setWifiScanResults(wifiScanResults);
		return this;
	}
	
	public DiagnosticsResultsBuilder speedTests(SpeedTests speedTests) {
		diagnosticsResults.setSpeedTests(speedTests);
		return this;
	}
	
	public DiagnosticsResultsBuilder generalPingTests(GeneralPingTests generalPingTests) {
		diagnosticsResults.setGeneralPingTests(generalPingTests);
		return this;
	}
	
	public DiagnosticsResultsBuilder amsPingTests(AmsPingTests amsPingTests) {
		diagnosticsResults.setAmsPingTests(amsPingTests);
		return this;
	}
	
	public DiagnosticsResultsBuilder exceptions(Exceptions exceptions) {
		diagnosticsResults.setExceptions(exceptions);
		return this;
	}
	
	public DiagnosticsResults build() {
		return this.diagnosticsResults;
	}
}
