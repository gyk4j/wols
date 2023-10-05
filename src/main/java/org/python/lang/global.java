package org.python.lang;

import static org.python.io.Io.bstr;

import java.util.HashMap;
import java.util.Map;

public class global {
	public static final String DEFAULT_ISP = "singtel";
	public static final byte[] DEFAULT_TRANSID = bstr("053786654500000000000000");
	public static boolean VERBOSE;
	public static String otp;
	public static String successCode;
	
	public static class Isp {
		public String essaUrl;
		public String apiPassword;
		public String[] createApiVersions;
		public String[] retrieveApiVersions;
		
		public Isp() {
			this.essaUrl = "";
			this.apiPassword = "";
			this.createApiVersions = new String[] { "0.0", "0.0" };
			this.retrieveApiVersions = new String[] { "0.0", "0.0" };
		}
		
		public Isp(String essaUrl, String apiPassword, String[] createApiVersions, String[] retrieveApiVersions) {
			this.essaUrl = essaUrl;
			this.apiPassword = apiPassword;
			this.createApiVersions = createApiVersions;
			this.retrieveApiVersions = retrieveApiVersions;
		}
	}
	
	public static final Map<String, Isp> ISP_CONFIG = new HashMap<>();
	
	static {
		ISP_CONFIG.put("m1", new Isp());
		
		ISP_CONFIG.put("singtel", new Isp(
//				"https://localhost/singtel/essa_r12",
				"https://singtel-wsg.singtel.com/essa_r12",
				"",
				new String[] {"2.6", "2.8"},
				new String[] {"2.0", "2.6"}));
		
		ISP_CONFIG.put("starhub", new Isp(
//				"https://localhost/starhub/essa_r12",
				"https://api.wifi.starhub.net.sg/essa_r12",
				"5t4rHUB4p1",
				new String[] {"2.6", "2.8"},
				new String[] {"2.0", "2.6"}));
		
		ISP_CONFIG.put("simba", new Isp());
	}
}
