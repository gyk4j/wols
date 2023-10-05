package com.altkeyx12.wols;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.python.io.Io.bstr;
import static org.python.lang.global.DEFAULT_TRANSID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.python.lang.ApacheCommonsCliParser;
import org.python.lang.ArgumentParser;
import org.python.lang.Arguments;

public class ArgumentsTest {
	ArgumentParser parser;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		parser = new ApacheCommonsCliParser();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testDefaultTransId() {
		System.out.println(DEFAULT_TRANSID.length);
		assertEquals(24, DEFAULT_TRANSID.length);
		String transid = "" + new String(DEFAULT_TRANSID) + "";
		System.out.println("transid: " + transid + "(" + transid.length() + ")");
		assertEquals(24, transid.length());
		
		assertEquals(DEFAULT_TRANSID.length, transid.length());
		assertEquals(transid, new String(DEFAULT_TRANSID));
		
		byte[] bstr = bstr("053786654500000000000000");
		assertEquals(24, bstr.length);
		System.out.println("--- BEGIN ---");
		for(byte b: bstr) {
			System.out.print((char)b);
		}
		System.out.println();
		System.out.println("--- END ---");
	}
	/*
	@Test
	public void testPrintHelp() {
		String[] args = { "-h" };
		Arguments arguments = parser.parse(args);
		assertNotNull(arguments);
	}
	*/

	@Test
	public void testFromStringArray() {
		String[] args = {
				"-mobile", "test",
				"-dob", "05052023",
				"-O", "123456"
				
		};
		Arguments arguments = parser.parse(args);
		assertNotNull(arguments);
	}
	
	@Test
	public void testRequestRegistration() {
		String[] args = {
				"-mobile", "98887777",
				"-dob", "31121980",
				"-i", "myrepublic",
				"-s", "Mr",
				"-n", "John Tan",
				"-g", "m",
				"-c", "MY",
				"-e", "john.tan@yahoo.com.my",
				"-t", "AABBCCDDEEFF000000000000",
		};
		Arguments arguments = parser.parse(args);
		assertNotNull(arguments);
		assertEquals("98887777", arguments.mobile);
		assertEquals("31121980", arguments.dob);
		assertEquals("myrepublic", arguments.isp);
		assertEquals("Mr", arguments.salutation);
		assertEquals("John Tan", arguments.name);
		assertEquals("m", arguments.gender);
		assertEquals("MY", arguments.country);
		assertEquals("john.tan@yahoo.com.my", arguments.email);
		assertArrayEquals("AABBCCDDEEFF000000000000".getBytes(), arguments.transid);
	}
	
	@Test
	public void testValidateOtp() {
		String[] args = {
				"-i", "singtel",
				"-dob", "31121980",
				"-mobile", "98887777",
				"-O", "123456",
				"-S", "success_code",
				"-t", "112233445566000000000000",
		};
		
		Arguments arguments = parser.parse(args);
		assertNotNull(arguments);
		assertEquals("singtel", arguments.isp);
		assertEquals("31121980", arguments.dob);
		assertEquals("98887777", arguments.mobile);
		assertEquals("123456", arguments.otp);
		assertEquals("success_code", arguments.successCode);
		assertArrayEquals("112233445566000000000000".getBytes(), arguments.transid);
	}
	
	@Test
	public void testDecryptionToday() {
		String[] args = {
				"-mobile", "98887777",
				"-dob", "31121980",
				"-D"	
		};
		Arguments arguments = parser.parse(args);
		assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")), arguments.decryptionDate);
	}
	
	@Test
	public void testDecryptionCustom() {
		String[] args = {
				"-mobile", "98887777",
				"-dob", "31121980",
				"-D", "230101"
		};
		Arguments arguments = parser.parse(args);
		assertEquals("230101", arguments.decryptionDate);
	}

}
