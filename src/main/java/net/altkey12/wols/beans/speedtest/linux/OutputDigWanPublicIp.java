package net.altkey12.wols.beans.speedtest.linux;

import java.net.InetAddress;

public class OutputDigWanPublicIp {
	InetAddress wanIp;

	public OutputDigWanPublicIp(InetAddress wanIp) {
		this.wanIp = wanIp;
	}

	public InetAddress getWanIp() {
		return wanIp;
	}

	public void setWanIp(InetAddress wanIp) {
		this.wanIp = wanIp;
	}
	
}
