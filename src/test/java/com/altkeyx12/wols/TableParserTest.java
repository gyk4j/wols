package com.altkeyx12.wols;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import net.altkey12.wols.text.TableParser;
import net.altkey12.wols.util.CommandLineOutputs;
import net.altkey12.wols.util.CommandLineRunner;

public class TableParserTest {

	@Test
	public void testParse() {
		String data = 
				"Column1 Column2    Column3         Column4" + System.lineSeparator() + 
				"Value1  Value   2  Value.........3 Value         4";
		Map<String, String> map = new TableParser().parse(data);
		
		map.forEach((k, v) -> {
			System.out.println(k + "=" + v);
		});
		
		assertTrue(map.containsKey("Column1"));
		assertTrue(map.containsKey("Column2"));
		assertTrue(map.containsKey("Column3"));
		assertTrue(map.containsKey("Column4"));
		
		assertTrue(map.containsValue("Value1"));
		assertTrue(map.containsValue("Value   2"));
		assertTrue(map.containsValue("Value.........3"));
		assertTrue(map.containsValue("Value         4"));
		
		assertEquals(map.get("Column1"), "Value1");
		assertEquals(map.get("Column2"), "Value   2");
		assertEquals(map.get("Column3"), "Value.........3");
		assertEquals(map.get("Column4"), "Value         4");
	}
	
	@Test
	public void testParse1() {
		CommandLineOutputs o = CommandLineRunner.run("wmic", "computersystem", "get", "manufacturer,model");
		String data = o.getStdout();
		System.out.println(data);
		Map<String, String> map = new TableParser().parse(data);
		
		assertEquals("Hewlett-Packard", map.get("Manufacturer"));
		assertEquals("HP EliteBook 2560p", map.get("Model"));
	}

	@Test
	public void testParse2() {
		CommandLineOutputs o = CommandLineRunner.run("wmic", "os", "get", "name,version,BuildNumber");
		String data = o.getStdout();
		System.out.println(data);
		Map<String, String> map = new TableParser().parse(data);
		assertEquals("7601", map.get("BuildNumber"));
		assertEquals("Microsoft Windows 7 Professional |C:\\Windows|\\Device\\Harddisk0\\Partition2", map.get("Name"));
		assertEquals("6.1.7601", map.get("Version"));
	}
}
