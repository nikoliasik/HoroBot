package com.winter.horobot.exceptions;

public class MissingLocaleException extends Exception {

	public MissingLocaleException() {
		super();
	}

	public MissingLocaleException(String m) {
		super(m);
	}

	public MissingLocaleException(String m, Throwable c) {
		super(m, c);
	}
}
