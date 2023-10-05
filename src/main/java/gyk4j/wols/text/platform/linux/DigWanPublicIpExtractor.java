package gyk4j.wols.text.platform.linux;

import java.net.InetAddress;
import java.util.Map;

import gyk4j.wols.beans.speedtest.linux.OutputDigWanPublicIp;
import gyk4j.wols.text.StdoutExtractor;
import gyk4j.wols.text.StringParser;

public class DigWanPublicIpExtractor implements StdoutExtractor<OutputDigWanPublicIp> {

	@Override
	public OutputDigWanPublicIp extract(Map<String, String> out) {
		InetAddress wanIp = StringParser.toInetAddress(out.get("ip"));
		return new OutputDigWanPublicIp(wanIp);
	}

}
