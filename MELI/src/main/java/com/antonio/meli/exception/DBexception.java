package com.antonio.meli.exception;

public class DBexception extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String mensaje;

	public DBexception(String message) {
		super(message);
		this.mensaje= message;
	}
	
	
}
