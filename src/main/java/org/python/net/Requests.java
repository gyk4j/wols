package org.python.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;

import net.altkey12.wols.Wols;
import net.altkey12.wols.net.RequestsImpl;
import net.altkey12.wols.net.RequestsStub;

public interface Requests {
	public static class codes {
		public static int ok = HttpURLConnection.HTTP_OK;
		public static int not_found = HttpURLConnection.HTTP_NOT_FOUND;
	}
	
	public static Response get(String url, LinkedHashMap<String, String> params) throws IOException {
		return (Wols.SSID_PROD.equals(Wols.SSID))? 
				RequestsImpl.get(url, params) : RequestsStub.get(url, params);
	}
}
