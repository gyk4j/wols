package net.altkey12.wols.net;

import net.altkey12.wols.beans.speedtest.AmsPingTests;
import net.altkey12.wols.beans.speedtest.DeviceInformation;
import net.altkey12.wols.beans.speedtest.DiagnosticsInformation;
import net.altkey12.wols.beans.speedtest.Exceptions;
import net.altkey12.wols.beans.speedtest.GeneralPingTests;
import net.altkey12.wols.beans.speedtest.NetworkInformation;
import net.altkey12.wols.beans.speedtest.SpeedTests;
import net.altkey12.wols.beans.speedtest.WifiInformation;
import net.altkey12.wols.beans.speedtest.WifiScanResults;

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
