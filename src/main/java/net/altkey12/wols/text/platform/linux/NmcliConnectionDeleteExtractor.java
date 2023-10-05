package net.altkey12.wols.text.platform.linux;

import java.util.Map;

import net.altkey12.wols.beans.main.linux.OutputNmcliConnectionDelete;
import net.altkey12.wols.text.StdoutExtractor;
import net.altkey12.wols.text.StringParser;

public class NmcliConnectionDeleteExtractor implements StdoutExtractor<OutputNmcliConnectionDelete> {

	@Override
	public OutputNmcliConnectionDelete extract(Map<String, String> out) {
		OutputNmcliConnectionDelete extracted = new OutputNmcliConnectionDelete(
				out.get("connection.id"), 
				StringParser.toUuid(out.get("connection.uuid"))
				);
		return extracted;
	}

}
