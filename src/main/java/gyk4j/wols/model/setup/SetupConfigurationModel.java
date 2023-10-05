package gyk4j.wols.model.setup;

import gyk4j.wols.beans.setup.ConfigurationRequest;
import gyk4j.wols.beans.setup.ConfigurationResult;
import gyk4j.wols.controller.Controller;

public class SetupConfigurationModel {
	public ConfigurationResult checkConfiguration() {
		ConfigurationResult result = new ConfigurationResult();
		
		if(result.isOK()) {
			// Need to reset the registration and verification form.
			Controller.getInstance().resetRegistrationForms();
		}
		
		return result;
	}
	
	public ConfigurationRequest createRequest() {
		ConfigurationRequest configurationRequest = new ConfigurationRequest();
		
		return configurationRequest;
	}
	
	public void reset() {
		
	}
}
