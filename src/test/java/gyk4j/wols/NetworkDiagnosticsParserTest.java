package gyk4j.wols;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import gyk4j.wols.text.StringParser;

public class NetworkDiagnosticsParserTest {

	@Test
	public void testMacColon() {
		byte[] mac = StringParser.toMacAddress("60:67:20:1e:cd:00");
		assertEquals(6, mac.length);
	}
	
	@Test
	public void testMacHyphen() {
		byte[] mac = StringParser.toMacAddress("60-67-20-1e-cd-00");
		assertEquals(6, mac.length);
	}
	
	@Test
	public void testMacNone() {
		byte[] mac = StringParser.toMacAddress("6067201ecd00");
		assertEquals(6, mac.length);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNotMac() {
		byte[] mac = StringParser.toMacAddress(Integer.toString(Integer.MAX_VALUE));
		assertNull(mac);
	}
	
	@Test
	public void testLocalDateTime() {
		LocalDateTime ldt = StringParser.toLocalDateTime("Thursday, April 20, 2023 1:26:52 PM");
		assertNotNull(ldt);
	}
	
	@Test
	public void testHardwareAddress() {
		byte[] mac = StringParser.toMacAddress("00-00-00-00-00-00-00-E0");
		assertEquals(8, mac.length);
	}

}
