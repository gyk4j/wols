package gyk4j.wols.net;

import static org.python.io.Logger.LOG;
import static org.python.io.Logger.errprint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Properties;

import org.python.net.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Mimic the Python requests library module
 *
 */
public class RequestsImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestsImpl.class);
	
	private static int TIMEOUT = 30000; // 30 seconds timeout
	
	public static Response get(String url, LinkedHashMap<String, String> params) throws IOException {
		return get(url, params, null);
	}
	
	private static Response get(String uri, LinkedHashMap<String, String> params, Properties headers) throws IOException {
		int statusCode = HttpURLConnection.HTTP_INTERNAL_ERROR;
		String body = "";
		
		String urlWithParams = addRequestParameters(params, uri);
		
		final URL url = new URL(urlWithParams);
		
		LOGGER.trace(url.toString());
		
		final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		addRequestProperties(headers, connection);
		
		try {
			connection.connect();

			int responseCode = connection.getResponseCode();
			LOG(String.format("HTTP Response: %d", responseCode));
			LOGGER.trace("HTTP Response: {}", responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream is = connection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();

				body = response.toString();
			} else {
				body = connection.getResponseMessage();
			}

			statusCode = responseCode;
		} catch (IOException ex) {
			errprint(ex.toString());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		if(body != null && body.length() > 0)
			LOGGER.trace(body);
		
		return new Response(statusCode, body);
	}
	
	private static String addRequestParameters(LinkedHashMap<String, String> params, String baseUrl) throws UnsupportedEncodingException {
		final StringBuilder urlBuilder = new StringBuilder();
		
		if(!baseUrl.endsWith("/")) {
			urlBuilder.append('/');
		}
		
		if (params != null && !params.isEmpty()) {
			urlBuilder.append('?');
			params.forEach(
					(key, value) -> 
					{
						try {
							String urlencoded = (value == null)? "": URLEncoder.encode((String)value, "UTF-8");
							urlBuilder
							.append(key).append('=').append(urlencoded)
							.append('&');
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					});
		}
		
		String url = urlBuilder.toString();
		if(url.endsWith("&")) {
			url = url.substring(0, url.length()-1);
		}
		
		return baseUrl.concat(url);
	}
	
	private static void addRequestProperties(Properties properties, HttpURLConnection connection) throws ProtocolException {
		connection.setConnectTimeout(TIMEOUT);
		connection.setReadTimeout(TIMEOUT);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("GET");
//		connection.setRequestProperty("User-Agent", "");
//		connection.setRequestProperty("Accept-Language", "en-US");

		if (properties != null && !properties.isEmpty()) {
			properties.forEach((key, value) -> connection.setRequestProperty((String) key, (String) value));
		}
	}
}
