package gyk4j.wols.text.platform.linux;

import java.util.Map;

import gyk4j.wols.beans.main.linux.OutputNmcli8021xIdentityConnectionShow;
import gyk4j.wols.text.StdoutExtractor;

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
