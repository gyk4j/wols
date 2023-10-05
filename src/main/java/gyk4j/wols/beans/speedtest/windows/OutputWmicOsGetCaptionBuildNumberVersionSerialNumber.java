package gyk4j.wols.beans.speedtest.windows;

public class OutputWmicOsGetCaptionBuildNumberVersionSerialNumber {
	String caption;
	String buildNumber;
	String version;
	String serialNumber;
	
	/**
	 * 
	 * @param caption
	 * @param buildNumber
	 * @param version
	 */
	public OutputWmicOsGetCaptionBuildNumberVersionSerialNumber(String caption, String buildNumber, String version, String serialNumber) {
		super();
		this.caption = caption;
		this.buildNumber = buildNumber;
		this.version = version;
		this.serialNumber = serialNumber;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getBuildNumber() {
		return buildNumber;
	}
	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
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
	
}
