package net.altkey12.wols.repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesConfiguration implements IConfiguration {
	public static final String CONFIGURATION_FILE="application.properties";
	
	private static final PropertiesConfiguration instance = new PropertiesConfiguration();;
	
	private Properties properties;
	
	public PropertiesConfiguration(){
		super();
		properties = new Properties();
		
		properties.setProperty(String.join(".", ISP_CONFIG, SINGTEL, ESSA_URL), "https://singtel-wsg.singtel.com/essa_r12");
		properties.setProperty(String.join(".", ISP_CONFIG, SINGTEL, CREATE_API_VERSIONS, "1"), "2.4");
		properties.setProperty(String.join(".", ISP_CONFIG, SINGTEL, CREATE_API_VERSIONS, "2"), "2.4");
		properties.setProperty(String.join(".", ISP_CONFIG, SINGTEL, RETRIEVE_API_VERSIONS, "1"), "2.0");
		properties.setProperty(String.join(".", ISP_CONFIG, SINGTEL,  RETRIEVE_API_VERSIONS, "2"), "2.6");
		
		properties.setProperty(String.join(".", ISP_CONFIG, MYREPUBLIC, ESSA_URL), "https://wireless-sg-app.myrepublic.net/essa_r12");
		properties.setProperty(String.join(".", ISP_CONFIG, MYREPUBLIC, CREATE_API_VERSIONS, "1"), "2.3");
		properties.setProperty(String.join(".", ISP_CONFIG, MYREPUBLIC, CREATE_API_VERSIONS, "2"), "2.4");
		properties.setProperty(String.join(".", ISP_CONFIG, MYREPUBLIC, RETRIEVE_API_VERSIONS, "1"), "1.6");
		properties.setProperty(String.join(".", ISP_CONFIG, MYREPUBLIC,  RETRIEVE_API_VERSIONS, "2"), "2.2");
		
		properties.setProperty(VERBOSE, Boolean.FALSE.toString());
		properties.setProperty(RC_SUCCESS, Integer.valueOf(1100).toString());
		
		loadIfExists();
	}
	
	protected void loadIfExists() {
		Path configurationFilePath = Paths.get(CONFIGURATION_FILE);
		if(Files.exists(configurationFilePath)) {
			System.out.println(String.format("'%s' found. Loading configuration values.", configurationFilePath.toAbsolutePath()));
			try {
				properties.load(new FileReader(CONFIGURATION_FILE));
			} catch (FileNotFoundException e) {
				System.err.println("Load failed: " + e.getMessage());
			} catch (IOException e) {
				System.err.println("Load failed: " + e.getMessage());
			}
		}
		else {
			System.out.println("No application.properties found. Using default configuration values.");
		}
	}

	@Override
	public boolean isVerbose() {
		return Boolean.getBoolean(properties.getProperty(VERBOSE));
	}

	@Override
	public String getEssaUrl(String isp) {
		return properties.getProperty(String.join(".", ISP_CONFIG, isp, ESSA_URL));
	}

	@Override
	public String getCreateApiVersion(String isp, int version) {
		return properties.getProperty(String.join(".", ISP_CONFIG, isp, CREATE_API_VERSIONS, Integer.valueOf(version).toString()));
	}

	@Override
	public String getRetrieveApiVersion(String isp, int version) {
		return properties.getProperty(String.join(".", ISP_CONFIG, isp, RETRIEVE_API_VERSIONS, Integer.valueOf(version).toString()));
	}

	@Override
	public IConfiguration getInstance() {
		return instance;
	}

	@Override
	public String get(String... nodes) {
		String key = String.join(".", nodes);
		return properties.getProperty(key);
	}

}
