package com.rene.ecommerce.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rene.ecommerce.exceptions.ProductHasAlreadyBeenSold;

@ControllerAdvice
public class ResourceExceptionHandler {

	
	  @ExceptionHandler(ProductHasAlreadyBeenSold.class)
	    public ResponseEntity<StandardError> productHasAlreadyBeenSold(ProductHasAlreadyBeenSold e, HttpServletRequest request) {

	        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
	                "Product has already been sold", e.getMessage(), request.getRequestURI());

	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	    }
}
