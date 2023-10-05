package net.altkey12.wols.beans.speedtest.windows;

public class OutputNetshWlanShowNetworksModeBssid {
	String interfaceName;
	Network[] networks;
	
	/**
	 * 
	 * @param interfaceName
	 * @param networks
	 */
	public OutputNetshWlanShowNetworksModeBssid(String interfaceName, Network[] networks) {
		super();
		this.interfaceName = interfaceName;
		this.networks = networks;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Network[] getNetworks() {
		return networks;
	}

	public void setNetworks(Network[] networks) {
		this.networks = networks;
	}

	public static class Network {
		String ssid;
		String networkType;
		String authentication;
		String encryption;
		Bssid[] bssids;
		
		/**
		 * 
		 * @param ssid
		 * @param networkType
		 * @param authentication
		 * @param encryption
		 * @param bssids
		 */
		public Network(String ssid, String networkType, String authentication, String encryption, Bssid[] bssids) {
			super();
			this.ssid = ssid;
			this.networkType = networkType;
			this.authentication = authentication;
			this.encryption = encryption;
			this.bssids = bssids;
		}

		public String getSsid() {
			return ssid;
		}

		public void setSsid(String ssid) {
			this.ssid = ssid;
		}

		public String getNetworkType() {
			return networkType;
		}

		public void setNetworkType(String networkType) {
			this.networkType = networkType;
		}

		public String getAuthentication() {
			return authentication;
		}

		public void setAuthentication(String authentication) {
			this.authentication = authentication;
		}

		public String getEncryption() {
			return encryption;
		}

		public void setEncryption(String encryption) {
			this.encryption = encryption;
		}

		public Bssid[] getBssids() {
			return bssids;
		}

		public void setBssids(Bssid[] bssids) {
			this.bssids = bssids;
		}
		
	}
	
	public static class Bssid {
		String bssid;
		int signal; // in percent
		String radioType;
		int channel;
		float[] basicRatesMbps;
		float[] otherRatesMbps;
		
		/**
		 * 
		 * @param bssid
		 * @param signal
		 * @param radioType
		 * @param channel
		 * @param basicRatesMbps
		 * @param otherRatesMbps
		 */
		public Bssid(String bssid, int signal, String radioType, int channel, float[] basicRatesMbps, float[] otherRatesMbps) {
			super();
			this.bssid = bssid;
			this.signal = signal;
			this.radioType = radioType;
			this.channel = channel;
			this.basicRatesMbps = basicRatesMbps;
			this.otherRatesMbps = otherRatesMbps;
		}

		public String getBssid() {
			return bssid;
		}

		public void setBssid(String bssid) {
			this.bssid = bssid;
		}

		public int getSignal() {
			return signal;
		}

		public void setSignal(int signal) {
			this.signal = signal;
		}

		public String getRadioType() {
			return radioType;
		}

		public void setRadioType(String radioType) {
			this.radioType = radioType;
		}

		public int getChannel() {
			return channel;
		}

		public void setChannel(int channel) {
			this.channel = channel;
		}

		public float[] getBasicRatesMbps() {
			return basicRatesMbps;
		}

		public void setBasicRatesMbps(float[] basicRatesMbps) {
			this.basicRatesMbps = basicRatesMbps;
		}

		public float[] getOtherRatesMbps() {
			return otherRatesMbps;
		}

		public void setOtherRatesMbps(float[] otherRatesMbps) {
			this.otherRatesMbps = otherRatesMbps;
		}
	}
}
