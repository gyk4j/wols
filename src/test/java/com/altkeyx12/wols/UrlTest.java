package com.altkeyx12.wols;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import org.json.JSONObject;
import org.junit.Test;
import org.python.net.Response;

import net.altkey12.wols.net.RequestsStub;

import static org.python.lang.global.DEFAULT_TRANSID;

public class UrlTest {

	@Test
	public void test() {
		URL url = null;
		try {
			url = new URL("https", "localhost", "/abc/def/ghi.html?requestProperty=value");
			System.out.println(url.getQuery());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertNotNull(url);
		assertEquals("requestProperty=value", url.getQuery());
	}
	
	@Test
	public void testRegister() {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("api", "create_user_r12x1a");
		params.put("api_password", "xxxxxxxx");
		params.put("salutation", "Ms");
		params.put("name", "Violet Onn");
		params.put("gender", "f");
		params.put("dob", "20071955");
		params.put("mobile", "96665555");
		params.put("nationality", "SG");
		params.put("email", "violet.onn@gmail.com");
		params.put("tid", new String(DEFAULT_TRANSID, StandardCharsets.US_ASCII));
		
		try {
			Response r = RequestsStub.get("https://telco-wsg.telco.com/essa_r12", params, null);
			prettyPrint(r);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testValidateOtp() {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("api", "create_user_r12x1b");
		params.put("api_password", "yyyyyyyy");
		params.put("dob", "18031965");
		params.put("mobile", "80105463");
		params.put("otp", "123456");
		params.put("success_code", "999999");
		params.put("tid", new String(DEFAULT_TRANSID, StandardCharsets.US_ASCII));
		
		try {
			Response r = RequestsStub.get("https://telco-wsg.telco.com/essa_r12", params, null);
			prettyPrint(r);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void prettyPrint(Response response) {
		if(response.getStatusCode() == RequestsStub.codes.ok) {
			JSONObject json = new JSONObject(response.getBody());
			System.out.println(json.toString(4));
		}
		else {
			System.err.println("Status code: " + response.getStatusCode());
		}
	}
}
