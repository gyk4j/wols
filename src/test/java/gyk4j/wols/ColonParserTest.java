package gyk4j.wols;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import gyk4j.wols.text.ColonParser;
import gyk4j.wols.util.CommandLineOutputs;
import gyk4j.wols.util.CommandLineRunner;

public class ColonParserTest {

	@Test
	public void testParse() {
		CommandLineOutputs o = CommandLineRunner.run("netsh", "wlan", "show", "interfaces");
		String data = o.getStdout();
		
		if (o.getExitCode() != 0) {
			System.err.println(o.getStdout());
		} else {
			Map<String, String> map = new ColonParser().parse(data);

			map.forEach((k, v) -> {
				System.out.println("[testParse] " + k + "=" + v);
			});
			
			assertEquals("Wireless Network Connection 2", map.get("There is 1 interface on the system/Name"));
			assertEquals("Intel(R) Centrino(R) Advanced-N 6205", map.get("There is 1 interface on the system/Description"));
		}
	}
	
	@Test
	public void testParseNetsh() {
		CommandLineOutputs o = CommandLineRunner.run("netsh", "wlan", "show", "networks", "mode=bssid");
		
		if (o.getExitCode() != 0) {
			System.err.println(o.getStdout());
		} else {
			String data = o.getStdout();
			Map<String, String> map = new ColonParser().parse(data);

			map.forEach((k, v) -> {
				System.out.println("[testParseNetsh] " + k + "=" + v);
			});
		}
	}
	
	@Test
	public void testIpconfigParse() {
		CommandLineOutputs o = CommandLineRunner.run("ipconfig", "/all");
		if (o.getExitCode() != 0) {
			System.err.println(o.getStdout());
		} else {
			String data = o.getStdout();
			Map<String, String> map = new ColonParser().parse(data);

			map.forEach((k, v) -> {
				System.out.println("[testIpconfigParse] " + k + "=" + v);
			});
		}
	}
	
	@Test
	public void testPingParse() {
		CommandLineOutputs o = CommandLineRunner.run("ping", "-c", "4", "127.0.0.1");
		if (o.getExitCode() != 0) {
			System.err.println(o.getStdout());
		} else {
			String data = o.getStdout();
			Map<String, String> map = new ColonParser().parse(data);

			map.forEach((k, v) -> {
				System.out.println("[testPingParse] " + k + "=" + v);
			});
		}
	}
	
	@Test
	public void testCharCount() {
		long count = "Packets: Sent = 4, Received = 4, Lost = 0 (0% loss),".chars().filter(i -> i == '=').count();
		
		assertEquals(3, count);
	}

}
