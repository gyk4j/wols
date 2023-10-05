package gyk4j.wols.beans.speedtest.windows;

import java.net.InetAddress;
import java.time.LocalDateTime;

public class OutputIpconfigAll {
	WindowsIpConfiguration windowsIpConfiguration;
	Connection[] connections;
	
	/**
	 * 
	 * @param windowsIpConfiguration
	 * @param connections
	 */
	public OutputIpconfigAll(WindowsIpConfiguration windowsIpConfiguration, Connection[] connections) {
		super();
		this.windowsIpConfiguration = windowsIpConfiguration;
		this.connections = connections;
	}
	
	public WindowsIpConfiguration getWindowsIpConfiguration() {
		return windowsIpConfiguration;
	}

	public void setWindowsIpConfiguration(WindowsIpConfiguration windowsIpConfiguration) {
		this.windowsIpConfiguration = windowsIpConfiguration;
	}

	public Connection[] getConnections() {
		return connections;
	}

	public void setConnections(Connection[] connections) {
		this.connections = connections;
	}

	public static class WindowsIpConfiguration {
		String hostName;
		String primaryDnsSuffix;
		String nodeType;
		boolean ipRoutingEnabled;
		boolean winsProxyEnabled;
		
		/**
		 * 
		 * @param hostName computer name
		 * @param primaryDnsSuffix May be ""
		 * @param nodeType Hybrid?
		 * @param ipRoutingEnabled Yes/No
		 * @param winsProxyEnabled Yes/No
		 */
		public WindowsIpConfiguration(String hostName, String primaryDnsSuffix, String nodeType,
				boolean ipRoutingEnabled, boolean winsProxyEnabled) {
			super();
			this.hostName = hostName;
			this.primaryDnsSuffix = primaryDnsSuffix;
			this.nodeType = nodeType;
			this.ipRoutingEnabled = ipRoutingEnabled;
			this.winsProxyEnabled = winsProxyEnabled;
		}

		public String getHostName() {
			return hostName;
		}

		public void setHostName(String hostName) {
			this.hostName = hostName;
		}

		public String getPrimaryDnsSuffix() {
			return primaryDnsSuffix;
		}

		public void setPrimaryDnsSuffix(String primaryDnsSuffix) {
			this.primaryDnsSuffix = primaryDnsSuffix;
		}

		public String getNodeType() {
			return nodeType;
		}

		public void setNodeType(String nodeType) {
			this.nodeType = nodeType;
		}

		public boolean isIpRoutingEnabled() {
			return ipRoutingEnabled;
		}

		public void setIpRoutingEnabled(boolean ipRoutingEnabled) {
			this.ipRoutingEnabled = ipRoutingEnabled;
		}

		public boolean isWinsProxyEnabled() {
			return winsProxyEnabled;
		}

		public void setWinsProxyEnabled(boolean winsProxyEnabled) {
			this.winsProxyEnabled = winsProxyEnabled;
		}
	}
	
	public static class Connection {
		String name;
		boolean mediaState;
		String connectionSpecificDnsSuffix;
		String description;
		byte[] physicalAddress;
		boolean dhcpEnabled;
		boolean autoconfigurationEnabled;
		InetAddress linkLocalIpv6Address;
		InetAddress ipv4Address;
		byte[] subnetMask;
		LocalDateTime leaseObtained;
		LocalDateTime leaseExpires;
		InetAddress defaultGateway;
		InetAddress dhcpServer;
		int dhcpV6Iaid;
		String dhcpV6ClientDuid;
		InetAddress[] dnsServers;
		boolean netBiosOverTcpip;
		
		/**
		 * 
		 * @param name
		 * @param mediaState
		 * @param connectionSpecificDnsSuffix
		 * @param description
		 * @param physicalAddress
		 * @param dhcpEnabled
		 * @param autoconfigurationEnabled
		 * @param linkLocalIpv6Address
		 * @param ipv4Address
		 * @param subnetMask
		 * @param leaseObtained
		 * @param leaseExpires
		 * @param defaultGateway
		 * @param dhcpServer
		 * @param dhcpV6Iaid
		 * @param dhcpV6ClientDuid
		 * @param dnsServers
		 * @param netBiosOverTcpip
		 */
		public Connection(String name, boolean mediaState, String connectionSpecificDnsSuffix, String description,
				byte[] physicalAddress, boolean dhcpEnabled, boolean autoconfigurationEnabled,
				InetAddress linkLocalIpv6Address, InetAddress ipv4Address, byte[] subnetMask,
				LocalDateTime leaseObtained, LocalDateTime leaseExpires, InetAddress defaultGateway,
				InetAddress dhcpServer, int dhcpV6Iaid, String dhcpV6ClientDuid, InetAddress[] dnsServers,
				boolean netBiosOverTcpip) {
			super();
			this.name = name;
			this.mediaState = mediaState;
			this.connectionSpecificDnsSuffix = connectionSpecificDnsSuffix;
			this.description = description;
			this.physicalAddress = physicalAddress;
			this.dhcpEnabled = dhcpEnabled;
			this.autoconfigurationEnabled = autoconfigurationEnabled;
			this.linkLocalIpv6Address = linkLocalIpv6Address;
			this.ipv4Address = ipv4Address;
			this.subnetMask = subnetMask;
			this.leaseObtained = leaseObtained;
			this.leaseExpires = leaseExpires;
			this.defaultGateway = defaultGateway;
			this.dhcpServer = dhcpServer;
			this.dhcpV6Iaid = dhcpV6Iaid;
			this.dhcpV6ClientDuid = dhcpV6ClientDuid;
			this.dnsServers = dnsServers;
			this.netBiosOverTcpip = netBiosOverTcpip;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isMediaState() {
			return mediaState;
		}

		public void setMediaState(boolean mediaState) {
			this.mediaState = mediaState;
		}

		public String getConnectionSpecificDnsSuffix() {
			return connectionSpecificDnsSuffix;
		}

		public void setConnectionSpecificDnsSuffix(String connectionSpecificDnsSuffix) {
			this.connectionSpecificDnsSuffix = connectionSpecificDnsSuffix;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public byte[] getPhysicalAddress() {
			return physicalAddress;
		}

		public void setPhysicalAddress(byte[] physicalAddress) {
			this.physicalAddress = physicalAddress;
		}

		public boolean isDhcpEnabled() {
			return dhcpEnabled;
		}

		public void setDhcpEnabled(boolean dhcpEnabled) {
			this.dhcpEnabled = dhcpEnabled;
		}

		public boolean isAutoconfigurationEnabled() {
			return autoconfigurationEnabled;
		}

		public void setAutoconfigurationEnabled(boolean autoconfigurationEnabled) {
			this.autoconfigurationEnabled = autoconfigurationEnabled;
		}

		public InetAddress getLinkLocalIpv6Address() {
			return linkLocalIpv6Address;
		}

		public void setLinkLocalIpv6Address(InetAddress linkLocalIpv6Address) {
			this.linkLocalIpv6Address = linkLocalIpv6Address;
		}

		public InetAddress getIpv4Address() {
			return ipv4Address;
		}

		public void setIpv4Address(InetAddress ipv4Address) {
			this.ipv4Address = ipv4Address;
		}

		public byte[] getSubnetMask() {
			return subnetMask;
		}

		public void setSubnetMask(byte[] subnetMask) {
			this.subnetMask = subnetMask;
		}

		public LocalDateTime getLeaseObtained() {
			return leaseObtained;
		}

		public void setLeaseObtained(LocalDateTime leaseObtained) {
			this.leaseObtained = leaseObtained;
		}

		public LocalDateTime getLeaseExpires() {
			return leaseExpires;
		}

		public void setLeaseExpires(LocalDateTime leaseExpires) {
			this.leaseExpires = leaseExpires;
		}

		public InetAddress getDefaultGateway() {
			return defaultGateway;
		}

		public void setDefaultGateway(InetAddress defaultGateway) {
			this.defaultGateway = defaultGateway;
		}

		public InetAddress getDhcpServer() {
			return dhcpServer;
		}

		public void setDhcpServer(InetAddress dhcpServer) {
			this.dhcpServer = dhcpServer;
		}

		public int getDhcpV6Iaid() {
			return dhcpV6Iaid;
		}

		public void setDhcpV6Iaid(int dhcpV6Iaid) {
			this.dhcpV6Iaid = dhcpV6Iaid;
		}

		public String getDhcpV6ClientDuid() {
			return dhcpV6ClientDuid;
		}

		public void setDhcpV6ClientDuid(String dhcpV6ClientDuid) {
			this.dhcpV6ClientDuid = dhcpV6ClientDuid;
		}

		public InetAddress[] getDnsServers() {
			return dnsServers;
		}

		public void setDnsServers(InetAddress[] dnsServers) {
			this.dnsServers = dnsServers;
		}

		public boolean isNetBiosOverTcpip() {
			return netBiosOverTcpip;
		}

		public void setNetBiosOverTcpip(boolean netBiosOverTcpip) {
			this.netBiosOverTcpip = netBiosOverTcpip;
		}
	}
}
