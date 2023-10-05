package net.altkey12.wols.beans.speedtest;

import java.net.InetAddress;
import java.time.Duration;

public class NetworkInformation {
	protected InetAddress wanIp;
	protected InetAddress lanIp;
	protected InetAddress gatewayIp;
	protected InetAddress[] dnsServers;
	protected InetAddress dhcpServer;
	protected Duration dhcpLeaseTime;
	protected byte[] networkMask;
	protected byte[] macAddress;
	
	/**
	 * 
	 * @param wanIp
	 * @param lanIp
	 * @param gatewayIp
	 * @param dnsServers
	 * @param dhcpServer
	 * @param dhcpLeaseTime
	 * @param networkMask
	 * @param macAddress
	 */
	public NetworkInformation(InetAddress wanIp, InetAddress lanIp, InetAddress gatewayIp, InetAddress[] dnsServers,
			InetAddress dhcpServer, Duration dhcpLeaseTime, byte[] networkMask, byte[] macAddress) {
		super();
		this.wanIp = wanIp;
		this.lanIp = lanIp;
		this.gatewayIp = gatewayIp;
		this.dnsServers = dnsServers;
		this.dhcpServer = dhcpServer;
		this.dhcpLeaseTime = dhcpLeaseTime;
		this.networkMask = networkMask;
		this.macAddress = macAddress;
	}

	public InetAddress getWanIp() {
		return wanIp;
	}

	public void setWanIp(InetAddress wanIp) {
		this.wanIp = wanIp;
	}

	public InetAddress getLanIp() {
		return lanIp;
	}

	public void setLanIp(InetAddress lanIp) {
		this.lanIp = lanIp;
	}

	public InetAddress getGatewayIp() {
		return gatewayIp;
	}

	public void setGatewayIp(InetAddress gatewayIp) {
		this.gatewayIp = gatewayIp;
	}

	public InetAddress[] getDnsServers() {
		return dnsServers;
	}

	public void setDnsServers(InetAddress[] dnsServers) {
		this.dnsServers = dnsServers;
	}

	public InetAddress getDhcpServer() {
		return dhcpServer;
	}

	public void setDhcpServer(InetAddress dhcpServer) {
		this.dhcpServer = dhcpServer;
	}

	public Duration getDhcpLeaseTime() {
		return dhcpLeaseTime;
	}

	public void setDhcpLeaseTime(Duration dhcpLeaseTime) {
		this.dhcpLeaseTime = dhcpLeaseTime;
	}

	public byte[] getNetworkMask() {
		return networkMask;
	}

	public void setNetworkMask(byte[] networkMask) {
		this.networkMask = networkMask;
	}

	public byte[] getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(byte[] macAddress) {
		this.macAddress = macAddress;
	}
	
	
	
}
