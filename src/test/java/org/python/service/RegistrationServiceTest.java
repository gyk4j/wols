package org.python.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.Security;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.python.lang.error.HTTPNotFoundExn;
import org.python.lang.error.MalformedResponseExn;
import org.python.lang.error.ServerErrorExn;
import org.python.net.Credentials;
import org.python.net.ValidateOtpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.net.RequestsStub;

public class RegistrationServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws HTTPNotFoundExn, MalformedResponseExn, ServerErrorExn, IOException {
		final String isp = "starhub";
		final String dob = "09081965";
		final String mobile = "6591234567";
		String successCode = RegistrationService.requestRegistration(isp, dob, mobile);
		
		LOGGER.info("Success code: {}", successCode);
		
		assertNotNull(successCode);
		assertNotEquals(0, successCode.length());
		
		ValidateOtpResult r = RegistrationService.validateOtp(
				isp, dob, mobile, RequestsStub.getFakeOTP(), successCode);
		
		assertNotNull(r);
		assertNotNull(r.getUserid());
		assertNotEquals(0, r.getUserid().length);
		assertNotNull(r.getEncUserId());
		assertNotEquals(0, r.getEncUserId().length);
		assertNotNull(r.getTagUserId());
		assertNotEquals(0, r.getTagUserId().length);
		assertNotNull(r.getEncPassword());
		assertNotEquals(0, r.getEncPassword().length);
		assertNotNull(r.getTagPassword());
		assertNotEquals(0, r.getTagPassword().length);
		assertNotNull(r.getNonce());
		assertNotEquals(0, r.getNonce().length);
		
		LOGGER.info("User ID: {}", new String(r.getUserid()));
		
		Credentials c = RegistrationService.decryptCredentials(
				RequestsStub.getFakeOTP(), r);
		
		assertNotNull(c);
		assertNotNull(c.getUserid());
		assertNotEquals(0, c.getUserid().length());
		assertNotNull(c.getPassword());
		assertNotEquals(0, c.getPassword().length());
		
		LOGGER.info("User ID: {}", c.getUserid());
		LOGGER.info("Password: {}", c.getPassword());
	}

}
