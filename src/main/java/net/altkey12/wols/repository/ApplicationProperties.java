package net.altkey12.wols.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);
	
	private static final String LAUNCH_APPLICATION_ID = "launch.application_id";
	private static final String LAUNCH_TERMS_AND_CONDITIONS_AGREE = "launch.terms_and_conditions_agree";
	private static final String LAUNCH_PRIVACY_AGREE = "launch.privacy_agree";
	private static final String CONFIGURATION_TIMESTAMP = "configuration.timestamp";
	private static final String UPDATE_CONTENT_AUTO_UPDATE_ON_LAUNCH = "update_content.auto_update_on_launch";
	private static final String NEWS_LAST_VIEWED = "news.last_viewed";

	private static final String APPLICATION_PROPERTIES = "application.properties";
	
	protected Path appDataDir;
	
	protected Properties settings = new Properties();
	
	public ApplicationProperties(Path appDataDir) {
		this.appDataDir = appDataDir;
		load();
	}
	
	protected File getFile() {
		return appDataDir.resolve(APPLICATION_PROPERTIES).toFile();
	}
	
	protected void writeDefaultConfiguration() {
		setSettings(LAUNCH_APPLICATION_ID, UUID.randomUUID().toString());
		setSettings(LAUNCH_TERMS_AND_CONDITIONS_AGREE, "false");
		setSettings(LAUNCH_PRIVACY_AGREE, "false");
		setSettings(UPDATE_CONTENT_AUTO_UPDATE_ON_LAUNCH, "false");
		store();
	}
	
	public void load() {
		File properties = getFile();
		
		if(properties.exists() && properties.canRead()) {
			
			FileReader reader = null;
			try {
				reader = new FileReader(properties);
				settings.load(reader);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else {
			writeDefaultConfiguration();
		}
	}
	
	public void store() {
		File properties = getFile();
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(properties);
			settings.store(
					writer, 
					"Generated by application. Please do not modifiy. Any changes or edits made will be overwritten.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected String getSettings(String key) {
		return settings.getProperty(key);
	}
	
	protected void setSettings(String key, String value) {
		settings.setProperty(key, value);
	}
	
	public boolean parseBoolean(String s) {
		boolean value = false;
		
		if(s != null) {
			switch(s.trim().toLowerCase()) {
			case "false":
			case "off":
			case "no":
			case "n":
			case "0":
			case "inactive":
			case "disable":
			case "disabled":
			case "-":
				value = false;
				break;
			case "true":
			case "on":
			case "yes":
			case "y":
			case "1":
			case "active":
			case "enable":
			case "enabled":
			case "+":
				value = true;
				break;
			default:
				value = false;
				break;
			}
		}
		return value;
	}
	
	public String toString(boolean value) {
		return (value) ? "1": "0";
	}
	
	public UUID getApplicationId() {
		String appId = getSettings(LAUNCH_APPLICATION_ID);
		if(appId == null) {
			UUID newAppId = UUID.randomUUID();
			setApplicationId(newAppId);
			store();
			appId = newAppId.toString();
		}
		return UUID.fromString(appId);
	}
	
	public void setApplicationId(UUID applicationId) {
		String value = applicationId.toString();
		setSettings(LAUNCH_APPLICATION_ID, value);
	}
	
	public boolean getTermsAndConditionsAgree() {
		String agree = getSettings(LAUNCH_TERMS_AND_CONDITIONS_AGREE);
		return parseBoolean(agree);
	}
	
	public void setTermsAndConditionsAgree(boolean agree) {
		String value = toString(agree);
		setSettings(LAUNCH_TERMS_AND_CONDITIONS_AGREE, value);
	}
	
	public boolean getPrivacyAgree() {
		String agree = getSettings(LAUNCH_PRIVACY_AGREE);
		return parseBoolean(agree);
	}
	
	public void setPrivacyAgree(boolean agree) {
		String value = toString(agree);
		setSettings(LAUNCH_PRIVACY_AGREE, value);
	}
	
	public boolean getUpdateOnLaunch() {
		String update = getSettings(UPDATE_CONTENT_AUTO_UPDATE_ON_LAUNCH);
		return parseBoolean(update);
	}
	
	public void setUpdateOnLaunch(boolean update) {
		String value = toString(update);
		setSettings(UPDATE_CONTENT_AUTO_UPDATE_ON_LAUNCH, value);
	}
	
	public LocalDateTime getNewsLastViewed() {
		String newsLastViewed = getSettings(NEWS_LAST_VIEWED);
		
		LocalDateTime lastViewed = null;
		if(newsLastViewed != null) {
			try {
				lastViewed = LocalDateTime.parse(newsLastViewed);
			}
			catch(DateTimeParseException e) {
				LOGGER.error(e.getMessage() + " : " + newsLastViewed);
			}
		}
		return lastViewed;
	}
	
	public void setNewsLastViewed(LocalDateTime newsLastViewed) {
		String value = newsLastViewed.toString();
		setSettings(NEWS_LAST_VIEWED, value);
	}
	
	public LocalDateTime getConfigurationTimestamp() {
		String configurationTimestamp = getSettings(CONFIGURATION_TIMESTAMP);
		
		LocalDateTime configuration = null;
		if(configurationTimestamp != null) {
			try {
				configuration = LocalDateTime.parse(configurationTimestamp);
			}
			catch(DateTimeParseException e) {
				configuration = LocalDateTime.MIN;
				LOGGER.error(e.getMessage() + " : " + configurationTimestamp);
			}
		}
		return configuration;
	}
	
	public void setConfigurationTimestamp(LocalDateTime configurationTimestamp) {
		String value = configurationTimestamp.toString();
		setSettings(CONFIGURATION_TIMESTAMP, value);
	}
	
}