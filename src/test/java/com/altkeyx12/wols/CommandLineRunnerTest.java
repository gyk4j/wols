package com.altkeyx12.wols;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import net.altkey12.wols.util.CommandLineOutputs;
import net.altkey12.wols.util.CommandLineRunner;

public class CommandLineRunnerTest {

	@Test
	public void testFail() {
		CommandLineOutputs out = CommandLineRunner.run("ping", "-c", "4", "afjaklfjlf214.com");
		assertNotEquals(0, out.getExitCode());
	}
	
	@Test
	public void testPass() {
		CommandLineOutputs out = CommandLineRunner.run("ping", "-c", "4", "google.com");
		assertEquals(0, out.getExitCode());
	}
	
	@Test
	public void testTracert() {
		CommandLineOutputs out = CommandLineRunner.run("traceroute", "-m", "5", "google.com");
		assertEquals(0, out.getExitCode());
	}

}
