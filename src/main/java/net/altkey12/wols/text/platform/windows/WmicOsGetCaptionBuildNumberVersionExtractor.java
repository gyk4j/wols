package net.altkey12.wols.text.platform.windows;

import java.util.Map;

import net.altkey12.wols.beans.speedtest.windows.OutputWmicOsGetCaptionBuildNumberVersionSerialNumber;
import net.altkey12.wols.text.StdoutExtractor;

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
