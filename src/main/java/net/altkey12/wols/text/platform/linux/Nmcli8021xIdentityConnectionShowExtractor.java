package net.altkey12.wols.text.platform.linux;

import java.util.Map;

import net.altkey12.wols.beans.main.linux.OutputNmcli8021xIdentityConnectionShow;
import net.altkey12.wols.text.StdoutExtractor;

public class Nmcli8021xIdentityConnectionShowExtractor
		implements StdoutExtractor<OutputNmcli8021xIdentityConnectionShow> {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(Nmcli8021xIdentityConnectionShowExtractor.class);

	@Override
	public OutputNmcli8021xIdentityConnectionShow extract(Map<String, String> out) {
		String identity = out.get("802-1x.identity");
//		LOGGER.trace(identity);
		return new OutputNmcli8021xIdentityConnectionShow(identity);
	}

}
