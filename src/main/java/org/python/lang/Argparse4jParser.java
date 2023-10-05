package org.python.lang;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import static net.sourceforge.argparse4j.impl.Arguments.storeTrue;

import static org.python.lang.global.ISP_CONFIG;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.python.lang.global.DEFAULT_ISP;
import static org.python.lang.global.DEFAULT_TRANSID;
import static org.python.io.Io.bytes;

public class Argparse4jParser implements org.python.lang.ArgumentParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Argparse4jParser.class);

	@Override
	public Arguments parse(String[] args) {
		net.sourceforge.argparse4j.inf.ArgumentParser parser = 
				ArgumentParsers.newFor("java -jar wols-0.0.1-SNAPSHOT.jar").build()
                .defaultHelp(true)
                .description("Wireless@SG registration utility.");
		
		parser.addArgument("mobile")
		.type(String.class)
		.help("Mobile phone number");
		
		parser.addArgument("dob")
		.type(String.class)
		.help("Date of birth in DDMMYYYY format");
		
		parser.addArgument("-I", "--isp")
		.type(String.class)
		.choices(ISP_CONFIG.keySet().stream().filter(k -> "singtel".equals(k) || "starhub".equals(k)).collect(Collectors.toList()))
		.setDefault(DEFAULT_ISP)
		.help("ISP to register with");
		
		parser.addArgument("-s", "--salutation")
		.type(String.class)
		.setDefault("Dr")
		.help("Salutation");
		
		parser.addArgument("-n", "--name")
		.type(String.class)
		.setDefault("Some Person")
		.help("Full name");
		
		parser.addArgument("-g", "--gender")
		.type(String.class)
		.setDefault("f")
		.help("Gender");
		
		parser.addArgument("-c", "--country")
		.type(String.class)
		.setDefault("SG")
		.help("Nationality country code");
		
		parser.addArgument("-e", "--email")
		.type(String.class)
		.setDefault("nonexistent@noaddresshere.com")
		.help("Email address");
		
		parser.addArgument("-t", "--transid")
		.type(String.class)
		.setDefault(new String(DEFAULT_TRANSID, StandardCharsets.US_ASCII))
		.help("Transaction ID");
		
		parser.addArgument("-1", "--registration-phase-only")
		.action(storeTrue())
		.help("Terminate after registration phase, returns success code.");
		
		parser.addArgument("-O", "--otp")
		.type(String.class)
		.help("OTP received on mobile. Note that if this is set, then wasg-register will skip the registration phase and move immediately to OTP validation. success-code must also be provided.");
		
		parser.addArgument("-S", "--success-code")
		.type(String.class)
		.help("Success code received during registration phase. Note that if this is set, then wasg-register will skip the registration phase and move immediately to OTP validation. OTP must also be provided.");
		
		parser.addArgument("-D", "--decryption-date")
		.type(String.class)
		.help("Date the OTP was generated, for use in decryption, in YYMMDD format.");
		
		parser.addArgument("-r", "--retrieve-mode")
		.action(net.sourceforge.argparse4j.impl.Arguments.storeTrue())
		.help("Run in retrieve mode, for existing accounts.");
		
		parser.addArgument("-v", "--verbose")
		.action(net.sourceforge.argparse4j.impl.Arguments.storeTrue())
		.help("Be verbose.");
		
		Arguments a = new Arguments();
		
		try {
			Namespace ns = parser.parseArgs(args);
			
			a.mobile = ns.getString("mobile");
			a.dob = ns.getString("dob");
			a.isp = ns.getString("isp");
			a.salutation = ns.getString("salutation");
			a.name = ns.getString("name");
			a.gender = ns.getString("gender");
			a.country = ns.getString("country");
			a.email = ns.getString("email");
			a.transid = bytes(ns.getString("transid"), "us-ascii");
			a.registrationPhaseOnly = ns.getBoolean("registration_phase_only");
			a.otp = ns.getString("otp");
			a.successCode = ns.getString("success_code");
			a.decryptionDate = ns.getString("decryption_date");
			a.retrieveMode = ns.getBoolean("retrieve_mode");
			a.verbose = ns.getBoolean("verbose");
		} catch (ArgumentParserException e) {
			parser.handleError(e);
			System.exit(0);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage());
			System.exit(-1);
		}
		
		return a;
	}

}
