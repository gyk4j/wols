package gyk4j.wols.text.platform.windows;

import java.util.Map;

import gyk4j.wols.beans.speedtest.windows.OutputWmicOsGetCaptionBuildNumberVersionSerialNumber;
import gyk4j.wols.text.StdoutExtractor;

public class WmicOsGetCaptionBuildNumberVersionExtractor
		implements StdoutExtractor<OutputWmicOsGetCaptionBuildNumberVersionSerialNumber> {

	@Override
	public OutputWmicOsGetCaptionBuildNumberVersionSerialNumber extract(Map<String, String> out) {
		return new OutputWmicOsGetCaptionBuildNumberVersionSerialNumber(
				out.getOrDefault("Caption", UNAVAILABLE), 
				out.getOrDefault("BuildNumber", UNAVAILABLE), 
				out.getOrDefault("Version", UNAVAILABLE),
				out.getOrDefault("SerialNumber", UNAVAILABLE));
	}

}
