package com.altkeyx12.wols;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.junit.Test;

public class PkexecTest {
	@Test
	public void parseUuid() {
		UUID uuid = UUID.fromString("aed4ba36-436a-11e2-8929-65c45f0620f8");
		assertNotNull(uuid);
	}
	

	@Test
	public void test() {
		try {
			
			Process p = Runtime.getRuntime().exec("pkexec cat /sys/class/dmi/id/product_uuid");
						
			InputStream is = p.getInputStream();
			int read;
			StringBuilder sb = new StringBuilder();
			while((read = is.read()) != -1) {
				sb.append((char)read);
			}
			is.close();
			
			InputStream es = p.getErrorStream();
			while((read = es.read()) != -1) {
//				System.err.print((char)read);
			}
			is.close();
			
			int exit = p.waitFor();
			String machineId = sb.toString().trim();
			System.out.println("Exit code: " + exit + ", " + UUID.fromString(machineId));
			assertEquals(0, exit);
		} catch (IOException e) {
			e.printStackTrace();
			assertNull(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
			assertNull(e);
		}
	}

}
