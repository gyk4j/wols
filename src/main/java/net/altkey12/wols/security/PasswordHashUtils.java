package net.altkey12.wols.security;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import net.altkey12.wols.text.ValueFormatter;
import net.altkey12.wols.text.ValueFormatter.Padding;
import net.altkey12.wols.text.ValueFormatter.Radix;

public class PasswordHashUtils {
	public static String toNtHash(String password) {
		Charset utf16le = Charset.forName("UTF-16LE");
		
		ByteBuffer bb = utf16le.encode(password);
		
		MD4 md4 = new MD4();
		md4.update(bb);
		byte[] digest = md4.digest();
		
		String hash = ValueFormatter.from(digest, Radix.Hexadecimal, Padding.Zero, "", false);
		bb.rewind();
		return "hash:".concat(hash);
	}
	
	public static String toWpaPsk(String password, String ssid) throws NoSuchAlgorithmException, InvalidKeySpecException {
		final String ALGORITHM = "PBKDF2WithHmacSHA1";
		final int iterationCount = 4096;
		final int keyLength = 32*8; // 32 bytes to number of bits 256-bit PSK
	    final char[] chars = password.toCharArray();
	    final byte[] salt = ssid.getBytes(StandardCharsets.UTF_8);

	    PBEKeySpec spec = new PBEKeySpec(chars, salt, iterationCount, keyLength);
	    SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);

	    byte[] key = skf.generateSecret(spec).getEncoded();
	    String hash = ValueFormatter.from(key, Radix.Hexadecimal, Padding.Space, "", false);
	    return hash;
	}
}
