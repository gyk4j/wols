package org.python.service;

import static org.python.io.Io.bytes;
import static org.python.io.Logger.LOG;
import static org.python.io.Logger.errprint;
import static org.python.io.Logger.errquit;
import static org.python.lang.global.ISP_CONFIG;
import static org.python.lang.global.DEFAULT_TRANSID;
import static org.python.modules.Codecs.hexdecode;
import static org.python.modules.crypto.CryptoUtils.buildDecryptKey;
import static org.python.modules.crypto.CryptoUtils.decrypt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.python.lang.Dict;
import org.python.lang.error.HTTPNotFoundExn;
import org.python.lang.error.MalformedResponseExn;
import org.python.lang.error.ServerErrorExn;
import org.python.lang.error.ValueError;
import org.python.modules.Codecs;
import org.python.net.Credentials;
import org.python.net.Requests;
import org.python.net.Response;
import org.python.net.ValidateOtpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RegistrationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);
	
	public static final int RC_SUCCESS = 1100;
	public static final int RC_FAILURE = 9999;
	
	public static final String SG_DIAL_CODE = "65";
	
	@FunctionalInterface
	interface MalformedResponseExceptionLambda {
		void run(String m) throws MalformedResponseExn;
	}
	
	/**
	 * Helper function to validate server responses.
	 * @param resp
	 * @param key
	 * @param val
	 * @param fatal
	 * @throws MalformedResponseExn
	 */
	public static void validate(Dict resp, String key, String val, boolean fatal) throws MalformedResponseExn {
		
		MalformedResponseExceptionLambda raise = m -> { throw new MalformedResponseExn(m); };
		
		MalformedResponseExceptionLambda err = m -> { 
			if(!fatal)
				errprint("Warning: " + m);
			else
				raise.run(m);
		};
	    
	    if(!resp.contains(key)) {
	        LOG("Invalid server response: %s" + resp.toString());
	        err.run(String.format("Server response did not contain key '%s'.", key));
	    }
	    else if(val != null && !resp.getString(key).equals(val)) {
	    	LOG(String.format("Invalid server response: %s", resp.toString()));
	        err.run(String.format("Unexpected server response, key '%s' is '%s', not '%s'.",key, resp.getString(key), val));
	    }
	}

	/**
	 * Convenience method for default val and fatal parameter.
	 * @param resp
	 * @param key
	 * @throws MalformedResponseExn
	 */
	public static void validate(Dict resp, String key) throws MalformedResponseExn {
		//val=None, fatal=False
		validate(resp, key, null, false);
	}
	
	/**
	 * Convenience method for default val parameter.
	 * @param resp
	 * @param key
	 * @throws MalformedResponseExn
	 */
	public static void validate(Dict resp, String key, boolean fatal) throws MalformedResponseExn {
		//val=None
		validate(resp, key, null, fatal);
	}
	
	/**
	 * Convenience method for default fatal parameter.
	 * @param resp
	 * @param key
	 * @throws MalformedResponseExn
	 */
	public static void validate(Dict resp, String key, String val) throws MalformedResponseExn {
		//fatal=False
		validate(resp, key, val, false);
	}
	
	// Checks if the response is an error message. If so, print it out and bail.
	private static void checkForError(Dict resp) throws MalformedResponseExn, ServerErrorExn {

	    validate(resp, "status", true);
	    validate(resp.get("status"), "resultcode", true);
	    int rc = resp.get("status").getInt("resultcode");

	    if(rc != RC_SUCCESS) {
	        LOG(String.format("Server response reports an error, resultcode = %d", rc));
	        validate(resp, "body", true);

	        String msg = (resp.get("body").contains("message"))? 
	        		resp.get("body").getString("message"): "(empty)";
	        LOG(String.format("Received error message from server: %s", msg));
	        throw new ServerErrorExn(msg);
	    }
	}
	
	/**
	 * 
	 * @param isp
	 * @param salutation
	 * @param name
	 * @param gender
	 * @param dob
	 * @param mobile
	 * @param country
	 * @param email
	 * @param transid
	 * @return
	 * @throws HTTPNotFoundExn
	 * @throws MalformedResponseExn
	 * @throws IOException
	 * @throws ServerErrorExn
	 */
	public static String requestRegistration(
			String isp,
			String dob, 
			String mobile) 
					throws HTTPNotFoundExn, MalformedResponseExn, IOException, ServerErrorExn {
		return requestRegistration(
				isp,
				"Dr", 
				"Some Person", 
				"f", 
				dob, 
				SG_DIAL_CODE.concat(mobile),
				"SG", 
				"nonexistent@noaddresshere.com", 
				DEFAULT_TRANSID,
				false);
	}
	
	/**
	 * Convenience method for requestRegistration with default retrieve_mode = False
	 * @param isp
	 * @param salutation
	 * @param name
	 * @param gender
	 * @param dob
	 * @param mobile
	 * @param country
	 * @param email
	 * @param transid
	 * @throws MalformedResponseExn 
	 * @throws HTTPNotFoundExn 
	 * @throws IOException 
	 * @throws ServerErrorExn 
	 */
	public static String requestRegistration(
			String isp,
			String salutation, 
			String name, 
			String gender, 
			String dob, 
			String mobile,
			String country, 
			String email, 
			byte[] transid) 
					throws HTTPNotFoundExn, MalformedResponseExn, IOException, ServerErrorExn {
		return requestRegistration(isp,
				salutation, name, gender, dob, mobile,
				country, email, transid,
				false);
	}
	
	/**
	 * 
	 * @param isp
	 * @param salutation
	 * @param name
	 * @param gender
	 * @param dob
	 * @param mobile
	 * @param country
	 * @param email
	 * @param transid
	 * @param retrieveMode
	 * @return
	 * @throws HTTPNotFoundExn
	 * @throws MalformedResponseExn
	 * @throws IOException
	 * @throws ServerErrorExn
	 */
	public static String requestRegistration(
			String isp,
			String salutation, 
			String name, 
			String gender, 
			String dob, 
			String mobile,
			String country, 
			String email, 
			byte[] transid,
			boolean retrieveMode) 
					throws HTTPNotFoundExn, MalformedResponseExn, IOException, ServerErrorExn{
		
		String api;
		String apiVersion;
		String apiPassword = ISP_CONFIG.get(isp).apiPassword;
		
		if(retrieveMode) {
			api = "retrieve_user_r12x2a";
			apiVersion = ISP_CONFIG.get(isp).retrieveApiVersions[0];
		}
		else {
			api = "create_user_r12x1a";
			apiVersion = ISP_CONFIG.get(isp).createApiVersions[0];
		}

		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("api", api);
		params.put("api_password", apiPassword);
		params.put("salutation", salutation);
		params.put("name", name);
		params.put("gender", gender);
		params.put("dob", dob);
		params.put("mobile", mobile);
		params.put("nationality", country);
		params.put("email", email);
		params.put("tid", new String(transid, StandardCharsets.US_ASCII));
		Response r = Requests.get(ISP_CONFIG.get(isp).essaUrl, params);

		if (r.getStatusCode() != Requests.codes.ok) {
			throw new HTTPNotFoundExn(
					MessageFormat.format("Failed to make request query, status code: {0}", r.getStatusCode()));
		}

		Dict resp;
		try {
			resp = r.json();
		} catch (ValueError ex) {
			throw new MalformedResponseExn("Could not parse JSON.");
		}

		checkForError(resp);
		validate(resp, "api", api);
		validate(resp, "version", apiVersion);

		validate(resp, "body", true);
		validate(resp.get("body"), "success_code", true);

		return resp.get("body").getString("success_code");
	}
	
	/**
	 * 
	 * @param isp
	 * @param dob
	 * @param mobile
	 * @param otp
	 * @param successCode
	 * @return
	 * @throws HTTPNotFoundExn
	 * @throws MalformedResponseExn
	 * @throws ServerErrorExn
	 * @throws IOException
	 */
	public static ValidateOtpResult validateOtp(
			String isp, 
			String dob, 
			String mobile, 
			String otp, 
			String successCode) throws HTTPNotFoundExn, MalformedResponseExn, ServerErrorExn, IOException {
		return validateOtp(
				isp, 
				dob, 
				SG_DIAL_CODE.concat(mobile), 
				otp, 
				successCode, 
				DEFAULT_TRANSID, 
				false);
	}
	
	/**
	 * 
	 * @param isp
	 * @param dob
	 * @param mobile
	 * @param otp
	 * @param successCode
	 * @param transid
	 * @return
	 * @throws HTTPNotFoundExn
	 * @throws MalformedResponseExn
	 * @throws ServerErrorExn
	 * @throws IOException
	 */
	public static ValidateOtpResult validateOtp(
			String isp, 
			String dob, 
			String mobile, 
			String otp, 
			String successCode, 
			byte[] transid) throws HTTPNotFoundExn, MalformedResponseExn, ServerErrorExn, IOException {
		return validateOtp(isp, dob, mobile, otp, successCode, transid, false);
	}
	
	/**
	 * 
	 * @param isp
	 * @param dob
	 * @param mobile
	 * @param otp
	 * @param successCode
	 * @param transid
	 * @param retrieveMode
	 * @return
	 * @throws IOException
	 * @throws HTTPNotFoundExn
	 * @throws MalformedResponseExn
	 * @throws ServerErrorExn
	 */
	public static ValidateOtpResult validateOtp(
			String isp, 
			String dob, 
			String mobile, 
			String otp, 
			String successCode, 
			byte[] transid, 
			boolean retrieveMode) throws IOException, HTTPNotFoundExn, MalformedResponseExn, ServerErrorExn {
		
		String api, apiVersion; 
		String apiPassword = ISP_CONFIG.get(isp).apiPassword;
		
		if (retrieveMode) {
	        api = "retrieve_user_r12x2b";
	        apiVersion = ISP_CONFIG.get(isp).retrieveApiVersions[1];
		}
	    else {
	        api = "create_user_r12x1b";
	        apiVersion = ISP_CONFIG.get(isp).createApiVersions[1];
	    }
	    
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("api", api);
		params.put("api_password", apiPassword);
		params.put("dob", dob);
		params.put("mobile", mobile);
		params.put("otp", otp);
		params.put("success_code", successCode);
		params.put("tid", new String(transid, StandardCharsets.US_ASCII));
	    Response r = Requests.get(ISP_CONFIG.get(isp).essaUrl, params);

	    if (r.getStatusCode() != Requests.codes.ok) {
	        throw new HTTPNotFoundExn(
	        		MessageFormat.format("Failed to make validation query, status code: {0}", r.getStatusCode()));
	    }
	    
	    Dict resp;
	    try {
	        resp = r.json();
	    }
	    catch (ValueError ex) {
	        throw new MalformedResponseExn("Malformed response from server.");
	    }

	    checkForError(resp);
	    validate(resp, "api", api);
	    validate(resp, "version", apiVersion);
	    validate(resp, "body", true);
	    validate(resp.get("body"), "userid", true);
	    validate(resp.get("body"), "enc_userid", true);
	    validate(resp.get("body"), "tag_userid", true);
	    validate(resp.get("body"), "enc_password", true);
	    validate(resp.get("body"), "tag_password", true);
	    validate(resp.get("body"), "iv", true);
	    
	    return new ValidateOtpResult(
	        bytes(resp.get("body").getString("userid"), "utf8"),
	        hexdecode(resp.get("body").getString("enc_userid")),
	        hexdecode(resp.get("body").getString("tag_userid")),
	        hexdecode(resp.get("body").getString("enc_password")),
	        hexdecode(resp.get("body").getString("tag_password")),
	        bytes(resp.get("body").getString("iv"), "utf8")
	    );
	    /*
	    "userid" : bytes(resp["body"]["userid"], "utf8"),
        "enc_userid" : hexdecode(resp["body"]["enc_userid"]),
        "tag_userid" : hexdecode(resp["body"]["tag_userid"]),
        "enc_password" : hexdecode(resp["body"]["enc_password"]),
        "tag_password" : hexdecode(resp["body"]["tag_password"]),
        "nonce" : bytes(resp["body"]["iv"], "utf8")
        */
	}
	
	public static Credentials decryptCredentials(
			String otp, 
			ValidateOtpResult r) {
		return decryptCredentials(
				null,
				DEFAULT_TRANSID,
				otp,
				r);
	}
	/**
	 * 
	 * @param argsDecryptionDate
	 * @param argsTransid
	 * @param otp
	 * @param r
	 * @return
	 */
	public static Credentials decryptCredentials(String argsDecryptionDate, byte[] argsTransid, String otp, ValidateOtpResult r) {
		LocalDate decryptionDate;
		if (argsDecryptionDate != null)
			decryptionDate = LocalDate.parse(argsDecryptionDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
		else
			decryptionDate = LocalDate.now();

		LocalDate[] tryDates = new LocalDate[] {
				decryptionDate,
				decryptionDate.plusDays(1),
				decryptionDate.minusDays(1)
		};

		boolean found = false;
		byte[] key = null;
		for(LocalDate date: tryDates) {
			key = buildDecryptKey(date, argsTransid, otp);
			LOGGER.trace("Try Decrypt using {}", Codecs.hexencode(key));
			if (Arrays.equals(decrypt(key, r.getNonce(), r.getTagUserId(), r.getEncUserId()), r.getUserid())) {
				LOG(String.format("Successfully decrypted using date %s.", DateTimeFormatter.ofPattern("yyyyMMdd").format(date)));
				found = true;
				break;
			}
		}

		if (!found) {
			errquit("Decryption failed. Try a different date?");
			return null;
		}

		LOG(String.format("Decryption key: %s", Codecs.encode(key, "hex")));
		LOG(String.format("Nonce: %s", r.getNonce()));
		LOG(String.format("userid tag: %s", Codecs.encode(r.getTagUserId(), "hex")));
		LOG(String.format("password tag: %s", Codecs.encode(r.getTagPassword(), "hex")));

		byte[] password = decrypt(key, r.getNonce(), r.getTagPassword(), r.getEncPassword());

		Credentials c = new Credentials(
				new String(r.getUserid(), StandardCharsets.US_ASCII),
				new String(password, StandardCharsets.US_ASCII));
		
		System.out.println("Credentials:");
		System.out.println(String.format("\tuserid = %s", c.getUserid()));
		System.out.println(String.format("\tpassword = %s", c.getPassword()));
		
		return c;
	}
}
