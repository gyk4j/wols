package com.altkeyx12.wols;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.altkey12.wols.text.platform.linux.NmcliConnectionAddParser;

public class NmcliConnectionAddParserTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void test() {
		Map<String, String> out = new NmcliConnectionAddParser()
		.parse("Connection 'hotspot_20230529034038' (b4674b88-5337-4001-8d35-290d6108dd15) successfully added.");
		
		assertNotNull(out.get("connection.id"));
		assertNotNull(out.get("connection.uuid"));
		assertEquals("hotspot_20230529034038", out.get("connection.id"));
		assertEquals("b4674b88-5337-4001-8d35-290d6108dd15", out.get("connection.uuid"));
	}

}
