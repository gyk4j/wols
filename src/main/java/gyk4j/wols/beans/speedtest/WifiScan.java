package gyk4j.wols.beans.speedtest;

public class WifiScan {

	protected int id;
	protected String ssid;
	protected String bssid;
	protected String networkType;
	protected int rssi;
	protected int channelFreq;
	protected int channelNumber;
	protected String channelWidth;
	
	/**
	 * 
	 * @param id
	 * @param ssid
	 * @param bssid
	 * @param networkType
	 * @param rssi
	 * @param channelFreq
	 * @param channelNumber
	 * @param channelWidth
	 */
	public WifiScan(int id, String ssid, String bssid, String networkType, int rssi, int channelFreq, int channelNumber,
			String channelWidth) {
		super();
		this.id = id;
		this.ssid = ssid;
		this.bssid = bssid;
		this.networkType = networkType;
		this.rssi = rssi;
		this.channelFreq = channelFreq;
		this.channelNumber = channelNumber;
		this.channelWidth = channelWidth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public int getChannelFreq() {
		return channelFreq;
	}

	public void setChannelFreq(int channelFreq) {
		this.channelFreq = channelFreq;
	}

	public int getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(int channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getChannelWidth() {
		return channelWidth;
	}

	public void setChannelWidth(String channelWidth) {
		this.channelWidth = channelWidth;
	}
}
