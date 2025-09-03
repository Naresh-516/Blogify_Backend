package com.nt.exception;

public class PasswordMismatchException extends RuntimeException{
	public PasswordMismatchException(String msg) {
		super(msg);
	}

}
