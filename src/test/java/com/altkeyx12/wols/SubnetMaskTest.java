package com.altkeyx12.wols;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class SubnetMaskTest {

	@Test
	public void testToString() throws UnknownHostException {
		String addr = "192.168.1.1/22";
		String[] parts = addr.split("/");
	    String ip = parts[0];
	    int prefix;
	    if (parts.length < 2) {
	        prefix = 0;
	    } else {
	        prefix = Integer.parseInt(parts[1]);
	    }
	    int mask = 0xffffffff << (32 - prefix);
	    System.out.println("Prefix=" + prefix);
	    System.out.println("Address=" + ip);

	    int value = mask;
	    byte[] bytes = new byte[]{ 
	            (byte)(value >>> 24), (byte)(value >> 16 & 0xff), (byte)(value >> 8 & 0xff), (byte)(value & 0xff) };

	    InetAddress netAddr = InetAddress.getByAddress(bytes);
	    System.out.println("Mask=" + netAddr.getHostAddress());
	    assertEquals("255.255.252.0", netAddr.getHostAddress());
	}

}
