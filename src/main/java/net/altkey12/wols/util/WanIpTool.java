package net.altkey12.wols.util;

import java.net.InetAddress;
import java.util.concurrent.Callable;

import net.altkey12.wols.beans.speedtest.OutputWanIp;

public class WanIpTool implements Callable<OutputWanIp> {

	private static final String IPIFY_URL = "https://api.ipify.org";

	@Override
	public OutputWanIp call() throws Exception {
		InetAddress wanIp = null;
		try (java.util.Scanner s = new java.util.Scanner(
				new java.net.URL(IPIFY_URL).openStream(), 
				"UTF-8")
				.useDelimiter("\\A")) {
			wanIp = InetAddress.getByName(s.next());
		} catch (java.io.IOException e) {
		    System.err.println("[WARN] " + e.getMessage());
		}
		
		return new OutputWanIp(wanIp);
	}

}
