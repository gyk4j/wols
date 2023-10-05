package gyk4j.wols.controller;

import static org.python.io.Io.input;
import static org.python.io.Logger.LOG;
import static org.python.io.Logger.errquit;
import static org.python.lang.global.VERBOSE;
import static org.python.lang.global.otp;
import static org.python.lang.global.successCode;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.python.lang.Argparse4jParser;
import org.python.lang.ArgumentParser;
import org.python.lang.Arguments;
import org.python.lang.error.HTTPNotFoundExn;
import org.python.lang.error.MalformedResponseExn;
import org.python.lang.error.ServerErrorExn;
import org.python.net.Credentials;
import org.python.net.ValidateOtpResult;
import org.python.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.Wols;

public class CliController extends AbstractController {
	public static final Logger LOGGER = LoggerFactory.getLogger(CliController.class);

	protected static CliController instance;
	
	public static CliController getInstance() {
		if(instance == null) {
			instance = new CliController();
			instance.initialize();
		}
		return instance;
	}
	
	protected void initialize() {
		
	}
	
	public static int main() 
			throws HTTPNotFoundExn, MalformedResponseExn, ServerErrorExn, IOException, 
			InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, 
			NoSuchPaddingException, InvalidAlgorithmParameterException, 
			IllegalBlockSizeException, BadPaddingException {

		AbstractController controller = Wols.getInstance().getController();
		
		ArgumentParser parser = new Argparse4jParser(); //new ApacheCommonsCliParser();
		Arguments args = parser.parse(controller.getArguments());
		
		VERBOSE = true;

		otp = null;
		successCode = null;

		if (args.otp == null && args.successCode == null) {
			// Begin registration phase.

			successCode = RegistrationService.requestRegistration(
					args.isp,
					args.salutation,
					args.name,
					args.gender,
					args.dob,
					args.mobile,
					args.country,
					args.email,
					args.transid,
					args.retrieveMode);

			LOG(String.format("Got success code: %s", successCode));

			if (args.registrationPhaseOnly) {
				System.out.println(String.format("Success code: %s", successCode));
				return 0;
			}

			System.out.println(String.format("OTP will be sent to mobile phone number %s", args.mobile));
			otp = input("Enter OTP to continue: ");
		}
		else {
			// Skipping registration phase, make sure we have OTP and success code.
			if (args.otp == null || args.successCode == null) {
				return errquit("Both success code and OTP must be provided to skip registration phase.");
			}

			successCode = args.successCode;
			otp = args.otp;

		}

		ValidateOtpResult r = RegistrationService.validateOtp(
				args.isp,
				args.dob,
				args.mobile,
				otp,
				successCode,
				args.transid,
				args.retrieveMode);

		Credentials credentials = RegistrationService.decryptCredentials(
				args.decryptionDate, 
				args.transid, 
				otp,
				r);

		return (credentials != null)? 0: -1;
	}
}
