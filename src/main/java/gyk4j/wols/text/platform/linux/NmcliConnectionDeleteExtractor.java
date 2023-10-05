package gyk4j.wols.text.platform.linux;

import java.util.Map;

import gyk4j.wols.beans.main.linux.OutputNmcliConnectionDelete;
import gyk4j.wols.text.StdoutExtractor;
import gyk4j.wols.text.StringParser;

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
