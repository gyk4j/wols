package com.altkeyx12.wols;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.junit.Test;

public class SudoTest {

	@Test
	public void test() {
		try {
			Process p = Runtime.getRuntime().exec("sudo -S cat /sys/class/dmi/id/product_uuid");
			
			OutputStream os = p.getOutputStream();
			byte[] password = "hp2560p\n".getBytes();
			os.write(password);
			os.flush();
			os.close();
			
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
			System.out.println("Exit code: " + exit + ", " + UUID.fromString(sb.toString()));
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
