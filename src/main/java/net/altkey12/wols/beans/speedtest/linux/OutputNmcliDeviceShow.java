package net.altkey12.wols.beans.speedtest.linux;

import java.net.InetAddress;

public class OutputNmcliDeviceShow {
	Device[] devices;
	
	public OutputNmcliDeviceShow(Device[] devices) {
		this.devices = devices;
	}
	
	public Device[] getDevices() {
		return devices;
	}

	public void setDevices(Device[] devices) {
		this.devices = devices;
	}

	public static class Device {
		
		public class General {
			public String device;
			public String type;
			public byte[] hwaddr = new byte[4];
			public int mtu;
			public boolean state;
			public String connection;
			public String conPath;
		}
		
		public class WifiProperties {
			public boolean _2GHz;
			public boolean _5GHz;
		}
		
		public class Ap {
			public boolean inUse;
			public String bssid;
			public String ssid;
			public String mode;
			public int chan;
			public int rate; // Mbit/s
			public int signal;
			public String bars;
			public String security;
		}
		
		public class Ip4 {
			public InetAddress[] address = new InetAddress[0];
			public byte[][] subnetMask = new byte[0][0];
			public InetAddress gateway;
			public InetAddress[] dns = new InetAddress[0];
		}
		
		public class Dhcp4 {
			public InetAddress dhcp;
			public long dhcpLeaseTime;
		}
		
		public class Ip6 {
			public InetAddress[] address = new InetAddress[0];
			public InetAddress gateway;
		}
		
		public General general = new General();
		public WifiProperties wifiProperties = new WifiProperties();
		public Ap[] aps = new Ap[0];
		public Ip4 ip4 = new Ip4();
		public Dhcp4 dhcp4 = new Dhcp4();
		public Ip6 ip6 = new Ip6();
	}
}
