package com.antonio.meli.exception;

public class ADNexception extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mensaje;
	
	public ADNexception(String message) {
		super(message);
		this.mensaje=message;
	}

	
}
