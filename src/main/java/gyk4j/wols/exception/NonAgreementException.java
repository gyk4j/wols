package gyk4j.wols.exception;

public class NonAgreementException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public NonAgreementException() {
		super();
	}

	public NonAgreementException(String s) {
		super(s);
	}

	public NonAgreementException(Throwable cause) {
		super(cause);
	}

	public NonAgreementException(String message, Throwable cause) {
		super(message, cause);
	}

}
