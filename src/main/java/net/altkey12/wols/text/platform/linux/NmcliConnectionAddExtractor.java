package net.altkey12.wols.text.platform.linux;

import java.util.Map;

import net.altkey12.wols.beans.main.linux.OutputNmcliConnectionAdd;
import net.altkey12.wols.text.StdoutExtractor;
import net.altkey12.wols.text.StringParser;

public class NmcliConnectionAddExtractor implements StdoutExtractor<OutputNmcliConnectionAdd> {

	@Override
	public OutputNmcliConnectionAdd extract(Map<String, String> out) {
		OutputNmcliConnectionAdd extracted = new OutputNmcliConnectionAdd(
				out.get("connection.id"), 
				StringParser.toUuid(out.get("connection.uuid"))
				);
		return extracted;
	}

}
