package org.python.io;

import static org.python.lang.global.VERBOSE;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.slf4j.LoggerFactory;

public class Io {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Io.class); 
	
	public static byte[] bstr(String s) {
		byte[] value = null;
		try {
			value = bytes(s, "utf8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage());
		}
		return value;
	}
	
	public static byte[] bytes(String s, String encoding) throws UnsupportedEncodingException {
		if(s == null) {
			return null;
		}
		
		Charset charset;

		switch(encoding) {
		case "ascii":
			charset = StandardCharsets.US_ASCII;
			break;
		case "iso-8859-1":
		case "latin1":
			charset = StandardCharsets.ISO_8859_1;
			break;
		case "utf16":
		case "utf-16":
			charset = StandardCharsets.UTF_16;
			break;
		case "utf-8":
		case "utf8":
			charset = StandardCharsets.UTF_8;
			break;
		default:
			charset = Charset.forName(encoding);
		}
		
		return s.getBytes(charset);
	}
	
	public static void errprint(String m) {
		LOGGER.error(m);
	}
	
	public static void LOG(String m) {
	    if (VERBOSE) errprint(m);
	}
	
	public static String input(String m) {
		String input;
		System.out.print(m);
		try (Scanner scanner = new Scanner(System.in)){
			input = scanner.nextLine();
		}
		return input;
	}
}
