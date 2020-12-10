package com.rene.ecommerce.exceptions;

public class ClientHasBoughtProductsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public ClientHasBoughtProductsException() {
		super("You've already bought products, then you can't delete your own account");
	}

	public ClientHasBoughtProductsException(String msg) {
		super(msg);
	}

	public ClientHasBoughtProductsException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
