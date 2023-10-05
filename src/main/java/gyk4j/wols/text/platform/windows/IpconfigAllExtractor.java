package gyk4j.wols.text.platform.windows;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gyk4j.wols.beans.speedtest.windows.OutputIpconfigAll;
import gyk4j.wols.beans.speedtest.windows.OutputIpconfigAll.Connection;
import gyk4j.wols.beans.speedtest.windows.OutputIpconfigAll.WindowsIpConfiguration;
import gyk4j.wols.text.ColonParser;
import gyk4j.wols.text.StdoutExtractor;
import gyk4j.wols.text.StringParser;

public class IpconfigAllExtractor implements StdoutExtractor<OutputIpconfigAll> {

	@Override
	public OutputIpconfigAll extract(Map<String, String> out) {
		WindowsIpConfiguration windowsIpConfiguration = new WindowsIpConfiguration(
				out.getOrDefault("Windows IP Configuration/Host Name", UNAVAILABLE), 
				out.getOrDefault("Windows IP Configuration/Primary Dns Suffix", UNAVAILABLE), 
				out.getOrDefault("Windows IP Configuration/Node Type", UNAVAILABLE),
				out.getOrDefault("Windows IP Configuration/IP Routing Enabled", UNAVAILABLE).equals("Yes"), 
				out.getOrDefault("Windows IP Configuration/WINS Proxy Enabled", UNAVAILABLE).equals("Yes"));
		
		List<Connection> connections = new ArrayList<>();
		
		out.forEach((k , v) -> {
			if(k.startsWith("Windows IP Configuration"))
				return;
			
			String[] tokens = k.split(ColonParser.KEY_SEPARATOR_STR);
			
			if(tokens.length == 1) {
				String connectionName = tokens[0];				
				
				try {
					String[] dnsServersStr = out.getOrDefault(connectionName + "/DNS Servers", "").split(", ");
					InetAddress[] dnsServers = new InetAddress[dnsServersStr.length];
					
					for(int i=0; i < dnsServers.length; i++) {
						dnsServers[i] = StringParser.toInetAddress(dnsServersStr[i]);
					}
					
					boolean isConnected = true;
					if(out.get(connectionName + "/Media State") != null) {
						isConnected = false;
					}
					
					Connection connection = new Connection(
							tokens[0],
							isConnected,
							out.get(connectionName + "/Connection-specific DNS Suffix"),
							out.get(connectionName + "/Description"),
							StringParser.toMacAddress(out.get(connectionName + "/Physical Address")),
							StringParser.toBoolean(out.get(connectionName + "/DHCP Enabled")),
							StringParser.toBoolean(out.get(connectionName + "/Autoconfiguration Enabled")),
							StringParser.toInetAddress(out.get(connectionName + "/Link-local IPv6 Address")),
							StringParser.toInetAddress(out.get(connectionName + "/IPv4 Address")),
							StringParser.toIpv4SubnetMask(out.get(connectionName + "/Subnet Mask")),
							StringParser.toLocalDateTime(out.get(connectionName + "/Lease Obtained")),
							StringParser.toLocalDateTime(out.get(connectionName + "/Lease Expires")),
							StringParser.toInetAddress(out.get(connectionName + "/Default Gateway")),
							StringParser.toInetAddress(out.get(connectionName + "/DHCP Server")),
							StringParser.toInt(out.get(connectionName + "/DHCPv6 IAID")),
							out.get(connectionName + "/DHCPv6 Client DUID"),
							dnsServers,
							StringParser.toBoolean(out.get(connectionName + "/NetBIOS over Tcpip"))
					);
					
					connections.add(connection);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		});
		
		return new OutputIpconfigAll(windowsIpConfiguration, connections.toArray(new Connection[0]));
	}

}
