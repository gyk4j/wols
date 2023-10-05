package net.altkey12.wols.beans.speedtest;

public class DiagnosticsResults {
	protected DiagnosticsInformation diagnosticsInformation;
	protected DeviceInformation deviceInformation;
	protected WifiInformation wifiInformation;
	protected NetworkInformation networkInformation;
	protected WifiScanResults wifiScanResults;
	protected SpeedTests speedTests;
	protected GeneralPingTests generalPingTests;
	protected AmsPingTests amsPingTests;
	protected Exceptions exceptions;
	
	public DiagnosticsResults() {
		super();
	}
	
	public DiagnosticsResults(DiagnosticsInformation diagnosticsInformation, DeviceInformation deviceInformation,
			WifiInformation wifiInformation, NetworkInformation networkInformation, WifiScanResults wifiScanResults,
			SpeedTests speedTests, GeneralPingTests generalPingTests, AmsPingTests amsPingTests,
			Exceptions exceptions) {
		super();
		this.diagnosticsInformation = diagnosticsInformation;
		this.deviceInformation = deviceInformation;
		this.wifiInformation = wifiInformation;
		this.networkInformation = networkInformation;
		this.wifiScanResults = wifiScanResults;
		this.speedTests = speedTests;
		this.generalPingTests = generalPingTests;
		this.amsPingTests = amsPingTests;
		this.exceptions = exceptions;
	}

	public DiagnosticsInformation getDiagnosticsInformation() {
		return diagnosticsInformation;
	}

	public void setDiagnosticsInformation(DiagnosticsInformation diagnosticsInformation) {
		this.diagnosticsInformation = diagnosticsInformation;
	}

	public DeviceInformation getDeviceInformation() {
		return deviceInformation;
	}

	public void setDeviceInformation(DeviceInformation deviceInformation) {
		this.deviceInformation = deviceInformation;
	}

	public WifiInformation getWifiInformation() {
		return wifiInformation;
	}

	public void setWifiInformation(WifiInformation wifiInformation) {
		this.wifiInformation = wifiInformation;
	}

	public NetworkInformation getNetworkInformation() {
		return networkInformation;
	}

	public void setNetworkInformation(NetworkInformation networkInformation) {
		this.networkInformation = networkInformation;
	}

	public WifiScanResults getWifiScanResults() {
		return wifiScanResults;
	}

	public void setWifiScanResults(WifiScanResults wifiScanResults) {
		this.wifiScanResults = wifiScanResults;
	}

	public SpeedTests getSpeedTests() {
		return speedTests;
	}

	public void setSpeedTests(SpeedTests speedTests) {
		this.speedTests = speedTests;
	}

	public GeneralPingTests getGeneralPingTests() {
		return generalPingTests;
	}

	public void setGeneralPingTests(GeneralPingTests generalPingTests) {
		this.generalPingTests = generalPingTests;
	}

	public AmsPingTests getAmsPingTests() {
		return amsPingTests;
	}

	public void setAmsPingTests(AmsPingTests amsPingTests) {
		this.amsPingTests = amsPingTests;
	}

	public Exceptions getExceptions() {
		return exceptions;
	}

	public void setExceptions(Exceptions exceptions) {
		this.exceptions = exceptions;
	}
	
}
