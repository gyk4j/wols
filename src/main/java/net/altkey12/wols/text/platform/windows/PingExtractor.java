package net.altkey12.wols.text.platform.windows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.altkey12.wols.beans.speedtest.windows.OutputPing;
import net.altkey12.wols.beans.speedtest.windows.OutputPing.ApproximateRoundTripTimes;
import net.altkey12.wols.beans.speedtest.windows.OutputPing.PingStatistics;
import net.altkey12.wols.beans.speedtest.windows.OutputPing.Reply;
import net.altkey12.wols.text.StdoutExtractor;

import java.util.Set;

public class PingExtractor implements StdoutExtractor<OutputPing> {

	@Override
	public OutputPing extract(Map<String, String> out) {
		List<Reply> replies = new ArrayList<>();
		PingStatistics pingStatistics = new PingStatistics(0, 0, 0);;
		ApproximateRoundTripTimes approximateRoundTripTimes;
		
		Set<Entry<String, String>> entrySet = out.entrySet();
		Iterator<Entry<String, String>> iterator = entrySet.iterator();
		
		while(iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String k = entry.getKey();
			String v = entry.getValue();
			
			if(k.startsWith("Reply from ")) {
				String[] replyLines = v.split(",");
				for(int l=0; l < replyLines.length; l++) {
					String[] tokens = replyLines[l].trim().split(" ");
					
					// tokens[0] = "bytes=32"
					int bytes = (tokens[0].startsWith("bytes="))? 
							Integer.parseInt(tokens[0].substring("bytes=".length())): 
							0;
							
					// tokens[1] = "time<1ms"
					int time = (tokens[1].startsWith("time"))? 
							Integer.parseInt(tokens[1].substring("time=".length()).replaceAll("ms", "")): 
								0;
					
					// tokens[2] = "TTL=128"
					int ttl = (tokens[2].startsWith("TTL="))? 
							Integer.parseInt(tokens[2].substring("TTL=".length())): 
								0;
					
					replies.add(new Reply(bytes, time, ttl));
				}
				
			}
			else if(k.startsWith("Ping statistics for ") && k.endsWith("/Packets")) {
				String[] statistics = v.split(", ");
				pingStatistics = new PingStatistics(
						Integer.parseInt(statistics[0].substring(statistics[0].indexOf('=')+1).trim()), 
						Integer.parseInt(statistics[1].substring(statistics[1].indexOf('=')+1).trim()), 
						Integer.parseInt(statistics[2].substring(statistics[2].indexOf('=')+1, statistics[2].indexOf('(')).trim()));
			}
			else if(k.startsWith("Request timed out.")) {
				
			}
		}
		
		approximateRoundTripTimes = new ApproximateRoundTripTimes(
				getPingTime(out, "Minimum"), 
				getPingTime(out, "Maximum"), 
				getPingTime(out, "Average"));
		
		return new OutputPing(
				replies.toArray(new Reply[0]), 
				pingStatistics, 
				approximateRoundTripTimes);
	}
	
	private int getPingTime(Map<String, String> out, String s) {
		int pingTime;
		String v = out.get("Approximate round trip times in milli-seconds/".concat(s));
		
		if(v != null) {
			pingTime = Integer.parseInt(v.replaceAll("ms", ""));
		}
		else {
			pingTime = -1;
		}
		
		return pingTime;
	}

}
