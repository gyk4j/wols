package gyk4j.wols.text.platform.linux;

import java.util.Map;

import gyk4j.wols.beans.main.OutputNetworkProfile;
import gyk4j.wols.text.StdoutExtractor;

public class NmConnectionExtractor implements StdoutExtractor<OutputNetworkProfile> {

	@Override
	public OutputNetworkProfile extract(Map<String, String> out) {
		OutputNetworkProfile networkProfile = 
				new OutputNetworkProfile(
						out.get("identity"), 
						out.get("password"));
		return networkProfile;
	}

}
