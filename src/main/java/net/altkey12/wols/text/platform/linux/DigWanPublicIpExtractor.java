package net.altkey12.wols.text.platform.linux;

import java.net.InetAddress;
import java.util.Map;

import net.altkey12.wols.beans.speedtest.linux.OutputDigWanPublicIp;
import net.altkey12.wols.text.StdoutExtractor;
import net.altkey12.wols.text.StringParser;

public class DigWanPublicIpExtractor implements StdoutExtractor<OutputDigWanPublicIp> {

	@Override
	public OutputDigWanPublicIp extract(Map<String, String> out) {
		InetAddress wanIp = StringParser.toInetAddress(out.get("ip"));
		return new OutputDigWanPublicIp(wanIp);
	}

}
