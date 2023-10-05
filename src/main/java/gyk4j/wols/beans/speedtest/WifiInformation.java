package gyk4j.wols.beans.speedtest;

public class WifiInformation {
	protected String ssid;
	protected String bssid;
	protected int rssi;
	protected int channelFrequency;
	protected int radioLinkSpeed;
	
	/**
	 * 
	 * @param ssid
	 * @param bssid
	 * @param rssi
	 * @param channelFrequency
	 * @param radioLinkSpeed
	 */
	public WifiInformation(String ssid, String bssid, int rssi, int channelFrequency, int radioLinkSpeed) {
		super();
		this.ssid = ssid;
		this.bssid = bssid;
		this.rssi = rssi;
		this.channelFrequency = channelFrequency;
		this.radioLinkSpeed = radioLinkSpeed;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getBssid() {
		return bssid;
	}

	public void setBssid(String bssid) {
		this.bssid = bssid;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public int getChannelFrequency() {
		return channelFrequency;
	}

	public void setChannelFrequency(int channelFrequency) {
		this.channelFrequency = channelFrequency;
	}

	public int getRadioLinkSpeed() {
		return radioLinkSpeed;
	}

	public void setRadioLinkSpeed(int radioLinkSpeed) {
		this.radioLinkSpeed = radioLinkSpeed;
	}
}
