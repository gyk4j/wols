package gyk4j.wols;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Test;

import gyk4j.wols.repository.linux.WpaSupplicantConfBuilder;
import gyk4j.wols.security.PasswordHashUtils;

public class WpaSupplicantConfBuilderTest {
	@Test
	public void testHashMySecretPassword() throws NoSuchAlgorithmException {
		String hash = PasswordHashUtils.toNtHash("MySecretPassword");
		assertEquals("hash:f38de32ad5224f05be73c6f542266937", hash);		
	}
	
	@Test
	public void testHashPassword() throws NoSuchAlgorithmException {
		String password = PasswordHashUtils.toNtHash("password");
		assertEquals("hash:8846f7eaee8fb117ad06bdd830b7586c", password);
	}
	
	@Test
	public void testHashRandom() throws NoSuchAlgorithmException {
		String random = PasswordHashUtils.toNtHash("random");
		assertEquals("hash:cbe8830064087367b0228dff2d88b248", random);
	}
	
	@Test
	public void testHashWpaPsk() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String psk = PasswordHashUtils.toWpaPsk("WLAN_PASSWORD", "WLAN_NAME");
		assertEquals("26df251bcd9b7be94233691ee676b581028e34052f13aff3c2a73122be1eea0f", psk);
	}
	
	@Test
	public void testHashWpaPsk2() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String psk = PasswordHashUtils.toWpaPsk("my_very_secret_passphrase", "myssid");
		assertEquals("ccb290fd4fe6b22935cbae31449e050edd02ad44627b16ce0151668f5f53c01b", psk);
	}
	
	@Test
	public void testHashWpaPsk3() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String psk = PasswordHashUtils.toWpaPsk("mypassphrase", "myrouter");
		assertEquals("8ada1f8dbea59704ac379538b4d9191f6a72390581b4cd7a72864cea685b1a7f", psk);
	}
	
	@Test
	public void testHashWpaPsk4() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String psk = PasswordHashUtils.toWpaPsk("hello", "world");
		assertEquals("e4a13c7fdcce96bde5b3d2779e13088a41bc4400faea97ca2041c0970076a713", psk);
	}
	
	@Test
	public void testWpaSupplicantConfBuilder() {
		final String ssid="Wireless@SGx", identity="92345678", password="!#!P_a^s&s|w-o+rd!@!";
		final String hash = PasswordHashUtils.toNtHash(password);
		
		WpaSupplicantConfBuilder builder = new WpaSupplicantConfBuilder();
		
		String conf = builder.ssid(ssid).identity(identity).password(hash).build();
		System.out.println(conf);
		String expected = "network={" + System.lineSeparator() + 
				"    ssid=\"" + ssid + "\"" + System.lineSeparator() + 
				"    priority=0" + System.lineSeparator() + 
				"    key_mgmt=WPA-EAP" + System.lineSeparator() + 
				"    eap=PEAP" + System.lineSeparator() + 
				"    identity=\"" + identity + "\"" + System.lineSeparator() + 
				"    password=\"" + hash + "\"" + System.lineSeparator() + 
				"    phase1=\"peaplabel=0\"" + System.lineSeparator() + 
				"    phase2=\"auth=MSCHAPV2\"" + System.lineSeparator() + 
				"    ca_cert=\"/etc/ssl/certs/ca-certificates.crt\"" + System.lineSeparator() + 
				"    ca_cert2=\"/etc/ssl/certs/ca-certificates.crt\"" + System.lineSeparator() + 
				"    ca_path=\"/etc/ssl/certs\"" + System.lineSeparator() + 
				"    ca_path2=\"/etc/ssl/certs\"" + System.lineSeparator() + 
				"}";
		assertEquals(expected, conf);
		
	}
}
