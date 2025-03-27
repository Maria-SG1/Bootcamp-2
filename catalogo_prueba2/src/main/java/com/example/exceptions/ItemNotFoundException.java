package com.example.exceptions;

public class ItemNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private final static String MESSAGE_STRING = "Not found";
	
	public ItemNotFoundException() {
		this(MESSAGE_STRING);
	}

	public ItemNotFoundException(String message) {
		super(message);
	}

	public ItemNotFoundException(Throwable cause) {
		this(MESSAGE_STRING, cause);
	}

	public ItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ItemNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
