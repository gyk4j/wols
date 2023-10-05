package gyk4j.wols.net;

import gyk4j.wols.beans.speedtest.AmsPingTests;
import gyk4j.wols.beans.speedtest.DeviceInformation;
import gyk4j.wols.beans.speedtest.DiagnosticsInformation;
import gyk4j.wols.beans.speedtest.Exceptions;
import gyk4j.wols.beans.speedtest.GeneralPingTests;
import gyk4j.wols.beans.speedtest.NetworkInformation;
import gyk4j.wols.beans.speedtest.SpeedTests;
import gyk4j.wols.beans.speedtest.WifiInformation;
import gyk4j.wols.beans.speedtest.WifiScanResults;

public interface NetworkDiagnostics {
	public DiagnosticsInformation getDiagnosticsInformation();
	public DeviceInformation getDeviceInformation();
	public WifiInformation getWifiInformation();
	public NetworkInformation getNetworkInformation();
	public WifiScanResults getWifiScanResults();
	public SpeedTests getSpeedTests();
	public GeneralPingTests getGeneralPingTests();
	public AmsPingTests getAmsPingTests();
	public Exceptions getExceptions();
}
