package net.altkey12.wols.repository;

public interface IConfiguration {	
	// ISP URLs were taken from WSG.Common.dll
	// Test URL is for debugging.
	public static final String ISP_CONFIG = "isp";
	public static final String ESSA_URL = "essa_url";
	public static final String CREATE_API_VERSIONS = "create_api_versions";
	public static final String RETRIEVE_API_VERSIONS = "retrieve_api_versions";

	public static final String SINGTEL = "singtel";
	public static final String MYREPUBLIC = "myrepublic";

	public static final String DEFAULT_ISP = SINGTEL;
	
	public String getEssaUrl(String isp);
	public String getCreateApiVersion(String isp, int version);
	public String getRetrieveApiVersion(String isp, int version);
	public default String getDefaultIsp() {
		return DEFAULT_ISP;
	}

	// The transaction ID (transid) appears to be created from the WiFi
	// interface's GUID in Windows, which is probably based on the MAC
	// address. The below transid was found within WSG.Common.dll, and is used
	// when there is no "DeviceManager" available. It seems to work fine.
	public static final byte[] DEFAULT_TRANSID = { 05, 37, 86, 65, 45, 00, 00, 00, 00, 00, 00, 00 };
	public static final String VERBOSE = "verbose";

	// Result codes
	public static final String RC_SUCCESS = "rc_success";

	public boolean isVerbose();
	public default byte[] getDefaultTransId() {
		return DEFAULT_TRANSID;
	}
	
	public String get(String... nodes);
	
	public IConfiguration getInstance();
}
