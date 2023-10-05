package gyk4j.wols.exception;

public class NoProviderException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public NoProviderException() {
		super();
	}

	public NoProviderException(String s) {
		super(s);
	}

	public NoProviderException(Throwable cause) {
		super(cause);
	}

	public NoProviderException(String message, Throwable cause) {
		super(message, cause);
	}

}
