package com.altkeyx12.wols;

import static org.junit.Assert.*;

import org.junit.Test;

import net.altkey12.wols.io.HtmlEncoder;

public class HtmlEncoderTest {

	@Test
	public void testStripHtml() {
		String s = HtmlEncoder.stripHtml("<html>No underline <u><font color=#c0c0c0>This is a test.</font></u></html>");
		assertEquals("No underline This is a test.", s);
	}

}
