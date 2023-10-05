package net.altkey12.wols.beans.speedtest.windows;

public class OutputWmicComputerSystemGetManufacturerModel {
	String manufacturer;
	String model;
	
	/**
	 * 
	 * @param manufacturer
	 * @param model
	 */
	public OutputWmicComputerSystemGetManufacturerModel(String manufacturer, String model) {
		super();
		this.manufacturer = manufacturer;
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
}
