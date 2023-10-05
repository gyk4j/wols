package net.altkey12.wols.repository.linux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.altkey12.wols.repository.NetworkProfile;

public class WpaSupplicantConf extends NetworkProfile {
	public static final Logger LOGGER = LoggerFactory.getLogger(WpaSupplicantConf.class);
	
	public static final String PATH = "/etc/wpa_supplicant/wpa_supplicant.conf";

	public WpaSupplicantConf() {
		this("johndoe", "password");
	}
	
	public WpaSupplicantConf(String identity, String password) {
		super(identity, password);
	}

	@Override
	public void load() {		
		Path path = Paths.get(PATH);
		
		if(Files.exists(path) && Files.isRegularFile(path) && Files.isReadable(path)) {
			try {
				List<String> lines = Files.readAllLines(path);
				
				boolean inNetworkBlock = false;
				boolean inWirelessSGxProfile = false;
				
				Iterator<String> iterator = lines.iterator();
				while(iterator.hasNext()) {
					String line = iterator.next();
					if(line.trim().toLowerCase().startsWith("network={")) {
						inNetworkBlock = true;
					}
					else if(line.trim().equals("}")) {
						inWirelessSGxProfile = false;
						inNetworkBlock = false;
					}
					else if(inNetworkBlock) {
						// Key value pairs.
						
						if(line.indexOf('=') != -1) {
							String[] kv = new String[2];
							kv[0] = line.substring(0, line.indexOf('=')).trim();
							kv[1] = line.substring(line.indexOf('=')+1).trim();

							// Unescape quoted string value
							if(kv[1].startsWith("\"") || kv[1].endsWith("\"")) {
								kv[1] = kv[1].replaceAll("\\\"", "");
								kv[1] = kv[1].replaceAll("\"", "");
							}

							if("ssid".equals(kv[0].toLowerCase())) {							
								if("Wireless@SGx".equals(kv[1])) {
									inWirelessSGxProfile = true;
									LOGGER.info("Found Wireless@SGx");
								}
							}
							else if(inWirelessSGxProfile && "identity".equals(kv[0].toLowerCase())) {
								setIdentity(kv[1]);
								LOGGER.info("Identity: {}", getIdentity());
							}
							else if(inWirelessSGxProfile && "password".equals(kv[0].toLowerCase())) {
								setPassword(kv[1]);
								LOGGER.info("Password: {}", getPassword());
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void save() {
		Path path = Paths.get(PATH);
		Path dir = path.getParent();
		
		// Create missing directories if required. 
		// By right this should not happen on Linux system with wpa_supplicant installed.
		if(!Files.exists(dir) || !Files.isDirectory(dir)) {
			try {
				Files.createDirectories(dir);
			} catch (IOException e) {
				e.printStackTrace();
			}
			LOGGER.info("Created missing directory: {}", dir);
		}
		
		OpenOption option = (Files.exists(path))? 
				StandardOpenOption.APPEND: 
				StandardOpenOption.CREATE;
		
		WpaSupplicantConfBuilder builder = new WpaSupplicantConfBuilder();
		String conf = builder
				.ssid(getSsid())
				.identity(getIdentity())
				.password(getPassword())
				.build();
		List<String> lines = Arrays.asList(conf.split(System.lineSeparator()));
		
		if(Files.exists(path) && Files.isRegularFile(path) && Files.isWritable(path)) {
			try {
				Files.write(path, lines, option);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			LOGGER.error("{} does not exist, or is not a writable file", path);
		}
	}
	
	@Override
	public String getWifiProvider() {
		return getIdentity();
	}
	
	public LocalDateTime getConfigurationTimestamp() {
		LocalDateTime configurationTimestamp = null;
		
		Path path = Paths.get(PATH);
		
		if(Files.exists(path) && Files.isRegularFile(path) && Files.isReadable(path)) {
			try {
				configurationTimestamp = LocalDateTime.ofInstant(
						Files.getLastModifiedTime(path).toInstant(),
						ZoneId.systemDefault());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return configurationTimestamp;
	}

}
