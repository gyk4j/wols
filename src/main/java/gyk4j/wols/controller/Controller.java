package gyk4j.wols.controller;

import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.python.net.Credentials;
import org.python.net.ValidateOtpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.Wols;
import gyk4j.wols.beans.contactus.Provider;
import gyk4j.wols.beans.setup.ConfigurationRequest;
import gyk4j.wols.beans.setup.ConfigurationResult;
import gyk4j.wols.beans.setup.RegistrationRequest;
import gyk4j.wols.beans.setup.VerificationRequest;
import gyk4j.wols.beans.speedtest.DiagnosticsResults;
import gyk4j.wols.beans.updatecontent.FaqsSection;
import gyk4j.wols.beans.updatecontent.News;
import gyk4j.wols.beans.updatecontent.Update;
import gyk4j.wols.net.DiagnosticsResultsBuilder;
import gyk4j.wols.net.NetworkDiagnostics;
import gyk4j.wols.net.NetworkDiagnosticsFactory;
import gyk4j.wols.repository.CountryCodesRepository;
import gyk4j.wols.repository.NetworkProfile;
import gyk4j.wols.repository.UpdateRepository;
import gyk4j.wols.repository.linux.NetworkManager;
import gyk4j.wols.service.RegistrationService;
import gyk4j.wols.view.component.app.MainFrame;
import gyk4j.wols.view.component.main.MainPanel;
import gyk4j.wols.view.component.setup.SetupPage;

public class Controller extends AbstractController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

	private UUID applicationId;
	private boolean termsAndConditionsAgree;
	private boolean privacyAgree;
	
	private LocalDateTime newsLastViewed;
	
	protected static Controller instance;
	
	protected UpdateRepository updateRepository = new UpdateRepository();
	protected Update update;
	
	protected NetworkProfile networkProfile = new NetworkManager(); // new WpaSupplicantConf();
	
	protected CountryCodesRepository countryCodesRepository = new CountryCodesRepository();
	
	private RegistrationRequest registrationRequest;
	private String successCode;
	private VerificationRequest verificationRequest;
	private ValidateOtpResult validateOtpResult;
	private ConfigurationRequest configurationRequest;
	private ConfigurationResult configurationResult;
	
	public static Controller getInstance() {
		if(instance == null) {
			instance = new Controller();
			instance.initialize();
		}
		return instance;
	}
	
	protected void initialize() {
//		networkProfile.load();
		update = updateRepository.get();
	}
	
	// Main
	public UUID getApplicationId() {
		if(this.applicationId == null) {
			this.applicationId = applicationProperties.getApplicationId();
		}
		return this.applicationId;
	}
	
	public void setApplicationId(UUID applicationId) {
		this.applicationId = applicationId;
		applicationProperties.setApplicationId(applicationId);
		applicationProperties.store();
	}
	
	// Start
	public boolean getTermsAndConditionsAgree() {
		return this.termsAndConditionsAgree;
	}
	
	public void setTermsAndConditionsAgree(boolean termsAndConditionsAgree) {
		this.termsAndConditionsAgree = termsAndConditionsAgree;
	}

	public boolean getPrivacyAgree() {
		return this.privacyAgree;
	}
	
	public void setPrivacyAgree(boolean privacyAgree) {
		this.privacyAgree = privacyAgree;
	}	
	
	public LocalDateTime getNewsLastViewed() {
		return newsLastViewed;
	}

	public void setNewsLastViewed(LocalDateTime newsLastViewed) {
		this.newsLastViewed = newsLastViewed;
	}

	// Launch
	public boolean loadTermsAndConditionsAgree() {
		setTermsAndConditionsAgree(applicationProperties.getTermsAndConditionsAgree());
		return getTermsAndConditionsAgree();
	}
	
	public void saveTermsAndConditionsAgree() {
		setTermsAndConditionsAgree(true);
		applicationProperties.setTermsAndConditionsAgree(getTermsAndConditionsAgree());
		applicationProperties.store();
	}
	
	public boolean loadPrivacyAgree() {
		setPrivacyAgree(applicationProperties.getPrivacyAgree());
		return getPrivacyAgree();
	}
	
	public void savePrivacyAgree() {
		setPrivacyAgree(true);
		applicationProperties.setPrivacyAgree(getPrivacyAgree());
		applicationProperties.store();
	}
	
	// Main
	public void closeWindow() {
		MainFrame.getInstance().closeWindow();
	}
	
	public void nextScene() {
		MainFrame.getInstance().nextScene();
	}
	
	public String getApplicationVersion() {
		return Wols.VERSION;
	}
	
	public String getConfiguredIsp() {
		String isp = null;
//		networkProfile.load();
//		String userid = networkProfile.getIdentity();
		
		String userid = networkProfile.getWifiProvider();		
//		LOGGER.trace("Controller::getConfiguredIsp: " + userid);
		
		if(userid == null)
			isp = null;
		else if("Unconfigured".equals(userid))
			isp = userid;
		else if(userid.contains("@m1"))
			isp = "M1";
		else if(userid.contains("@singtel"))
			isp = "Singtel";
		else if(userid.contains("@starhub"))
			isp = "Starhub";
		else if(userid.contains("@simba") || userid.contains("@tpg"))
			isp = "Simba";
		else
			isp = null;
		
		return isp;
	}
	
	public LocalDateTime getConfigurationDateTime() {
		return networkProfile.getConfigurationTimestamp();
//		return applicationProperties.getConfigurationTimestamp();
	}
	
	public String getSSID() {
		return NetworkProfile.getSsid();
	}
	
	// Hotspots	
	public void loadUpdate() {
	}
	
	public LocalDate getDate() {
		return update.getDate();
	}
	
	public String getVersion() {
		return update.getVersion();
	}
	
	public Point2D.Double[] getHotspots() {
		return update.getHotspots();
	}
	
	// FAQs
	public FaqsSection[] getFaqs() {
		return update.getFaqsSections();
	}
	
	// News
	public News[] getNews() {
		return update.getNews();
	}
	
	public LocalDateTime loadNewsLastViewed() {
		LocalDateTime lastViewed = applicationProperties.getNewsLastViewed();
		setNewsLastViewed((lastViewed != null)? lastViewed: LocalDateTime.MIN);
		return getNewsLastViewed();
	}
	
	public void saveNewsLastViewed() {
		setNewsLastViewed(LocalDateTime.now());
		applicationProperties.setNewsLastViewed(newsLastViewed);
		applicationProperties.store();
	}
	
	// Update Content
	public boolean getUpdateOnLaunchSetting() {
		return applicationProperties.getUpdateOnLaunch();
	}
	
	public void setAutoUpdateOnLaunch(boolean update) {
		applicationProperties.setUpdateOnLaunch(update);
		applicationProperties.store();
	}
	
	public boolean updateNow() {
		return update();
	}
	
	public boolean updateOnLaunch() {
		boolean status = false;
		
		boolean updateOnLaunch = applicationProperties.getUpdateOnLaunch();
		
		if(updateOnLaunch) {
			LOGGER.info("Update on launch");
			status = update();
		}
		else {
			LOGGER.info("Skipped update on launch");
		}
		
		return status;
	}
	
	protected boolean update() {
		boolean status = false;
		
		Update newUpdate = updateRepository.get();
		
		if(update == null || newUpdate.compareTo(update) > 0) {
			update = newUpdate;			
			status = true;
		}
		
		return status;
	}
	
	// Setup
	
	public String[] loadCountryCodes() {
		return countryCodesRepository.findAll();
	}
	
	public String register(RegistrationRequest request) {
		LOGGER.trace("Register\n\tProvider: {}\n\tDOB: {}\n\tCountry: {}\n\tMobile Phone: {}\n\tAgree: {}",
				request.getProvider(),
				request.getDob().toString(),
				request.getCountry(),
				request.getMobilePhone(),
				request.isAgree());
		
		registrationRequest = request;
		
		successCode = RegistrationService.register(
				registrationRequest.getProvider(), 
				registrationRequest.getDob().format(DateTimeFormatter.ofPattern("ddMMyyyy")), 
				registrationRequest.getMobilePhone());
		
		return successCode;
	}
	
	public String verifyPin(VerificationRequest request) {
		LOGGER.trace("Verify\n\tPIN: {}", request.getPin());
		
		verificationRequest = request;
		
		validateOtpResult = RegistrationService.validate(
				registrationRequest.getProvider(), 
				registrationRequest.getDob().format(DateTimeFormatter.ofPattern("ddMMyyyy")), 
				registrationRequest.getMobilePhone(), 
				verificationRequest.getPin(), 
				successCode);
		
		if(validateOtpResult != null) {
			Credentials credentials = RegistrationService.decryptAccountInfo(
					verificationRequest.getPin(), 
					validateOtpResult);
			
			if(credentials != null) {
				
				configurationRequest = new ConfigurationRequest();
				configurationRequest.setIdentity(credentials.getUserid());
				configurationRequest.setPassword(credentials.getPassword());
				configure(configurationRequest);
				
				if (configurationResult != null && configurationResult.isOK())
					return configurationResult.getConnectionUuid().toString();
				else {
					LOGGER.error("Error during configuration update.");
					return "Error during configuration update.";
				}
			}
			else {
				LOGGER.error("Failed to decrypt userid and password with right success code and OTP??");
				return "Failed to decrypt userid and password with right success code and OTP??";
			}
		}
		else {
			LOGGER.error("Wrong OTP.");
			return "Wrong OTP.";
		}
	}
	
	private void configure(ConfigurationRequest request) {
		LOGGER.trace("Configure\n\tIdentity: {}\n\tPassword: {}", 
				request.getIdentity(), 
				request.getPassword());
		
		configurationRequest = request;
		
		networkProfile.setIdentity(configurationRequest.getIdentity());
		networkProfile.setPassword(configurationRequest.getPassword());
		networkProfile.save();
		
		configurationResult = new ConfigurationResult();
		configurationResult.setConnectionId(networkProfile.getId());
		configurationResult.setConnectionUuid(networkProfile.getUuid());
	}
	
	public String checkConfigurationResult() {
		return (configurationResult != null && configurationResult.isOK())? 
				configurationResult.getConnectionUuid().toString(): null;
	}
	
	public void resetRegistrationForms() {
		SetupPage p = (SetupPage) MainPanel.getInstance().getPage(SetupPage.class.getSimpleName());
		if(p != null) {
			p.reset();
		}
		else {
			LOGGER.error("Setup page is null. Cannot reset forms.");
		}
	}
	
	public DiagnosticsResults runDiagnostics() {
		NetworkDiagnostics d = NetworkDiagnosticsFactory.getInstance();
		
		DiagnosticsResultsBuilder drb = new DiagnosticsResultsBuilder();
		
		drb
		.diagnosticsInformation(d.getDiagnosticsInformation())
		.deviceInformation(d.getDeviceInformation())
		.wifiInformation(d.getWifiInformation())
		.networkInformation(d.getNetworkInformation())
		.wifiScanResults(d.getWifiScanResults())
		.speedTests(d.getSpeedTests())
		.generalPingTests(d.getGeneralPingTests())
		.amsPingTests(d.getAmsPingTests())
		.exceptions(d.getExceptions());
		
		DiagnosticsResults results = drb.build();
		
		return results;
	}
	
	// Contact Us
	public Provider[] getProviders() {
		return update.getContactUs();
	}
}
