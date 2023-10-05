package org.python.modules.crypto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.python.modules.Codecs;
import org.python.modules.crypto.cipher.AES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtils.class);
	
	public static byte[] buildDecryptKey(LocalDate date, byte[] transid, String otp) {
		LOGGER.trace("Generating key: date={}, tid={}, otp={}", date, new String(transid), new String(otp));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dMM");
		String dateHex = String.format("%03x", Integer.parseInt(date.format(formatter).trim()));
//		dateHex = b"%03x" % int(date.strftime("%e%m").strip());
		String otpHex = String.format("%05x", Integer.parseInt(otp));
//		otpHex = b"%05x" % int(otp);
		String keyHex = dateHex + new String(transid) + otpHex;
		return Codecs.decode(keyHex, "hex");
	}
	
	public static byte[] decrypt(byte[] key, byte[] nonce, byte[] tag, byte[] ciphertext) {
		byte[] plaintext = new byte[0];
		try {
			AES aes = new AES(key, AES.MODE_CCM, nonce);

//			if(aad != null)
//				aes.setAAD(aad);
			
			plaintext = aes.decryptAndVerify(ciphertext, tag);

			LOGGER.trace("plaintext: {}", new String(plaintext));
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage());
		}

		return plaintext;
	}
	
	/**
	 * 
	 * @param key
	 * @param plaintext
	 * @return
	 */
	public static AES.Encrypted encrypt(byte[] key, byte[] plaintext) {
		return encrypt(key, null, null, plaintext);
	}
	
	/**
	 * 
	 * @param key
	 * @param nonce
	 * @param plaintext
	 * @return
	 */
	public static AES.Encrypted encryptAndDigest(byte[] key, byte[] nonce, byte[] plaintext) {
		return encrypt(key, nonce, null, plaintext);
	}
	
	public static AES.Encrypted encrypt(byte[] key, byte[] nonce, byte[] aad, byte[] plaintext) {
		AES.Encrypted ciphertext = new AES.Encrypted();
		try {
			AES aes = new AES(key, AES.MODE_CCM, nonce);
			
			// 
			AES.CipherTextMac ciphertextMac = aes.encrypt(plaintext);
			
			// Bundle with the randomly generated nonce, preset AAD required for decryption
			ciphertext = new AES.Encrypted(
					aes.nonce, 
					aad, 
					ciphertextMac.getCiphertext(), 
					ciphertextMac.getMac());
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage());
		}
		return ciphertext;
	}
}
