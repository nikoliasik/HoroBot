package com.winter.horobot.exceptions;

public class NoResultsException extends Exception {

	public NoResultsException() {
		super();
	}

	public NoResultsException(String m) {
		super(m);
	}

	public NoResultsException(String m, Throwable c) {
		super(m, c);
	}
}
