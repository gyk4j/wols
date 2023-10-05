package net.altkey12.wols.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import net.altkey12.wols.Wols;

public abstract class NetworkProfile {
	public String id;
	public UUID uuid;

	public static final String IFNAME = "wlo1";
	protected String identity;
	protected String password;
	
	protected NetworkProfile() {
		super();
	}
	
	protected NetworkProfile(String identity, String password) {
		this.identity = identity;
		this.password = password;
	}
	
	public abstract void load();
	public abstract void save();
	public abstract String getWifiProvider();
	public abstract LocalDateTime getConfigurationTimestamp();
	
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static String getSsid() {
		return Wols.SSID;
	}
	public static String getInterfaceName() {
		return IFNAME;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public static String getIfname() {
		return IFNAME;
	}
}
