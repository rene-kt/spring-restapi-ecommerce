package com.rene.ecommerce.exceptions;

public class UserHasProductsRelationshipsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public UserHasProductsRelationshipsException() {
		super("You've already bought or sold products, then you can't delete your own account");
	}

	public UserHasProductsRelationshipsException(String msg) {
		super(msg);
	}

	public UserHasProductsRelationshipsException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
