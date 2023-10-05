package net.altkey12.wols.text.platform.linux;

import java.util.Map;

import net.altkey12.wols.beans.main.OutputNetworkProfile;
import net.altkey12.wols.text.StdoutExtractor;

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
