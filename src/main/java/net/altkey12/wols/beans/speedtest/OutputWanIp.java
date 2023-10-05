package net.altkey12.wols.beans.speedtest;

import java.net.InetAddress;

public class OutputWanIp {
	protected InetAddress wanIp;

	public OutputWanIp(InetAddress wanIp) {
		super();
		this.wanIp = wanIp;
	}

	public InetAddress getWanIp() {
		return wanIp;
	}

	public void setWanIp(InetAddress wanIp) {
		this.wanIp = wanIp;
	}
}
