package net.altkey12.wols.beans.speedtest;

public class DeviceInformation {

	protected String deviceModel;
	protected String deviceMake;
	protected String deviceOs;
	protected String deviceOsVersion;
	protected String applicationId;
	protected String applicationVersion;
	
	/**
	 * 
	 * @param deviceModel Device model
	 * @param deviceMake Device make
	 * @param deviceOs Device operating system
	 * @param deviceOsVersion Operating system version
	 * @param applicationId Application ID
	 * @param applicationVersion Application version
	 */
	public DeviceInformation(String deviceModel, String deviceMake, String deviceOs, String deviceOsVersion,
			String applicationId, String applicationVersion) {
		super();
		this.deviceModel = deviceModel;
		this.deviceMake = deviceMake;
		this.deviceOs = deviceOs;
		this.deviceOsVersion = deviceOsVersion;
		this.applicationId = applicationId;
		this.applicationVersion = applicationVersion;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceMake() {
		return deviceMake;
	}

	public void setDeviceMake(String deviceMake) {
		this.deviceMake = deviceMake;
	}

	public String getDeviceOs() {
		return deviceOs;
	}

	public void setDeviceOs(String deviceOs) {
		this.deviceOs = deviceOs;
	}

	public String getDeviceOsVersion() {
		return deviceOsVersion;
	}

	public void setDeviceOsVersion(String deviceOsVersion) {
		this.deviceOsVersion = deviceOsVersion;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}
}
