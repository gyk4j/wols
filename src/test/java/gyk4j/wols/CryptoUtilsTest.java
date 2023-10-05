package gyk4j.wols;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.python.io.Io.bstr;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.python.modules.crypto.CryptoUtils;
import org.python.modules.crypto.cipher.AES;
import org.python.modules.crypto.cipher.AES.Encrypted;

public class CryptoUtilsTest {
	final static Charset charset = StandardCharsets.US_ASCII;
	final static byte[] aad = "PRESET ADDITIONAL AUTHENTICATION DATA".getBytes(charset);
	final static byte[] data = "test9876".getBytes(charset);
	final static byte[] key = new byte[] { 
			0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 
			0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
	};
	
	private AES.CipherTextMac ciphertextTag;
	private byte[] nonce;
	private byte[] decrypted;
	
	private void printAsText(String label, byte[] data) {
		System.out.print(label + " : ");
		for(int i=0; i < data.length; i++) {
			
			if(!Character.isAlphabetic(data[i]) && 
					!Character.isDigit(data[i]) && 
					data[i] != ' ') {
				System.out.print(".");
			}
			else {
				System.out.print((char)data[i]);
			}
		}
		System.out.println();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
		System.out.println("Max Key Size for AES : " + maxKeySize);
		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		AES encrypt = new AES(key, AES.MODE_CCM);
		encrypt.update(aad);
		ciphertextTag = encrypt.encrypt(data);
		nonce = encrypt.nonce;
	}

	@After
	public void tearDown() throws Exception {
		
		if(decrypted != null) {
			printAsText("Plaintext", decrypted);
		}
		else {
			System.err.println("Decryption failed.");
		}
		
		ciphertextTag = null;
		nonce = null;
		decrypted = null;
	}

	@Test
	public void testDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dMM");
		String dateHex = String.format("%03x", Integer.parseInt(LocalDate.of(2023, 1, 9).format(dtf)));
		System.out.println(dateHex);
		assertNotNull(dateHex);
	}
	
	@Test
	public void testDecryptOK() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {		
		AES decrypt = new AES(key, AES.MODE_CCM, nonce);
		decrypt.update(aad);
		decrypted = decrypt.decryptAndVerify(ciphertextTag.getCiphertext(), ciphertextTag.getMac());
		
		assertArrayEquals(data, decrypted);
	}
	
	@Test(expected = AEADBadTagException.class)
	public void testBadKey() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] badKey = new byte[] { 
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		}; // 16 bytes (128 bits)
		
		AES decrypt = new AES(badKey, AES.MODE_CCM, nonce);
		decrypt.update(aad);
		decrypted = decrypt.decryptAndVerify(ciphertextTag.getCiphertext(), ciphertextTag.getMac());
	}
	
	@Test(expected = AEADBadTagException.class)
	public void testBadNonce() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] badNonce = new byte[] { 
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
				0x00, 0x00, 0x00, 
		}; // 11 bytes (88 bits)
		
		AES decrypt = new AES(key, AES.MODE_CCM, badNonce);
		decrypt.update(aad);
		decrypted = decrypt.decryptAndVerify(ciphertextTag.getCiphertext(), ciphertextTag.getMac());
	}

	@Test(expected = AEADBadTagException.class)
	public void testBadAAD() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] badAAD = "header1".getBytes(charset);
		
		assertNotEquals(new String(aad), new String(badAAD));
		
		AES decrypt = new AES(key, AES.MODE_CCM, nonce);
		decrypt.update(badAAD);
		decrypted = decrypt.decryptAndVerify(ciphertextTag.getCiphertext(), ciphertextTag.getMac());
	}
	
	@Test(expected = AEADBadTagException.class)
	public void testBadCipherText() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] badCiphertext = ciphertextTag.getCiphertext();
		badCiphertext[badCiphertext.length-1] = (byte) (~(badCiphertext[badCiphertext.length-1]) & 0xff); // change a byte
		
		AES decrypt = new AES(key, AES.MODE_CCM, nonce);
		decrypt.update(aad);
		decrypted = decrypt.decryptAndVerify(badCiphertext, ciphertextTag.getMac());
	}
	
	@Test(expected = AEADBadTagException.class)
	public void testBadMac() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] badMac = ciphertextTag.getMac();
		badMac[badMac.length-1] = (byte) (~(badMac[badMac.length-1]) & 0xff); // change a byte
		
		AES decrypt = new AES(key, AES.MODE_CCM, nonce);
		decrypt.update(aad);
		decrypted = decrypt.decryptAndVerify(ciphertextTag.getCiphertext(), badMac);
	}
	
	@Test
	public void testEncryptDecryptCredentials() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] key = bstr("00000000000000000000000000000000");
		byte[] plainUserid = "essa-12345678901".getBytes();
		
		Encrypted encrypted = CryptoUtils.encrypt(key, plainUserid);
		
		byte[] nonce = encrypted.getNonce();
		byte[] tagUserid = encrypted.getTag();
		byte[] encUserid = encrypted.getCiphertext();
		
		byte[] decUserid = CryptoUtils.decrypt(key, nonce, tagUserid, encUserid);
		
		assertArrayEquals(plainUserid, decUserid);
	}
	
}
