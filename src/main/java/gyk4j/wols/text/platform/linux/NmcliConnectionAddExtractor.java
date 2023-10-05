package gyk4j.wols.text.platform.linux;

import java.util.Map;

import gyk4j.wols.beans.main.linux.OutputNmcliConnectionAdd;
import gyk4j.wols.text.StdoutExtractor;
import gyk4j.wols.text.StringParser;

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
