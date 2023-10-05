package com.altkeyx12.wols;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import net.altkey12.wols.text.platform.linux.PingParser;

public class PingParserTest {

	@Test
	public void test() {
		String s = 
				"PING google.com (172.217.194.102) 56(84) bytes of data.\n" + 
				"64 bytes from si-in-f102.1e100.net (172.217.194.102): icmp_seq=1 ttl=59 time=6.20 ms\n" + 
				"64 bytes from si-in-f102.1e100.net (172.217.194.102): icmp_seq=2 ttl=59 time=7.89 ms\n" + 
				"\n" + 
				"--- google.com ping statistics ---\n" + 
				"2 packets transmitted, 2 received, 0% packet loss, time 1001ms\n" + 
				"rtt min/avg/max/mdev = 6.198/7.042/7.887/0.844 ms\n";
		PingParser p = new PingParser();
		Map<String, String> m = p.parse(s);
		
//		m.forEach((k, v) -> System.out.println(String.format("%s = %s", k, v)));
		assertEquals("google.com", m.get("ping.name"));
		assertEquals("172.217.194.102", m.get("ping.ip"));
		assertEquals("64", m.get("0.bytes"));
		assertEquals("si-in-f102.1e100.net", m.get("0.name"));
		assertEquals("172.217.194.102", m.get("0.ip"));
		assertEquals("64", m.get("1.bytes"));
		assertEquals("si-in-f102.1e100.net", m.get("1.name"));
		assertEquals("172.217.194.102", m.get("1.ip"));
		assertEquals("2", m.get("packets.transmitted"));
		assertEquals("2", m.get("packets.received"));
		assertEquals("0", m.get("packets.lossPercentage"));
		assertEquals("1001", m.get("packets.timeMs"));
		assertEquals("6.198", m.get("statistics.min"));
		assertEquals("7.042", m.get("statistics.avg"));
		assertEquals("7.887", m.get("statistics.max"));
		assertEquals("0.844", m.get("statistics.mdev"));
	}

}
