package gyk4j.wols.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.ApplicationContext;
import gyk4j.wols.repository.ApplicationProperties;

public abstract class AbstractController {
	public static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);
	
	protected ApplicationProperties applicationProperties = new ApplicationProperties(ApplicationContext.getAppDataDir());
	
	private String[] arguments;
	
	public static AbstractController getInstance() {
		return null;
	}
	
	public String[] getArguments() {
		return arguments;
	}

	public void setArguments(String[] arguments) {
		this.arguments = arguments;
	}
}
