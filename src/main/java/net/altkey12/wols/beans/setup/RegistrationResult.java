package net.altkey12.wols.beans.setup;

public class RegistrationResult {
	boolean provider = false;
	
	boolean dobYear = false;
	boolean dobMonth = false;
	boolean dobDay = false;
	
	boolean country = false;
	
	boolean mobilePhone = false;
	boolean confirmPhone = false;
	
	boolean agree = false;
	
	public boolean isOK() {
		return provider & dobYear & dobMonth & dobDay & country & mobilePhone & confirmPhone & agree;
	}

	public boolean isProvider() {
		return provider;
	}

	public void setProvider(boolean provider) {
		this.provider = provider;
	}

	public boolean isDobYear() {
		return dobYear;
	}

	public void setDobYear(boolean dobYear) {
		this.dobYear = dobYear;
	}

	public boolean isDobMonth() {
		return dobMonth;
	}

	public void setDobMonth(boolean dobMonth) {
		this.dobMonth = dobMonth;
	}

	public boolean isDobDay() {
		return dobDay;
	}

	public void setDobDay(boolean dobDay) {
		this.dobDay = dobDay;
	}

	public boolean isCountry() {
		return country;
	}

	public void setCountry(boolean country) {
		this.country = country;
	}

	public boolean isMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(boolean mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public boolean isConfirmPhone() {
		return confirmPhone;
	}

	public void setConfirmPhone(boolean confirmPhone) {
		this.confirmPhone = confirmPhone;
	}

	public boolean isAgree() {
		return agree;
	}

	public void setAgree(boolean agree) {
		this.agree = agree;
	}
	
}
