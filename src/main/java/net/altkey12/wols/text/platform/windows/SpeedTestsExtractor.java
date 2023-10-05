package net.altkey12.wols.text.platform.windows;

import java.util.Map;

import net.altkey12.wols.beans.speedtest.OutputSpeedTests;
import net.altkey12.wols.text.StdoutExtractor;

/**
 * @deprecated
 * @author USER
 *
 */
public class SpeedTestsExtractor implements StdoutExtractor<OutputSpeedTests>{

	@Override
	public OutputSpeedTests extract(Map<String, String> out) {
		out.forEach((k,v) -> System.out.println("OutputSpeedTests::extract> " + k + " = " + v));
		return new OutputSpeedTests(Float.parseFloat(out.getOrDefault("Download Speed Tests", "0.00")));
	}

}
