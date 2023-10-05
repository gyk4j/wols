package net.altkey12.wols.net;

import static org.python.lang.global.ISP_CONFIG;
import static org.python.io.Io.bstr;
import static org.python.io.Io.bytes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Properties;

import org.json.JSONObject;
import org.python.lang.global.Isp;
import org.python.modules.Codecs;
import org.python.modules.crypto.CryptoUtils;
import org.python.modules.crypto.cipher.AES.Encrypted;
import org.python.net.Response;
import org.python.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Simulate the Python requests library module for testing.
 *
 */
public class RequestsStub {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestsStub.class);
	
	public static class codes {
		public static int ok = HttpURLConnection.HTTP_OK;
		public static int not_found = HttpURLConnection.HTTP_NOT_FOUND;
	}
	
	private static String fakeSuccessCode;
	private static String fakeOtp;
	
	private static String fakeUserId;
	private static String fakePassword;
	private static byte[] fakeNonce;
	private static byte[] fakeEncUserId;
	private static byte[] fakeTagUserId;
	private static byte[] fakeEncPassword;
	private static byte[] fakeTagPassword;
	
	public static String getFakeOTP() {
		return fakeOtp;
	}
	
	private static void generateRegister(String isp) {
		SecureRandom rng = new SecureRandom();
		
		fakeSuccessCode = String.format("%08d", Math.abs(rng.nextLong())).substring(0, 8);
		LOGGER.info("Success code: {}", fakeSuccessCode);
		
		fakeOtp = String.format("%06d", Math.abs(rng.nextLong())).substring(0, 6);
		LOGGER.info("OTP: {}", fakeOtp);
	}
	
	
	
	private static void generateValidate(String isp, byte[] transid, String otp) throws UnsupportedEncodingException {
		SecureRandom rng = new SecureRandom();
		
		byte[] userid = new byte[10];
		rng.nextBytes(userid);
		
		fakeUserId = "essa-"
				.concat(Base64.getEncoder().withoutPadding().encodeToString(userid))
				.concat("@")
				.concat(isp);
		LOGGER.trace("User ID: {}", fakeUserId);
		
		byte[] password = new byte[32];
		rng.nextBytes(password);
		fakePassword = Base64.getEncoder().withoutPadding().encodeToString(password);
		LOGGER.trace("Password: {}", fakePassword);
		
		byte[] random = new byte[16];
		rng.nextBytes(random);
		fakeNonce = bstr(Base64.getEncoder().withoutPadding().encodeToString(random).substring(0, 12));
		LOGGER.trace("Fake Nonce: {}", new String(fakeNonce, "us-ascii"));
		
		byte[] key = CryptoUtils.buildDecryptKey(LocalDate.now(), transid, otp);
		LOGGER.trace("Encrypt using {}", Codecs.hexencode(key));
		
		Encrypted u = CryptoUtils.encryptAndDigest(key, fakeNonce, bytes(fakeUserId, "utf8"));
		fakeEncUserId = u.getCiphertext();
		fakeTagUserId = u.getTag();
		
		Encrypted p = CryptoUtils.encryptAndDigest(key, fakeNonce, bytes(fakePassword, "utf8"));
		fakeEncPassword = p.getCiphertext();
		fakeTagPassword = p.getTag();
	}
	
	public static Response get(String url, LinkedHashMap<String, String> params) throws IOException {
		String urlWithParams = addRequestParameters(params, url);
		LOGGER.trace(urlWithParams.toString());
		
		JSONObject root = new JSONObject();
		
		JSONObject status = new JSONObject();
		status.put("resultcode", RegistrationService.RC_SUCCESS);
		root.put("status", status);
		
		root.put("api", params.get("api"));
		
		String version = "0.0";
		String isp = "singtel";
		for(Entry<String, Isp> ispConfig: ISP_CONFIG.entrySet()) {
			if(ispConfig.getValue().essaUrl.equals(url)) {
				isp = ispConfig.getKey();
				LOGGER.info("Found matching ISP: {}", isp);
			}
		}
		
		// register (retrieve/create)
		/*
		 * {
		 *   status: {
		 *     resultcode: 1100
		 *   },
		 *   body: {
		 *     message: "",
		 *     success_code: ""
		 *   },
		 *   api: "",
		 *   version: ""
		 * }
		 */
		JSONObject body = new JSONObject();
		
		String bodyMessage = "Message from server.";
		
		if("retrieve_user_r12x2a".equals(params.get("api"))) {
			generateRegister(isp);
			version = ISP_CONFIG.get(isp).retrieveApiVersions[0];
			body.put("success_code", fakeSuccessCode);
		}
		else if ("create_user_r12x1a".equals(params.get("api"))) {
			generateRegister(isp);
			version = ISP_CONFIG.get(isp).createApiVersions[0];
			body.put("success_code", fakeSuccessCode);
		}
		// validate (retrieve/create)
		/*
		 * {
		 *   api: "",
		 *   version: ""
		 *   status: {
		 *     resultcode: 1100
		 *   },
		 *   body: {
		 *     message: "",
		 *     userid: "",
		 *     enc_userid: "",
		 *     tag_userid: "",
		 *     enc_password: "",
		 *     tag_password: "",
		 *     iv: ""
		 *   }
		 * }
		 */		
		else if("retrieve_user_r12x2b".equals(params.get("api"))) {
			if(fakeSuccessCode == null) {
				status.put("resultcode", RegistrationService.RC_FAILURE);
				bodyMessage = "No fake success code. Validating OTP w/o Registering?";
			}
			else if(!fakeSuccessCode.equals(params.get("success_code"))) {
				status.put("resultcode", RegistrationService.RC_FAILURE);
				bodyMessage = "Success code mismatch";
			}
			else if(!fakeOtp.equals(params.get("otp"))) {
				status.put("resultcode", RegistrationService.RC_FAILURE);
				bodyMessage = "OTP mismatch";
			}
			else {
				generateValidate(isp, bstr(params.get("tid")), params.get("otp"));

				version = ISP_CONFIG.get(isp).retrieveApiVersions[1];
				body.put("userid", 		 new String(fakeUserId));
				body.put("enc_userid",   Codecs.encode(fakeEncUserId, "hex"));
				body.put("tag_userid",   Codecs.encode(fakeTagUserId, "hex"));
				body.put("enc_password", Codecs.encode(fakeEncPassword, "hex"));
				body.put("tag_password", Codecs.encode(fakeTagPassword, "hex"));
				body.put("iv",           new String(fakeNonce));
			}
		}
		else if("create_user_r12x1b".equals(params.get("api"))) {
			if(fakeSuccessCode == null) {
				status.put("resultcode", RegistrationService.RC_FAILURE);
				bodyMessage = "No fake success code. Validating OTP w/o Registering?";
			}
			else if(!fakeSuccessCode.equals(params.get("success_code"))) {
				status.put("resultcode", RegistrationService.RC_FAILURE);
				bodyMessage = "Success code mismatch";
			}
			else if(!fakeOtp.equals(params.get("otp"))) {
				status.put("resultcode", RegistrationService.RC_FAILURE);
				bodyMessage = "OTP mismatch";
			}
			else {
				generateValidate(isp, bstr(params.get("tid")), params.get("otp"));

				version = ISP_CONFIG.get(isp).createApiVersions[1];

				body.put("userid", 		 new String(fakeUserId));
				body.put("enc_userid",   Codecs.encode(fakeEncUserId, "hex"));
				body.put("tag_userid",   Codecs.encode(fakeTagUserId, "hex"));
				body.put("enc_password", Codecs.encode(fakeEncPassword, "hex"));
				body.put("tag_password", Codecs.encode(fakeTagPassword, "hex"));
				body.put("iv",           new String(fakeNonce));
			}
		}
		else {
			status.put("resultcode", RegistrationService.RC_FAILURE);
			bodyMessage = "Invalid action: " + params.get("api") + " " + RegistrationService.RC_FAILURE;
		}
		body.put("message", bodyMessage);
		
		root.put("version", version);
		root.put("body", body);
		
		String json = root.toString(4);
		
		LOGGER.trace(json);
		
		return new Response(HttpURLConnection.HTTP_OK, json);
	}
	
	public static Response get(String uri, LinkedHashMap<String, String> params, Properties headers) throws IOException {
		return get(uri, params);
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
}
