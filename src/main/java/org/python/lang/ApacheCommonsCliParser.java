package org.python.lang;

import static org.python.io.Io.bstr;
import static org.python.lang.global.DEFAULT_ISP;
import static org.python.lang.global.DEFAULT_TRANSID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ApacheCommonsCliParser implements ArgumentParser {
	
	@Override
	public Arguments parse(String[] args) {
		//Option.builder("").longOpt("").hasArg().argName("").desc("").required(false).build();
		Option helpOpt = Option.builder("h").longOpt("help")
				.desc("show this help message and exit")
				.required(false).build();
		
		Option mobileOpt = Option.builder("m").longOpt("mobile")
				.hasArg().argName("MOBILE")
				.desc("Mobile phone number (e.g. 98765432)")
				.required(true).build();
		
		Option dobOpt = Option.builder("b").longOpt("dob")
				.hasArg().argName("DOB")
				.desc("Date of birth in DDMMYYYY format (e.g. 09081965)")
				.required(true).build();
		
		Option ispOpt = Option.builder("i").longOpt("isp")
				.hasArg().argName("ISP")
				.desc("ISP to register with (e.g. singtel/myrepublic)")
				.required(false).build();
		
		Option salutationOpt = Option.builder("s").longOpt("salutation")
				.hasArg().argName("SALUTATION")
				.desc("Salutation (e.g. Dr/Mr/Ms)")
				.required(false).build();
		
		Option nameOpt = Option.builder("n").longOpt("name")
				.hasArg().argName("NAME")
				.desc("Full name (e.g. John Tan/Abdul Jalil/Kumar Subramaniam")
				.required(false).build();
		
		Option genderOpt = Option.builder("g").longOpt("gender")
				.hasArg().argName("GENDER")
				.desc("Gender (e.g. m/f)")
				.required(false).build();
		
		Option countryOpt = Option.builder("c").longOpt("country")
				.hasArg().argName("COUNTRY")
				.desc("Nationality country code (e.g. SG/MY/ID/BN/TH)")
				.required(false).build();
		
		Option emailOpt = Option.builder("e").longOpt("email")
				.hasArg().argName("EMAIL")
				.desc("Email address (e.g. user@email.com)")
				.required(false).build();
		
		Option transidOpt = Option.builder("t").longOpt("transid")
				.hasArg().argName("TRANSID")
				.desc("Transaction ID")
				.required(false).build();
		
		Option registrationPhaseOnlyOpt = Option.builder("1").longOpt("registration-phase-only")
				.desc("Terminate after registration phase, returns success code.")
				.required(false).build();
		
		Option otpOpt = Option.builder("O").longOpt("otp")
				.hasArg().argName("OTP")
				.desc("OTP received on mobile. Note that if this is set, then wasg-register will skip the registration phase and move immediately to OTP validation. success-code must also be provided.")
				.required(false).build();
		
		Option successCodeOpt = Option.builder("S").longOpt("success-code")
				.hasArg().argName("SUCCESS_CODE")
				.desc("Success code received during registration phase. Note that if this is set, then wasg-register will skip the registration phase and move immediately to OTP validation. OTP must also be provided.")
				.required(false).build();
		
		Option decryptionDateOpt = Option.builder("D").longOpt("decryption-date")
				.hasArg().argName("DECRYPTION_DATE").optionalArg(true)
				.desc("Date the OTP was generated, for use in decryption, in YYMMDD format. (e.g. " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + ")")
				.required(false).build();
		
		Option retrieveModeOpt = Option.builder("r").longOpt("retrieve-mode")
				.desc("Run in retrieve mode, for existing accounts.")
				.required(false).build();
		
		Option verboseOpt = Option.builder("v").longOpt("verbose")
				.desc("Be verbose.")
				.required(false).build();
		
		// define options
        Options options = new Options();
        options.addOption(helpOpt);
        options.addOption(mobileOpt);
        options.addOption(dobOpt);
        options.addOption(ispOpt);
        options.addOption(salutationOpt);
        options.addOption(nameOpt);
        options.addOption(genderOpt);
        options.addOption(countryOpt);
        options.addOption(emailOpt);
        options.addOption(transidOpt);
        options.addOption(registrationPhaseOnlyOpt);
        options.addOption(otpOpt);
        options.addOption(successCodeOpt);
        options.addOption(decryptionDateOpt);
        options.addOption(retrieveModeOpt);
        options.addOption(verboseOpt);
     
        // define parser
        CommandLine cmd;
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helper = new HelpFormatter();
        helper.setOptionComparator(null);
        
        boolean printHelp = false;
        Arguments a = new Arguments();
        
        try {
        	cmd = parser.parse(options, args);
        	
        	printHelp = cmd.hasOption(helpOpt);
        	
        	if (cmd.hasOption(mobileOpt)) {
        		a.mobile = cmd.getOptionValue(mobileOpt);
        	}

        	if (cmd.hasOption(dobOpt)) {
        		a.dob = cmd.getOptionValue(dobOpt);
        		LocalDate.parse(a.dob, DateTimeFormatter.ofPattern("ddMMyyyy"));
        	}
        	
        	if (cmd.hasOption(ispOpt)) {
        		switch(cmd.getOptionValue(ispOpt)) {
        		case "singtel":
        		case "myrepublic":
        			a.isp = cmd.getOptionValue(ispOpt);
        			break;
        		default:
        			a.isp = DEFAULT_ISP; // default
        		}
        	}
        	else {
        		a.isp = DEFAULT_ISP; // default
        	}
        	
        	if (cmd.hasOption(salutationOpt)) {
        		String value = cmd.getOptionValue(salutationOpt);
        		a.salutation = (value != null)? value : "Dr";
        	}
        	else {
        		a.salutation = "Dr";
        	}

        	if (cmd.hasOption(nameOpt)) {
        		String value = cmd.getOptionValue(nameOpt);
        		a.name = (value != null)? value : "Some Person";
        	}
        	else {
        		a.name = "Some Person";
        	}
        	
        	if (cmd.hasOption(genderOpt)) {
        		String value = cmd.getOptionValue(genderOpt);
        		a.gender = (value != null)? value : "f";
        	}
        	else {
        		a.gender = "f";
        	}
        	
        	if (cmd.hasOption(countryOpt)) {
        		String value = cmd.getOptionValue(countryOpt);
        		a.country = (value != null)? value : "SG";
        	}
        	else {
        		a.country = "SG";
        	}
        	
        	if (cmd.hasOption(emailOpt)) {
        		String value = cmd.getOptionValue(emailOpt);
        		a.email = (value != null)? value : "nonexistent@noaddresshere.com";
        	}
        	else {
        		a.email = "nonexistent@noaddresshere.com";
        	}
        	
        	if (cmd.hasOption(transidOpt)) {
        		byte[] value = bstr(cmd.getOptionValue(transidOpt));
        		a.transid = (value != null)? value : DEFAULT_TRANSID;
        	}
        	else {
        		a.transid = DEFAULT_TRANSID;
        	}

        	a.registrationPhaseOnly = cmd.hasOption(registrationPhaseOnlyOpt);

        	if(cmd.hasOption(otpOpt)) {
        		a.otp = cmd.getOptionValue(otpOpt);
        	}
        	
        	if (cmd.hasOption(successCodeOpt)) {
        		a.successCode = cmd.getOptionValue(successCodeOpt);
        	}
        	
        	if (cmd.hasOption(decryptionDateOpt)) {
        		String value = cmd.getOptionValue(decryptionDateOpt);
        		
        		boolean useDefaultValue = false;
        		if(value != null) {
        			try {
        				LocalDate.parse(value, DateTimeFormatter.ofPattern("yyMMdd"));
        			} catch(DateTimeParseException e) {
        				useDefaultValue = true; // Unparseable date.
        			}
        		}
        		else {
        			useDefaultValue = true; // No date specified.
        		}
        		
        		if(useDefaultValue) {
        			value = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        		}
        		
        		a.decryptionDate = value;        		
        	}
        	else {
        		a.decryptionDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        	}
        	
        	a.retrieveMode = cmd.hasOption(retrieveModeOpt);

        	a.verbose = cmd.hasOption(verboseOpt);
        	
        } catch (ParseException e) {
        	System.err.println(e.getMessage());
        	printHelp = true;
        }
        
        if(printHelp) {
        	helper.printHelp(
        			78,
        			"java -jar wols-0.0.1-SNAPSHOT.jar", 
        			System.lineSeparator() + "Wireless@SG registration utility." + System.lineSeparator() + System.lineSeparator(), 
        			options, 
        			System.lineSeparator() + "Register: java -jar wols-0.0.1-SNAPSHOT.jar -m 91234567 -b 09081965 -i singtel -s Mr -n \"Peter Lim\" -g m -c SG -e peter.lim@gmail.com" + System.lineSeparator() +
        			System.lineSeparator() + "Validate: java -jar wols-0.0.1-SNAPSHOT.jar -m 91234567 -b 09081965 -O 123456 -S 888888" + System.lineSeparator(), 
        					true);
        	System.exit(-1);
        }

		return a;
	}
}
