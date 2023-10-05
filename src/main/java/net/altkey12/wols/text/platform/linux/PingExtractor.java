package net.altkey12.wols.text.platform.linux;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.altkey12.wols.beans.speedtest.linux.OutputPing;
import net.altkey12.wols.beans.speedtest.linux.OutputPing.ApproximateRoundTripTimes;
import net.altkey12.wols.beans.speedtest.linux.OutputPing.PingStatistics;
import net.altkey12.wols.beans.speedtest.linux.OutputPing.Reply;
import net.altkey12.wols.text.StdoutExtractor;

public class PingExtractor implements StdoutExtractor<OutputPing> {
	protected static final Logger LOGGER = LoggerFactory.getLogger(PingExtractor.class); 
	
	@Override
	public OutputPing extract(Map<String, String> out) {
		int replyCount = Integer.parseInt(out.get("replies"));
		
		Reply[] replies = new Reply[replyCount];
		for(int r=0; r < replies.length; r++) {
			int bytes = Integer.parseInt(out.get(Integer.toString(r).concat(".bytes")));
			float time = Float.parseFloat(out.get(Integer.toString(r).concat(".time")));
			int ttl = Integer.parseInt(out.get(Integer.toString(r).concat(".ttl")));
			
			replies[r] = new Reply(bytes, time, ttl);
		}
		
		PingStatistics pingStatistics = new PingStatistics(
				Integer.parseInt(out.get("packets.transmitted")), 
				Integer.parseInt(out.get("packets.received")), 
				Float.parseFloat(out.get("packets.lossPercentage")));
		
		ApproximateRoundTripTimes rtt = new ApproximateRoundTripTimes(
				getPingTime(out, "statistics.min"), 
				getPingTime(out, "statistics.avg"), 
				getPingTime(out, "statistics.max"),
				getPingTime(out, "statistics.mdev"));
		
		return new OutputPing(replies, pingStatistics, rtt);
	}
	
	private float getPingTime(Map<String, String> out, String s) {
		float pingTime;
		
		String v = out.get(s);
		if(v != null) {
			pingTime = Float.parseFloat(v);
		}
		else {
			pingTime = -1;
		}
		
		return pingTime;
	}

}
