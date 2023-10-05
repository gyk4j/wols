package gyk4j.wols.repository.linux;

import gyk4j.wols.repository.NetworkProfile;

public class WpaSupplicantConfBuilder {
	protected String ssid = NetworkProfile.getSsid();
	protected String identity;
	protected String password;
	
	public static final String WIFI_NETWORK_PROFILE = 
			"network={" + System.lineSeparator() +
			"    ssid=\"%ssid%\"" + System.lineSeparator() +
			"    priority=0" + System.lineSeparator() +
			"    key_mgmt=WPA-EAP" + System.lineSeparator() +
			"    eap=PEAP" + System.lineSeparator() +
			"    identity=\"%identity%\"" + System.lineSeparator() +
			"    password=\"%password%\"" + System.lineSeparator() +
			"    phase1=\"peaplabel=0\"" + System.lineSeparator() +
			"    phase2=\"auth=MSCHAPV2\"" + System.lineSeparator() +
			"    ca_cert=\"/etc/ssl/certs/ca-certificates.crt\"" + System.lineSeparator() +
		    "    ca_cert2=\"/etc/ssl/certs/ca-certificates.crt\"" + System.lineSeparator() +
		    "    ca_path=\"/etc/ssl/certs\"" + System.lineSeparator() +
		    "    ca_path2=\"/etc/ssl/certs\"" + System.lineSeparator() +
//			"    proto=RSN" + System.lineSeparator() +
//			"    pairwise=CCMP" + System.lineSeparator() +
//			"    auth_alg=OPEN" + System.lineSeparator() +			
			"}";
	
	public String build() {
		String profile = WIFI_NETWORK_PROFILE
		.replaceAll("%ssid%", ssid)
		.replaceAll("%identity%", identity)
		.replaceAll("%password%", password);
//		String profile = String.format(WIFI_NETWORK_PROFILE, ssid, identity, password);
		return profile;
	}

	public String ssid() {
		return ssid;
	}

	public WpaSupplicantConfBuilder ssid(String ssid) {
		this.ssid = ssid;
		return this;
	}

	public String identity() {
		return identity;
	}

	public WpaSupplicantConfBuilder identity(String identity) {
		this.identity = identity;
		return this;
	}

	public String password() {
		return password;
	}

	public WpaSupplicantConfBuilder password(String password) {
		this.password = password;
		return this;
	}
}
