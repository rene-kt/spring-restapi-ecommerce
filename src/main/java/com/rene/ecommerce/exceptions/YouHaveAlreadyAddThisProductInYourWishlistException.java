package com.rene.ecommerce.exceptions;

public class YouHaveAlreadyAddThisProductInYourWishlistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public YouHaveAlreadyAddThisProductInYourWishlistException() {
		super("You've already add this product in your wishlist");
	}

	public YouHaveAlreadyAddThisProductInYourWishlistException(String msg) {
		super(msg);
	}

	public YouHaveAlreadyAddThisProductInYourWishlistException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
