package gyk4j.wols.text.platform.windows;

import java.util.Map;

import gyk4j.wols.beans.speedtest.windows.OutputWmicComputerSystemGetManufacturerModel;
import gyk4j.wols.text.StdoutExtractor;

public class WmicComputerSystemGetManufacturerModelExtractor
		implements StdoutExtractor<OutputWmicComputerSystemGetManufacturerModel> {

	@Override
	public OutputWmicComputerSystemGetManufacturerModel extract(Map<String, String> out) {
		return new OutputWmicComputerSystemGetManufacturerModel(
				out.get("Manufacturer"), 
				out.get("Model"));
	}

}
