package net.altkey12.wols.text.platform.windows;

import java.util.Map;

import net.altkey12.wols.beans.speedtest.windows.OutputWmicComputerSystemGetManufacturerModel;
import net.altkey12.wols.text.StdoutExtractor;

public class WmicComputerSystemGetManufacturerModelExtractor
		implements StdoutExtractor<OutputWmicComputerSystemGetManufacturerModel> {

	@Override
	public OutputWmicComputerSystemGetManufacturerModel extract(Map<String, String> out) {
		return new OutputWmicComputerSystemGetManufacturerModel(
				out.get("Manufacturer"), 
				out.get("Model"));
	}

}
