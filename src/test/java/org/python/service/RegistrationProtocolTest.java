package org.python.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.python.lang.global.DEFAULT_TRANSID;

import java.io.IOException;
import java.security.Security;

import org.junit.BeforeClass;
import org.junit.Test;
import org.python.lang.error.HTTPNotFoundExn;
import org.python.lang.error.MalformedResponseExn;
import org.python.lang.error.ServerErrorExn;
import org.python.net.ValidateOtpResult;

public class RegistrationProtocolTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	@Test
	public void testRequestRegistration() {
		String successCode = null;
		try {
			successCode = RegistrationService.requestRegistration(
					"starhub", 						// isp 
					"Mr",							// salutation 
					"Lee Min Ho",					// name 
					"m",							// gender 
					"09081965", 					// dob
					"91234567", 					// phone
					"SG", 							// country
					"lky@pmo.gov.sg", 				//email, 
					DEFAULT_TRANSID, 				//transid,
					false 							//retrieveMode
			);
		} catch (HTTPNotFoundExn e) {
			e.printStackTrace();
		} catch (MalformedResponseExn e) {
			e.printStackTrace();
		} catch (ServerErrorExn e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertNotNull(successCode);
		assertNotEquals(0, successCode.length());
		assertTrue(successCode.length() > 0);
	}
	
	@Test
	public void testValidateOtp() {
		ValidateOtpResult r = null;
		try {
			String successCode = "";
			r = RegistrationService.validateOtp(
					"singtel", 						//isp, 
					"09081965",						//dob, 
					"91234567",						//mobile, 
					"999999",						//otp, 
					successCode, 					//successCode, 
					DEFAULT_TRANSID					//transid
			);
		} catch (HTTPNotFoundExn e) {
			e.printStackTrace();
		} catch (MalformedResponseExn e) {
			e.printStackTrace();
		} catch (ServerErrorExn e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertNotNull(r);
		assertNotNull(r.getUserid());
		assertNotNull(r.getTagUserId());
		assertNotNull(r.getEncUserId());
		assertNotNull(r.getTagPassword());
		assertNotNull(r.getEncPassword());
		assertNotNull(r.getNonce());
	}

}
