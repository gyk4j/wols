package gyk4j.wols.text;

import java.net.InetAddress;

public class ValueFormatter {
	public enum Radix {
		Decimal,
		Hexadecimal
	}
	
	public enum Padding {
		None,
		Space,
		Zero
	}
	
	public static String from(InetAddress a) {
		String ip;
		
		if(a != null) {
			ip = a.toString();
			
			String[] tokens = ip.split("/");
			
			if(tokens[1].equals("0.0.0.0")){
				ip = tokens[0];
			}
			else {
				ip = tokens[1];
			}
		}
		else {
			ip = StdoutExtractor.UNAVAILABLE;
		}
		return ip;
	}
	
	public static String from(byte[] ba) {
		return from(ba, Radix.Hexadecimal);
	}
	
	public static String from(byte[] ba, Radix radix) {
		return from(ba, radix, Padding.None);
	}
	
	public static String from(byte[] ba, Radix radix, Padding padding) {
		return from(ba, radix, padding, null);
	}
	
	public static String from(byte[] ba, Radix radix, Padding padding, String separator) {
		return from(ba, radix, padding, separator, true);
	}
	
	public static String from(byte[] ba, Radix radix, Padding padding, String separator, boolean uppercase) {
		StringBuilder sb = new StringBuilder();
		
		if (ba != null) {
			for (int i = 0; i < ba.length; i++) {
				byte b = ba[i];

				if (separator != null && i > 0) {
					sb.append(separator);
				}

				StringBuilder formatBuilder = new StringBuilder();
				formatBuilder.append('%');
				if(radix == Radix.Hexadecimal) {
					if(padding == Padding.Zero) {
						formatBuilder.append("02");
					}
					else if(padding == Padding.Space) {
						formatBuilder.append('2');
					}
					formatBuilder.append((uppercase)? 'X': 'x');
				}
				else if(radix == Radix.Decimal) {
					if(padding == Padding.Zero) {
						formatBuilder.append("03");
					}
					else if(padding == Padding.Space) {
						formatBuilder.append('3');
					}
					
					formatBuilder.append('d');
				}
				else {
					throw new IllegalArgumentException("Unsupported radix: " + radix);
				}
				
				String format = formatBuilder.toString();
				sb.append(String.format(format, b & 0xff));
			}
		}
		else {
			sb.append(StdoutExtractor.UNAVAILABLE);
		}
		
		return sb.toString();
	}
}
