package net.altkey12.wols.text;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class StringParser {
	
	public static final DateTimeFormatter localDateTimeFormatter = 
			DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy h:mm:ss a");
	
	public static UUID toUuid(String s) {
		UUID uuid = new UUID(0, 0);
		return (s == null || s.length() == 0)? uuid: UUID.fromString(s);
	}
	
	public static byte[] toMacAddress(String s) {
		if(s == null) {
			return null;
		}
		
		s = s.trim();
		
		String[] hexBytes = null;
		
		if(hexBytes == null) {
			hexBytes = s.split("[:-]");
		}
		
		if((hexBytes == null || hexBytes.length != 6 || hexBytes.length != 8) && s.length() == 12) {
			hexBytes = new String[]{
					s.substring(0, 2),
					s.substring(2, 4),
					s.substring(4, 6),
					s.substring(6, 8),
					s.substring(8, 10),
					s.substring(10, 12)
			};
		}

		if(hexBytes == null || (hexBytes.length != 6 && hexBytes.length != 8)){
			return null;
		}
		
		byte[] mac = new byte[hexBytes.length];
		
		for(int i=0; i < hexBytes.length; i++) {
			mac[i] = (byte) Integer.parseInt(hexBytes[i], 16);
		}
		
		return mac;
	}
	
	public static byte[] toIpv4SubnetMask(String s) {		
		if(s == null || s.trim().length() == 0) {
			return null;
		}
		
		int prefix = Integer.parseInt(s);
		int mask = 0xffffffff << (32 - prefix);
		
		int value = mask;
	    byte[] bytes = new byte[]{ 
	            (byte)(value >>> 24), 
	            (byte)(value >> 16 & 0xff), 
	            (byte)(value >> 8 & 0xff), 
	            (byte)(value & 0xff) 
	    };
		
		return bytes;
	}
	
	public static int toInt(String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		}
		catch(NumberFormatException e) {
			i = -1;
		}
		return i; 
	}
	
	public static long toLong(String s) {
		long l;
		try {
			l = Long.parseLong(s);
		}
		catch(NumberFormatException e) {
			l = -1;
		}
		return l; 
	}
	
	public static boolean toBoolean(String s) {
		if(s == null)
			return false;
		
		String l = s.trim().toLowerCase();
		
		switch (l) {
		case "true":
		case "yes":
		case "on":
		case "connected":
		case "enabled":
			return true;
		default:
			return false;
		}
	}
	
	public static LocalDateTime toLocalDateTime(String s) {
		return (s == null)? null : LocalDateTime.parse(s, localDateTimeFormatter);
	}
	
	public static InetAddress toInetAddress(String s) {
		InetAddress a = null;
		
		if(s == null || s.trim().length() == 0 || "--".equals(s.trim())) {
			return a;
		}
		
		s = s.replaceAll("\\(Preferred\\)", "");
		
		int subnetMaskPos = s.indexOf('/');
		if(subnetMaskPos != -1) {
			s = s.substring(0, subnetMaskPos).trim();
		}
		
		try {
			a = InetAddress.getByName(s);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return a;
	}
	
	public static byte[] toSubnetMask(String s) {
		if(s == null || s.trim().length() == 0) {
			return null;
		}
		else if(s.indexOf('/') != -1) {
			s = s.substring(s.indexOf('/')+1).trim();
		}
		
		int prefix = Integer.parseInt(s);
		int mask = 0xffffffff << (32 - prefix);
		
		int value = mask;
	    byte[] bytes = new byte[]{ 
	            (byte)(value >>> 24), 
	            (byte)(value >> 16 & 0xff), 
	            (byte)(value >> 8 & 0xff), 
	            (byte)(value & 0xff) 
	    };
		
		return bytes;
	}
}
