package net.altkey12.wols.text.platform.linux;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.altkey12.wols.text.StdoutParser;

public class PingParser implements StdoutParser {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(PingParser.class);
	
	protected static final Pattern ACTION = 
			Pattern.compile("PING ([0-9a-zA-Z_\\-\\.]+) \\(([0-9\\.]+)\\) [0-9]+\\([0-9]+\\) bytes of data.");
	
	protected static final Pattern REPLY = 
			Pattern.compile("([0-9]+) bytes from ([0-9a-zA-Z_\\-\\.]+) \\(([0-9\\.]+)\\): icmp_seq=([0-9]+) ttl=([0-9]+) time=([0-9\\.]+) ms");
	
	protected static final Pattern PACKETS =
			Pattern.compile("([0-9]+) packets transmitted, ([0-9]+) received, ([0-9\\.]+)% packet loss, time ([0-9]+)ms");
	
	protected static final Pattern STATISTICS =
			Pattern.compile("rtt min/avg/max/mdev = ([0-9\\.]+)/([0-9\\.]+)/([0-9\\.]+)/([0-9\\.]+) ms");


	@Override
	public Map<String, String> parse(String s) {
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		
		int r=0;
		String[] lines = s.split(System.lineSeparator());
		for(int l=0; l < lines.length; l++) {
			String line = lines[l];
			
			Matcher action = ACTION.matcher(line);
			Matcher reply = REPLY.matcher(line);
			Matcher packets = PACKETS.matcher(line);
			Matcher statistics = STATISTICS.matcher(line);
			
			if(action.matches()) {
				String hostName = action.group(1);
				String hostIp = action.group(2);
				
				data.put("ping.name", hostName);
				data.put("ping.ip", hostIp);
				
//				LOGGER.trace("ACTION: {} <{}>", hostName, hostIp);
			}
			else if(reply.matches()) {
				String byteCount = reply.group(1);
				String hostName = reply.group(2);
				String hostIp = reply.group(3);
				String icmpSeq = reply.group(4);
				String ttl = reply.group(5);
				String time = reply.group(6);
				
				data.put(Integer.toString(r).concat(".bytes"), byteCount);
				data.put(Integer.toString(r).concat(".name"), hostName);
				data.put(Integer.toString(r).concat(".ip"), hostIp);
				data.put(Integer.toString(r).concat(".icmp_seq"), icmpSeq);
				data.put(Integer.toString(r).concat(".ttl"), ttl);
				data.put(Integer.toString(r).concat(".time"), time);
				
//				LOGGER.trace("REPLY: {} bytes, from {} <{}>", byteCount, hostName, hostIp);
				r++;
			}
			else if(packets.matches()) {
				String transmitted = packets.group(1);
				String received = packets.group(2);
				String lossPercentage = packets.group(3);
				String timeMs = packets.group(4);
				
				data.put("packets.transmitted", transmitted);
				data.put("packets.received", received);
				data.put("packets.lossPercentage", lossPercentage);
				data.put("packets.timeMs", timeMs);
				
//				LOGGER.trace("PACKETS: {} / {} / {}% / {} ms", transmitted, received, lossPercentage, timeMs);
			}
			else if(statistics.matches()) {
				String min = statistics.group(1);
				String avg = statistics.group(2);
				String max = statistics.group(3);
				String mdev = statistics.group(4);
				
				data.put("statistics.min", min);
				data.put("statistics.avg", avg);
				data.put("statistics.max", max);
				data.put("statistics.mdev", mdev);
				
//				LOGGER.trace("STATISTICS: min {} / avg {} / max {} / mdev {}", min, avg, max, mdev);
			}
//			else {
//				LOGGER.warn("Skipped unknown line: {}", line);
//			}
		}
		
		data.put("replies", Integer.toString(r));
		
		return data;
	}
}
