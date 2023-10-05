package gyk4j.wols.model.setup;

import java.time.DateTimeException;
import java.time.LocalDate;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.python.lang.global.Isp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.beans.setup.RegistrationRequest;
import gyk4j.wols.beans.setup.RegistrationResult;
import gyk4j.wols.exception.MismatchedPhoneNumberException;
import gyk4j.wols.exception.NoProviderException;
import gyk4j.wols.exception.NonAgreementException;
import gyk4j.wols.exception.PhoneNumberFormatException;

import static org.python.lang.global.ISP_CONFIG;

public class SetupRegistrationModel {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SetupRegistrationModel.class);
	
	final protected ButtonModel[] provider = {
			new JToggleButton.ToggleButtonModel(), // M1
			new JToggleButton.ToggleButtonModel(), // Singtel
			new JToggleButton.ToggleButtonModel(), // Starhub
			new JToggleButton.ToggleButtonModel()  // Simba
	};
	
//	final protected Document dob = new PlainDocument();
	final protected SpinnerModel dobYear = new SpinnerNumberModel(1980, 1900, LocalDate.now().getYear()-1, 1);
	final protected SpinnerModel dobMonth = new SpinnerNumberModel(1, 1, 12, 1);
	final protected SpinnerModel dobDay = new SpinnerNumberModel(1, 1, 31, 1);
	
	final protected ComboBoxModel<String> mobileNumberPrefix = new DefaultComboBoxModel<String>();
	final protected Document confirmMobileNumberPrefix = new PlainDocument();
	
	final protected Document mobileNumber = new PlainDocument();
	final protected Document confirmMobileNumber = new PlainDocument();
	
	final protected ButtonModel termsAndConditions = new JToggleButton.ToggleButtonModel();

	public ButtonModel getProvider(int i) {
		return provider[i];
	}
	
	public ButtonModel getProvider(Isp p) {
		int i=0;

		for(Isp isp: ISP_CONFIG.values()) {
			if(p == isp) {
				return getProvider(i);
			}
			i++;
		}
		return null;
	}
	
	public ButtonModel getProvider(String name) {
		return getProvider(ISP_CONFIG.get(name));
	}

//	public Document getDob() {
//		return dob;
//	}
	
	public SpinnerModel getDobYear() {
		return dobYear;
	}
	
	public SpinnerModel getDobMonth() {
		return dobMonth;
	}
	
	public SpinnerModel getDobDay() {
		return dobDay;
	}

	public ComboBoxModel<String> getMobileNumberPrefix() {
		return mobileNumberPrefix;
	}

	public Document getConfirmMobileNumberPrefix() {
		return confirmMobileNumberPrefix;
	}

	public Document getMobileNumber() {
		return mobileNumber;
	}

	public Document getConfirmMobileNumber() {
		return confirmMobileNumber;
	}

	public ButtonModel getTermsAndConditions() {
		return termsAndConditions;
	}
	
	public RegistrationResult checkRegistration() {
		RegistrationResult result = new RegistrationResult();
		
		for(Isp provider: ISP_CONFIG.values()) {
			if(getProvider(provider).isSelected() && 
					(provider == ISP_CONFIG.get("singtel") || provider == ISP_CONFIG.get("starhub"))) {
				result.setProvider(true);
			}
		}
		
		if(!result.isProvider()) {
			try {
				result.setProvider(false);
				throw new NoProviderException("No telco/provider selected.");
			}
			catch(NoProviderException e) {
				result.setProvider(false);
				System.err.println(e.getMessage());
			}
		}
		
		int year = ((SpinnerNumberModel) getDobYear()).getNumber().intValue();
		int month = ((SpinnerNumberModel) getDobMonth()).getNumber().intValue();
		int dayOfMonth = ((SpinnerNumberModel) getDobDay()).getNumber().intValue();
		try {
			LocalDate.of(year, month, dayOfMonth);
			result.setDobYear(true);
			result.setDobMonth(true);
			result.setDobDay(true);
		} catch (DateTimeException ex) {
			result.setDobYear(false);
			result.setDobMonth(false);
			result.setDobDay(false);
			System.err.println(ex.getMessage());
		}
		
		String prefix = (String) getMobileNumberPrefix().getSelectedItem();
		if("(+65) Singapore".equals(prefix)) {
			result.setCountry(true);
		}
		else {
			result.setCountry(false);
		}
		
		String mobileStr;
		int mobileInt = 0;
		Document mobilePhone = getMobileNumber();
		try {
			mobileStr = mobilePhone.getText(0, mobilePhone.getLength());
			mobileInt = Integer.parseInt(mobileStr);
			LOGGER.trace(Integer.toString(mobileInt));
			if(mobileInt >= 80000000 && mobileInt <= 99999999) {
				result.setMobilePhone(true);
			}
			else {
				result.setMobilePhone(false);
				throw new PhoneNumberFormatException("Invalid phone number " + mobileInt);
			}
		} catch (BadLocationException e) {
			result.setMobilePhone(false);
			LOGGER.error(e.getMessage());
		} catch (PhoneNumberFormatException e) {
			result.setMobilePhone(false);
			LOGGER.error(e.getMessage());
		} catch (NumberFormatException e) {
			result.setMobilePhone(false);
			LOGGER.error(e.getMessage());
		}
		
		String confirmStr = null;
		int confirmInt = 0;
		Document mobileConfirm = getConfirmMobileNumber();
		try {
			confirmStr = mobileConfirm.getText(0, mobileConfirm.getLength());
			confirmInt = Integer.parseInt(confirmStr);
			LOGGER.trace(Integer.toString(confirmInt));
			if(confirmInt >= 80000000 && confirmInt <= 99999999) {
				result.setConfirmPhone(true);
				
				if (mobileInt == confirmInt) {
					result.setConfirmPhone(true);
				} else {
					result.setConfirmPhone(false);
					throw new MismatchedPhoneNumberException("Confirmation number does not match mobile phone number.");
				}
			}
			else {
				result.setConfirmPhone(false);
				throw new PhoneNumberFormatException("Invalid phone number " + confirmInt);
			}
		} catch (BadLocationException e) {
			result.setConfirmPhone(false);
			LOGGER.error(e.getMessage());
		} catch (PhoneNumberFormatException e) {
			result.setConfirmPhone(false);
			LOGGER.error(e.getMessage());
		} catch (NumberFormatException e) {
			result.setConfirmPhone(false);
			LOGGER.error(e.getMessage());
		} catch (MismatchedPhoneNumberException e) {
			result.setConfirmPhone(false);
			LOGGER.error(e.getMessage());
		}
		
		boolean agree = getTermsAndConditions().isSelected();
		try {
			if(!agree) {
				result.setAgree(false);
				throw new NonAgreementException("Is not agreeable to the terms and conditions.");
			}
			else {
				result.setAgree(true);
			}
		}
		catch (NonAgreementException e) {
			result.setAgree(false);
			LOGGER.error(e.getMessage());
		}
		
		return result;
	}
	
	public RegistrationRequest createRequest() {
		RegistrationRequest registrationRequest = new RegistrationRequest();
		
		// Provider
		for(String p: ISP_CONFIG.keySet()) {
			ButtonModel m = getProvider(p);
			if(m.isSelected()) {
				registrationRequest.setProvider(p);
			}
		}
		
		// DOB
		int year = ((SpinnerNumberModel) getDobYear()).getNumber().intValue();
		int month = ((SpinnerNumberModel) getDobMonth()).getNumber().intValue();
		int dayOfMonth = ((SpinnerNumberModel) getDobDay()).getNumber().intValue();
		registrationRequest.setDob(LocalDate.of(year, month, dayOfMonth));
		
		// Country Code
		registrationRequest.setCountry("SG");
		
		// Mobile Phone
		Document mobilePhone = getMobileNumber();
		try {
			String mobileStr = mobilePhone.getText(0, mobilePhone.getLength());
			registrationRequest.setMobilePhone(mobileStr);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		// I Agree to Terms and Conditions
		registrationRequest.setAgree(getTermsAndConditions().isSelected());
		return registrationRequest;
	}
	
	public void reset() {
		for(String p: ISP_CONFIG.keySet()) {
			ButtonModel m = getProvider(p);
			m.setSelected(false);
		}
		
		((SpinnerNumberModel) getDobYear()).setValue(1980);
		((SpinnerNumberModel) getDobMonth()).setValue(1);
		((SpinnerNumberModel) getDobDay()).setValue(1);
		
		getMobileNumberPrefix().setSelectedItem("(+65) Singapore");
		
		Document d = getMobileNumber();
		try {
			d.remove(0, d.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		d = getConfirmMobileNumber();
		try {
			d.remove(0, d.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		getTermsAndConditions().setSelected(false);
	}
}
