package gyk4j.wols.beans.speedtest.windows;

import java.util.UUID;

public class OutputNetshWlanShowInterfaces {
	Interface[] interfaces;
	
	/**
	 * 
	 * @param interfaces
	 */
	public OutputNetshWlanShowInterfaces(Interface[] interfaces) {
		this.interfaces = interfaces;
	}
	
	public Interface[] getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Interface[] interfaces) {
		this.interfaces = interfaces;
	}

	public static class Interface {
		String name;
		String description;
		UUID guid;
		byte[] physicalAddress;
		boolean state;
		String ssid;
		String bssid;
		String networkType;
		String radioType;
		String authentication;
		String cipher;
		String connectionMode;
		int channel;
		int receiveRateMbps;
		int transmitRateMbps;
		int signal;
		String profile;
		String hostedNetworkStatus;
		
		/**
		 * 
		 * @param name
		 * @param description
		 * @param guid
		 * @param physicalAddress
		 * @param state
		 * @param ssid
		 * @param bssid
		 * @param networkType
		 * @param radioType
		 * @param authentication
		 * @param cipher
		 * @param connectionMode
		 * @param channel
		 * @param receiveRateMbps
		 * @param transmitRateMbps
		 * @param signal
		 * @param profile
		 * @param hostedNetworkStatus
		 */
		public Interface(String name, String description, UUID guid, byte[] physicalAddress, boolean state, String ssid,
				String bssid, String networkType, String radioType, String authentication, String cipher,
				String connectionMode, int channel, int receiveRateMbps, int transmitRateMbps, int signal,
				String profile, String hostedNetworkStatus) {
			super();
			this.name = name;
			this.description = description;
			this.guid = guid;
			this.physicalAddress = physicalAddress;
			this.state = state;
			this.ssid = ssid;
			this.bssid = bssid;
			this.networkType = networkType;
			this.radioType = radioType;
			this.authentication = authentication;
			this.cipher = cipher;
			this.connectionMode = connectionMode;
			this.channel = channel;
			this.receiveRateMbps = receiveRateMbps;
			this.transmitRateMbps = transmitRateMbps;
			this.signal = signal;
			this.profile = profile;
			this.hostedNetworkStatus = hostedNetworkStatus;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public UUID getGuid() {
			return guid;
		}

		public void setGuid(UUID guid) {
			this.guid = guid;
		}

		public byte[] getPhysicalAddress() {
			return physicalAddress;
		}

		public void setPhysicalAddress(byte[] physicalAddress) {
			this.physicalAddress = physicalAddress;
		}

		public boolean getState() {
			return state;
		}

		public void setState(boolean state) {
			this.state = state;
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

		public String getRadioType() {
			return radioType;
		}

		public void setRadioType(String radioType) {
			this.radioType = radioType;
		}

		public String getAuthentication() {
			return authentication;
		}

		public void setAuthentication(String authentication) {
			this.authentication = authentication;
		}

		public String getCipher() {
			return cipher;
		}

		public void setCipher(String cipher) {
			this.cipher = cipher;
		}

		public String getConnectionMode() {
			return connectionMode;
		}

		public void setConnectionMode(String connectionMode) {
			this.connectionMode = connectionMode;
		}

		public int getChannel() {
			return channel;
		}

		public void setChannel(int channel) {
			this.channel = channel;
		}

		public int getReceiveRateMbps() {
			return receiveRateMbps;
		}

		public void setReceiveRateMbps(int receiveRateMbps) {
			this.receiveRateMbps = receiveRateMbps;
		}

		public int getTransmitRateMbps() {
			return transmitRateMbps;
		}

		public void setTransmitRateMbps(int transmitRateMbps) {
			this.transmitRateMbps = transmitRateMbps;
		}

		public int getSignal() {
			return signal;
		}

		public void setSignal(int signal) {
			this.signal = signal;
		}

		public String getProfile() {
			return profile;
		}

		public void setProfile(String profile) {
			this.profile = profile;
		}

		public String getHostedNetworkStatus() {
			return hostedNetworkStatus;
		}

		public void setHostedNetworkStatus(String hostedNetworkStatus) {
			this.hostedNetworkStatus = hostedNetworkStatus;
		}
	}
}
