package gyk4j.wols.repository.linux;

import java.util.UUID;

import gyk4j.wols.io.ResourceLoader;
import gyk4j.wols.repository.NetworkProfile;

public class NetworkManagerBuilder {
	protected String ssid = NetworkProfile.getSsid();
	protected String identity;
	protected String password;
	protected UUID uuid;
	protected String interface_;
	
	public static final String WIFI_NETWORK_PROFILE = ResourceLoader.readAsString("ConnectionNetworkManager.txt");
	
	public String build() {
		String profile = WIFI_NETWORK_PROFILE
		.replaceAll("%ssid%", ssid)
		.replaceAll("%uuid%", uuid.toString())
		.replaceAll("%identity%", identity)
		.replaceAll("%password%", password)
		.replaceAll("%interface%", interface_);
//		String profile = String.format(WIFI_NETWORK_PROFILE, ssid, identity, password);
		return profile;
	}

	public String ssid() {
		return ssid;
	}

	public NetworkManagerBuilder ssid(String ssid) {
		this.ssid = ssid;
		return this;
	}

	public String identity() {
		return identity;
	}

	public NetworkManagerBuilder identity(String identity) {
		this.identity = identity;
		return this;
	}

	public String password() {
		return password;
	}

	public NetworkManagerBuilder password(String password) {
		this.password = password;
		return this;
	}

	public UUID uuid() {
		return uuid;
	}

	public NetworkManagerBuilder uuid(UUID uuid) {
		this.uuid = uuid;
		return this;
	}

	public String interface_() {
		return interface_;
	}

	public NetworkManagerBuilder interface_(String interface_) {
		this.interface_ = interface_;
		return this;
	}
	
}
