package net.altkey12.wols.text.platform.linux;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.altkey12.wols.beans.main.linux.OutputNmcliConnectionShow;
import net.altkey12.wols.text.StdoutExtractor;
import net.altkey12.wols.text.StringParser;

public class NmcliConnectionShowExtractor implements StdoutExtractor<OutputNmcliConnectionShow> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NmcliConnectionShowExtractor.class);
	
	@Override
	public OutputNmcliConnectionShow extract(Map<String, String> out) {
		OutputNmcliConnectionShow extracted = new OutputNmcliConnectionShow(
				out.get("connection.id"),
				StringParser.toUuid(out.get("connection.uuid")),
				out.get("connection.interface-name"),
				out.get("802-11-wireless.ssid"),
				out.get("802-11-wireless-security.auth-alg"),
				out.get("802-11-wireless-security.key-mgmt"),
				out.get("802-1x.ca-cert"),
				out.get("802-1x.eap"),
				out.get("802-1x.identity"), 
				out.get("802-1x.password"),
				out.get("802-1x.phase2-auth")
				);
		
		LOGGER.trace("connectionid: {}", extracted.getConnectionId());
		LOGGER.trace("8021x.identity: {}", extracted.get_8021xIdentity());
		LOGGER.trace("8021x.password: {}", extracted.get_8021xPassword());
		
		return extracted;
	}

}
