package org.python.lang.error;

public class Exn extends java.lang.Exception {
	private static final long serialVersionUID = 369694870443838757L;
	protected String message;

	public Exn(String message) {
		super(message);
		this.message = message;
	}
}
