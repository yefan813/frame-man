package com.frame.common.exception;

public class BusiException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8663493901686010719L;

	public BusiException(String message) {
		super(message);
	}

	public BusiException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusiException(Throwable cause) {
		super(cause);
	}
}
