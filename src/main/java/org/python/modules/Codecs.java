package org.python.modules;

import org.bouncycastle.util.encoders.Hex;

public class Codecs {
	public static String encode(byte[] b, String encoding) {
		String encoded;
		
		switch(encoding) {
		case "hex":
			encoded = hexencode(b);
			break;
		default:
			encoded = null;
			break;
		}
		
		return encoded;
	}
	
	public static byte[] decode(String s, String encoding) {
		byte[] decoded = null;
		
		switch(encoding) {
		case "hex":
			decoded = hexdecode(s);
			break;
		default:
			break;
		}
		
		return decoded;
	}
	
	public static String hexencode(byte[] b) {
		return Hex.toHexString(b);
//		StringBuilder result = new StringBuilder();
//        for (byte o : b) {
//            result.append(String.format("%02x", o));
//        }
//        return result.toString();
	}
	
	public static byte[] hexdecode(String s) {
		return Hex.decode(s);
//		int len = s.length();
//	    byte[] data = new byte[len / 2];
//	    for (int i = 0; i < len; i += 2) {
//	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
//	                             + Character.digit(s.charAt(i+1), 16));
//	    }
//	    return data;
	}
}
