package net.altkey12.wols.model.setup;

import net.altkey12.wols.beans.setup.ConfigurationRequest;
import net.altkey12.wols.beans.setup.ConfigurationResult;
import net.altkey12.wols.controller.Controller;

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
