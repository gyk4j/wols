package com.altkeyx12.wols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

import org.junit.Test;

public class Slf4jTest {

	static final Logger LOGGER = LoggerFactory.getLogger(Slf4jTest.class);
	
	@Test
	public void testHelloWorld() {
		LOGGER.trace("trace");
		LOGGER.debug("debug");
		LOGGER.info("Hello World");
		LOGGER.warn("Warning");
		LOGGER.error("Error");
		assertNotNull(LOGGER);
	}

}
