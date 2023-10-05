package net.altkey12.wols.exception;

public class MismatchedPhoneNumberException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public MismatchedPhoneNumberException() {
		super();
	}

	public MismatchedPhoneNumberException(String s) {
		super(s);
	}

	public MismatchedPhoneNumberException(Throwable cause) {
		super(cause);
	}

	public MismatchedPhoneNumberException(String message, Throwable cause) {
		super(message, cause);
	}

}
