package net.altkey12.wols.model.setup;

import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import net.altkey12.wols.beans.setup.VerificationRequest;
import net.altkey12.wols.beans.setup.VerificationResult;
import net.altkey12.wols.exception.PinNumberFormatException;

public class SetupVerificationModel {
	final protected Document phone;
	final protected Document pin = new PlainDocument();
	
	public SetupVerificationModel(Document phone) {
		this.phone = phone;
	}
	
	public String getMobilePhone() {
		String mobilePhone = null;
		int start = phone.getStartPosition().getOffset();
		try {
			mobilePhone = phone.getText(start, phone.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return mobilePhone;
	}
	
	public Document getPhone() {
		return phone;
	}

	public Document getPin() {
		return pin;
	}
	
	public VerificationResult checkVerification() {
		VerificationResult result = new VerificationResult();
		
		
		Document pin = getPin();
		try {
			String pinStr = pin.getText(0, pin.getLength());
			
			if(Pattern.matches("[0-9]{6}", pinStr)) {
				result.setPin(true);
				
			}
			else {
				result.setPin(false);
				throw new PinNumberFormatException("Pin number is malformed." + pinStr);
			}
		} catch (BadLocationException e) {
			result.setPin(false);
			System.err.println(e.getMessage());
		} catch (PinNumberFormatException e) {
			result.setPin(false);
			System.err.println(e.getMessage());
		} catch (NumberFormatException e) {
			result.setPin(false);
			System.err.println(e.getMessage());
		}
		
		return result;
	}
	
	public VerificationRequest createRequest() {
		VerificationRequest verificationRequest = new VerificationRequest();
		
		try {
			String pinStr = pin.getText(0, pin.getLength());
			verificationRequest.setPin(pinStr);
		} catch (BadLocationException e) {
			System.err.println(e.getMessage());
		}
		
		return verificationRequest;
	}
	
	public void reset() {
		// Clear verification PIN box
		Document d = getPin();
		try {
			d.remove(0, d.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
