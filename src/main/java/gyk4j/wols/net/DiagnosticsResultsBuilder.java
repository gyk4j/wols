package gyk4j.wols.net;

import gyk4j.wols.beans.speedtest.AmsPingTests;
import gyk4j.wols.beans.speedtest.DeviceInformation;
import gyk4j.wols.beans.speedtest.DiagnosticsInformation;
import gyk4j.wols.beans.speedtest.DiagnosticsResults;
import gyk4j.wols.beans.speedtest.Exceptions;
import gyk4j.wols.beans.speedtest.GeneralPingTests;
import gyk4j.wols.beans.speedtest.NetworkInformation;
import gyk4j.wols.beans.speedtest.SpeedTests;
import gyk4j.wols.beans.speedtest.WifiInformation;
import gyk4j.wols.beans.speedtest.WifiScanResults;

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
