package com.rene.ecommerce.exceptions;

public class DuplicateEntryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public DuplicateEntryException() {
		super("This email is already being used");
	}

	public DuplicateEntryException(String msg) {
		super(msg);
	}

	public DuplicateEntryException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
