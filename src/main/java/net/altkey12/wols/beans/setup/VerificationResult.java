package net.altkey12.wols.beans.setup;

public class VerificationResult {
	boolean pin = false;
	
	public boolean isOK() {
		return isPin();
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}
}
