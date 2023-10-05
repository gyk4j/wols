package net.altkey12.wols.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

import net.altkey12.wols.beans.speedtest.OutputSpeedTests;
import net.altkey12.wols.net.platform.windows.Wifi;
import net.altkey12.wols.net.platform.windows.Wifi.NetworkSpeedUnit;

public class SpeedTestsTool implements Callable<OutputSpeedTests> {

	private static final int CONNECT_TIMEOUT = 5000;
//	private static final String SAMPLE_DATA = "https://gist.githubusercontent.com/khaykov/a6105154becce4c0530da38e723c2330/raw/41ab415ac41c93a198f7da5b47d604956157c5c3/gistfile1.txt";
//	private static final String SAMPLE_DATA = "https://mirror.nforce.com/pub/speedtests/10mb.bin";
//	private static final String SAMPLE_DATA = "http://127.0.0.1/Free_Test_Data_10MB_MP4.mp4";
	private static final String SAMPLE_DATA = "http://www.speedtest.com.sg/test_random_10mb.zip";
	
	@Override
	public OutputSpeedTests call() throws Exception {
		long start = System.currentTimeMillis();
		
		long size = 0;

		URL url = new URL(SAMPLE_DATA);
		URLConnection c = url.openConnection();
		c.setConnectTimeout(CONNECT_TIMEOUT);
		InputStream is = c.getInputStream();
		while (is.read() != -1) {
			size++;
		}
		
		float mbps = Wifi.getNetworkSpeed(size, start, System.currentTimeMillis(), NetworkSpeedUnit.Mbps);
		return new OutputSpeedTests(mbps);
	}

}
