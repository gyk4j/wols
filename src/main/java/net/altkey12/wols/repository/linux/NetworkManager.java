package net.altkey12.wols.repository.linux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.altkey12.wols.beans.main.linux.OutputNmcli8021xIdentityConnectionShow;
import net.altkey12.wols.beans.main.linux.OutputNmcliConnectionAdd;
import net.altkey12.wols.beans.main.linux.OutputNmcliConnectionDelete;
import net.altkey12.wols.beans.main.linux.OutputNmcliConnectionShow;
import net.altkey12.wols.repository.NetworkProfile;
import net.altkey12.wols.text.ColonParser;
import net.altkey12.wols.text.platform.linux.Nmcli8021xIdentityConnectionShowExtractor;
import net.altkey12.wols.text.platform.linux.NmcliConnectionAddExtractor;
import net.altkey12.wols.text.platform.linux.NmcliConnectionAddParser;
import net.altkey12.wols.text.platform.linux.NmcliConnectionDeleteExtractor;
import net.altkey12.wols.text.platform.linux.NmcliConnectionDeleteParser;
import net.altkey12.wols.text.platform.linux.NmcliConnectionShowExtractor;
import net.altkey12.wols.text.platform.linux.NmcliConnectionShowParser;
import net.altkey12.wols.util.CommandLineTool;

public class NetworkManager extends NetworkProfile {
	public static final Logger LOGGER = LoggerFactory.getLogger(NetworkManager.class);
	protected ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	private static final String PATH = "/etc/NetworkManager/system-connections/" + getSsid() + ".nmconnection";
	
	public NetworkManager() {
		super();
	}

	public NetworkManager(String identity, String password) {
		this();
		setIdentity(identity);
		setPassword(password);
	}

	@Override
	public void load() {
		CommandLineTool<OutputNmcliConnectionShow> cmdNmcliConnectionShow = 
				new CommandLineTool<OutputNmcliConnectionShow>(
						new String[] { 
								"nmcli", "-t", "-s", 
								"connection", "show", getSsid()
						},
						new NmcliConnectionShowParser(),
						new NmcliConnectionShowExtractor());
		
		
		Future<OutputNmcliConnectionShow> r = executorService.submit(cmdNmcliConnectionShow);
		try {
			OutputNmcliConnectionShow nmcliConnectionShow = r.get();
			LOGGER.trace("Current SSID: {}", nmcliConnectionShow.getSsid());
			LOGGER.trace("Current Identity: {}", nmcliConnectionShow.get_8021xIdentity());
			LOGGER.trace("Current Password: {}", nmcliConnectionShow.get_8021xPassword());
			setId(nmcliConnectionShow.getConnectionId());
			setUuid(nmcliConnectionShow.getConnectionUuid());
			setIdentity(nmcliConnectionShow.get_8021xIdentity());
			setPassword(nmcliConnectionShow.get_8021xPassword());
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
		} catch (ExecutionException e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public void save() {
		
		// Delete any existing connection before overwriting.
		CommandLineTool<OutputNmcliConnectionDelete> cmdNmcliConnectionDelete = 
				new CommandLineTool<OutputNmcliConnectionDelete>(
						new String[] { 
								"nmcli", "connection", "delete", getSsid()
						},
						new NmcliConnectionDeleteParser(),
						new NmcliConnectionDeleteExtractor());
		
		Future<OutputNmcliConnectionDelete> rd = executorService.submit(cmdNmcliConnectionDelete);
		
		try {
			OutputNmcliConnectionDelete nmcliConnectionDelete = rd.get();
			LOGGER.trace("Old connection id: {}", nmcliConnectionDelete.getId());
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
		} catch (ExecutionException e) {
			LOGGER.error(e.getMessage());
		}
		
		// Now write the newest connection.
		CommandLineTool<OutputNmcliConnectionAdd> cmdNmcliConnectionAdd = 
				new CommandLineTool<OutputNmcliConnectionAdd>(
						new String[] { 
								"nmcli", "connection", "add",
								"connection.id", getSsid(),
								"type", "wifi",
								"ifname", getInterfaceName(),
								"ssid", getSsid(),
								"wifi-sec.auth-alg", "open",
								"wifi-sec.key-mgmt", "wpa-eap",
								"802-1x.ca-cert", "/etc/ssl/certs/ca-certificates.crt",
								"802-1x.eap", "peap",
								"802-1x.identity", getIdentity(),
								"802-1x.password", getPassword(),
								"802-1x.phase2-auth", "mschapv2"
						},
						new NmcliConnectionAddParser(),
						new NmcliConnectionAddExtractor());
		
		Future<OutputNmcliConnectionAdd> ra = executorService.submit(cmdNmcliConnectionAdd);
		
		try {
			OutputNmcliConnectionAdd nmcliConnectionAdd = ra.get();
			setId(nmcliConnectionAdd.getId());
			setUuid(nmcliConnectionAdd.getUuid());
			LOGGER.trace("New connection id: {}", nmcliConnectionAdd.getId());
			LOGGER.trace("New connection uuid: {}", nmcliConnectionAdd.getUuid());
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
		} catch (ExecutionException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	@Override
	public String getWifiProvider() {
		String identity = null;
		CommandLineTool<OutputNmcli8021xIdentityConnectionShow> cmdNmcli8021xIdentityConnectionShow = 
				new CommandLineTool<OutputNmcli8021xIdentityConnectionShow>(
						new String[] {  
								"nmcli", "-t", "-f", "802-1x.identity", 
								"connection", "show", getSsid()
						},
						new ColonParser(),
						new Nmcli8021xIdentityConnectionShowExtractor());
		
		Future<OutputNmcli8021xIdentityConnectionShow> r = executorService.submit(cmdNmcli8021xIdentityConnectionShow);
		
		try {
			OutputNmcli8021xIdentityConnectionShow nmcli8021xIdentityConnectionShow = r.get();
//			LOGGER.trace(nmcli8021xIdentityConnectionShow.getIdentity());
			identity = nmcli8021xIdentityConnectionShow.getIdentity();
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
		} catch (ExecutionException e) {
			LOGGER.error(e.getMessage());
		}
		
		return identity;
	}

	@Override
	public LocalDateTime getConfigurationTimestamp() {
		LocalDateTime lastConfigured = null;
		Path path = Paths.get(PATH);
		
		if(Files.exists(path) && Files.isRegularFile(path)) {
			FileTime lastModified;
			try {
				lastModified = (FileTime) Files.getAttribute(path, "lastModifiedTime");
				lastConfigured = LocalDateTime.ofInstant(
						lastModified.toInstant(), 
						ZoneId.systemDefault());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			LOGGER.warn("Unable to get connection time stamp.");
		}
		
		return lastConfigured;
	}

}
