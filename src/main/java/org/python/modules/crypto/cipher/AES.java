package org.python.modules.crypto.cipher;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AES {
	private static final Logger LOGGER = LoggerFactory.getLogger(AES.class);
	
	public static final char[] NONCE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890-_".toCharArray();
	
	public static final String ALGORITHM = "AES";
	public static final String PROVIDER = "BC";
	public static final String MODE_CCM = "CCM";
	public static final int NONCE_SIZE = 12; // bytes
	public static final int MAC_LEN = 16; // bytes
	
	protected Key k;
	protected AlgorithmParameterSpec parameterSpec;
	protected Cipher cipher;
	/**
	 * Get nonce generated after calling {@link encryptAndDigest(byte[] plaintext)}.
	 * For encryption only.
	 */
	public byte[] nonce;
	
	/**
	 * Additional Authentication Data / Associated Data
	 */
	protected byte[] aad;
	
	/**
	 * For encryption. Will auto generate a nonce IV.
	 * @param key
	 * @param mode
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 */
	public AES(byte[] key, String mode) throws NoSuchAlgorithmException, NoSuchPaddingException {
		this(key, mode, null);
	}
	
	/**
	 * For decryption when nonce is provided.
	 * @param key
	 * @param mode
	 * @param nonce
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 */
	public AES(byte[] key, String mode, byte[] nonce) throws NoSuchAlgorithmException, NoSuchPaddingException {
		setKey(new SecretKeySpec(key, ALGORITHM));
		setCipher(Cipher.getInstance(ALGORITHM.concat("/").concat(mode).concat("/NoPadding")));
		
		if(nonce == null) {
			SecureRandom randomSecureRandom = SecureRandom.getInstance("SHA1PRNG");
			StringBuilder sb = new StringBuilder();
			for(int i=0; i < NONCE_SIZE; i++) {
				sb.append(NONCE_CHARS[randomSecureRandom.nextInt(NONCE_CHARS.length)]);
			}
			try {
				setNonce(sb.toString().getBytes("us-ascii"));
				LOGGER.trace("Generated nonce: {}", new String(getNonce()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}	
		}
		else {
			setNonce(nonce);
			LOGGER.trace("Using user-specified nonce: {}", new String(getNonce()));
		}
		
		setParameterSpec(new GCMParameterSpec(MAC_LEN*8, getNonce()));
		//parameterSpec = new IvParameterSpec(nonce));
	}
	
	public Key getKey() {
		return k;
	}

	public void setKey(Key k) {
		this.k = k;
	}

	public AlgorithmParameterSpec getParameterSpec() {
		return parameterSpec;
	}

	public void setParameterSpec(AlgorithmParameterSpec parameterSpec) {
		this.parameterSpec = parameterSpec;
	}

	public Cipher getCipher() {
		return cipher;
	}

	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	public byte[] getNonce() {
		return nonce;
	}

	public void setNonce(byte[] nonce) {
		this.nonce = nonce;
	}

	public byte[] getAAD() {
		return aad;
	}

	public void setAAD(byte[] aad) {
		this.aad = aad;
	}
	
	/**
	 * Encrypt without Additional Authentication Data (AAD) or Associated Data (AD)
	 * @param plaintext
	 * @param aad
	 * @return
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public CipherTextMac encrypt(byte[] plaintext) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		getCipher().init(Cipher.ENCRYPT_MODE, getKey(), getParameterSpec());
//		LOGGER.trace("Nonce equality check: {}", Arrays.equals(getNonce(), getCipher().getIV()));
		
		if(getAAD() != null)
			getCipher().updateAAD(getAAD());
		
		byte[] encrypted = getCipher().doFinal(plaintext);

		byte[] cipherText = new byte[encrypted.length-MAC_LEN];
		byte[] macTag = new byte[MAC_LEN];
		
		System.arraycopy(encrypted, 0,             		cipherText, 	0, cipherText.length);
		System.arraycopy(encrypted, cipherText.length, 	macTag,    		0, macTag.length);
		
		return new CipherTextMac(cipherText, macTag);
	}
	
	/**
	 * By right it should call updateAAD() method, however, in Java/JCE, the cipher
	 * must be .init() first before updateAAD() can be called to set the AAD. If not,
	 * a IllegalStateException will be thrown because the cipher is not initialized yet.
	 * So we silently save it first, before it is actually used during encrypt / decrypt
	 * after init.
	 */
	public void update(byte[] aad) {
		setAAD(aad);
	}
	
	/**
	 * Decrypt with Additional Authentication Data (AAD) or Associated Data (AD)
	 * @param ciphertext
	 * @param aad
	 * @return
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 */
	public byte[] decrypt(byte[] ciphertext) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		getCipher().init(Cipher.DECRYPT_MODE, k, parameterSpec); //new IvParameterSpec(nonce));
//		LOGGER.trace("Nonce equality check: {}", Arrays.equals(getNonce(), getCipher().getIV()));
		
		if(getAAD() != null)
			getCipher().updateAAD(getAAD());
		
		getCipher().update(ciphertext);
		
		byte[] plaintext = getCipher().doFinal();
		
		return plaintext;
	}
	
	/**
	 * 
	 * @param cipherText
	 * @param macTag
	 * @return
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] decryptAndVerify(byte[] cipherText, byte[] macTag) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		int length = cipherText.length + macTag.length;
		byte[] C = ByteBuffer.allocate(length).put(cipherText).put(macTag).array();
		return decrypt(C);
	}	
	
	public static class CipherTextMac {
		byte[] ciphertext;
		byte[] mac;
		
		public static CipherTextMac from(byte[] encrypted) {
			int cipherLength = encrypted.length - MAC_LEN;
			byte[] C = new byte[cipherLength];
			byte[] T = new byte[MAC_LEN];
			
			System.arraycopy(encrypted, 0,			C,	0,	C.length);
			System.arraycopy(encrypted, C.length, 	T,	0,	T.length);
			
			return new CipherTextMac(C, T);
		}
		
		public static CipherTextMac from(byte[] C, byte[] T) {
			return new CipherTextMac(C, T);
		}
		
		public byte[] getBytes() {
			
			byte[] C = ciphertext;
			byte[] T = mac;
			
			int length = getCiphertext().length + getMac().length;
			
			byte[] bytes = new byte[length];
			
			System.arraycopy(C, 0,	bytes,	0,			C.length);
			System.arraycopy(T, 0, 	bytes,	C.length,	T.length);
			
			return bytes;
		}
		
		protected CipherTextMac(byte[] ciphertext, byte[] tag) {
			super();
			this.ciphertext = ciphertext;
			this.mac = tag;
		}
		public byte[] getCiphertext() {
			return ciphertext;
		}
		public void setCiphertext(byte[] ciphertext) {
			this.ciphertext = ciphertext;
		}
		public byte[] getMac() {
			return mac;
		}
		public void setMac(byte[] mac) {
			this.mac = mac;
		}
	}
	
	public static class Encrypted {
		byte[] nonce;
		byte[] header;
		byte[] ciphertext;
		byte[] tag;
		
		public Encrypted() {
			super();
			this.nonce = new byte[0];
			this.header = new byte[0];
			this.ciphertext = new byte[0];
			this.tag = new byte[0];
		}
		
		public Encrypted(byte[] nonce, byte[] header, byte[] ciphertext, byte[] tag) {
			super();
			this.nonce = nonce;
			this.header = header;
			this.ciphertext = ciphertext;
			this.tag = tag;
		}
		public byte[] getNonce() {
			return nonce;
		}
		public void setNonce(byte[] nonce) {
			this.nonce = nonce;
		}
		public byte[] getHeader() {
			return header;
		}
		public void setHeader(byte[] header) {
			this.header = header;
		}
		public byte[] getCiphertext() {
			return ciphertext;
		}
		public void setCiphertext(byte[] ciphertext) {
			this.ciphertext = ciphertext;
		}
		public byte[] getTag() {
			return tag;
		}
		public void setTag(byte[] tag) {
			this.tag = tag;
		}		
	}
}
