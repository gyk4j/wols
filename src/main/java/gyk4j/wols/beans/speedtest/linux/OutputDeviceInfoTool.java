package gyk4j.wols.beans.speedtest.linux;

public class OutputDeviceInfoTool {
	String sysVendor;
	String productName;
	String caption;
	String version;
	String serialNumber;
	String buildNumber;

	/**
	 * 
	 * @param sysVendor
	 * @param productName
	 * @param caption
	 * @param version
	 * @param serialNumber
	 * @param buildNumber
	 */
	public OutputDeviceInfoTool(String sysVendor, String productName, String caption, String version,
			String serialNumber, String buildNumber) {
		super();
		this.sysVendor = sysVendor;
		this.productName = productName;
		this.caption = caption;
		this.version = version;
		this.serialNumber = serialNumber;
		this.buildNumber = buildNumber;
	}

	public String getSysVendor() {
		return sysVendor;
	}

	public void setSysVendor(String sysVendor) {
		this.sysVendor = sysVendor;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}
	
}
