package net.altkey12.wols.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.python.lang.global.DEFAULT_TRANSID;

import org.python.lang.error.HTTPNotFoundExn;
import org.python.lang.error.MalformedResponseExn;
import org.python.lang.error.ServerErrorExn;
import org.python.net.Credentials;
import org.python.net.ValidateOtpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationService {
	private static Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);
	
	public static String register(
			String isp, 
			String dob, 
			String mobile) {
		
		String successCode = null;
		
		try {
			successCode = org.python.service.RegistrationService.requestRegistration(isp, dob, mobile);
		} catch (HTTPNotFoundExn e) {
			LOGGER.error(e.getMessage());
		} catch (MalformedResponseExn e) {
			LOGGER.error(e.getMessage());
		} catch (ServerErrorExn e) {
			LOGGER.error(e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		
		return successCode;
	}
	
	public static ValidateOtpResult validate(
			String isp, 
			String dob, 
			String mobile, 
			String otp, 
			String successCode) {
		ValidateOtpResult r = null;
		
		try {
			r = org.python.service.RegistrationService.validateOtp(
					isp, 
					dob, 
					mobile, 
					otp, 
					successCode);
		} catch (HTTPNotFoundExn e) {
			LOGGER.error(e.getMessage());
		} catch (MalformedResponseExn e) {
			LOGGER.error(e.getMessage());
		} catch (ServerErrorExn e) {
			LOGGER.error(e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		
		return r;
	}
	
	public static Credentials decryptAccountInfo(
			String otp,
			ValidateOtpResult r) {
		
		Credentials c;
		c = org.python.service.RegistrationService.decryptCredentials(
				LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), // use today's date.
				DEFAULT_TRANSID,
				otp, 
				r);
		
		return c;
	}
}
