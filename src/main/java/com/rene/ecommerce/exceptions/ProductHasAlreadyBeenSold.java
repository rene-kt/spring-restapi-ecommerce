package com.rene.ecommerce.exceptions;

public class ProductHasAlreadyBeenSold extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public ProductHasAlreadyBeenSold() {
		super("The product has already been sold");
	}

	public ProductHasAlreadyBeenSold(String msg) {
		super(msg);
	}

	public ProductHasAlreadyBeenSold(String msg, Throwable cause) {
		super(msg, cause);
	}
}
