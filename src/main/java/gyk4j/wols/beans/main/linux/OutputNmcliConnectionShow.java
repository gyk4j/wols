package gyk4j.wols.beans.main.linux;

import java.util.UUID;

public class OutputNmcliConnectionShow {
	String connectionId;
	UUID connectionUuid;
	String ifName;
	String ssid;
	String wifiSecAuthAlg;
	String wifiSecKeyMgmt;
	String _8021xCaCert;
	String _8021xEap;
	String _8021xIdentity;
	String _8021xPassword;
	String _8021xPhase2Auth;
	
	/**
	 * 
	 * @param connectionId
	 * @param ifName
	 * @param ssid
	 * @param wifiSecAuthAlg
	 * @param wifiSecKeyMgmt
	 * @param _8021xCaCert
	 * @param _8021xEap
	 * @param _8021xIdentity
	 * @param _8021xPassword
	 * @param _8021xPhase2Auth
	 */
	public OutputNmcliConnectionShow(String connectionId, UUID connectionUuid, String ifName, String ssid, String wifiSecAuthAlg,
			String wifiSecKeyMgmt, String _8021xCaCert, String _8021xEap, String _8021xIdentity, String _8021xPassword,
			String _8021xPhase2Auth) {
		super();
		this.connectionId = connectionId;
		this.connectionUuid = connectionUuid;
		this.ifName = ifName;
		this.ssid = ssid;
		this.wifiSecAuthAlg = wifiSecAuthAlg;
		this.wifiSecKeyMgmt = wifiSecKeyMgmt;
		this._8021xCaCert = _8021xCaCert;
		this._8021xEap = _8021xEap;
		this._8021xIdentity = _8021xIdentity;
		this._8021xPassword = _8021xPassword;
		this._8021xPhase2Auth = _8021xPhase2Auth;
	}

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
	
	public UUID getConnectionUuid() {
		return connectionUuid;
	}

	public void setConnectionUuid(UUID connectionUuid) {
		this.connectionUuid = connectionUuid;
	}

	public String getIfName() {
		return ifName;
	}

	public void setIfName(String ifName) {
		this.ifName = ifName;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getWifiSecAuthAlg() {
		return wifiSecAuthAlg;
	}

	public void setWifiSecAuthAlg(String wifiSecAuthAlg) {
		this.wifiSecAuthAlg = wifiSecAuthAlg;
	}

	public String getWifiSecKeyMgmt() {
		return wifiSecKeyMgmt;
	}

	public void setWifiSecKeyMgmt(String wifiSecKeyMgmt) {
		this.wifiSecKeyMgmt = wifiSecKeyMgmt;
	}

	public String get_8021xCaCert() {
		return _8021xCaCert;
	}

	public void set_8021xCaCert(String _8021xCaCert) {
		this._8021xCaCert = _8021xCaCert;
	}

	public String get_8021xEap() {
		return _8021xEap;
	}

	public void set_8021xEap(String _8021xEap) {
		this._8021xEap = _8021xEap;
	}

	public String get_8021xIdentity() {
		return _8021xIdentity;
	}

	public void set_8021xIdentity(String _8021xIdentity) {
		this._8021xIdentity = _8021xIdentity;
	}

	public String get_8021xPassword() {
		return _8021xPassword;
	}

	public void set_8021xPassword(String _8021xPassword) {
		this._8021xPassword = _8021xPassword;
	}

	public String get_8021xPhase2Auth() {
		return _8021xPhase2Auth;
	}

	public void set_8021xPhase2Auth(String _8021xPhase2Auth) {
		this._8021xPhase2Auth = _8021xPhase2Auth;
	}
	
	
}
