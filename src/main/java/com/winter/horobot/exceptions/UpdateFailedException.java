package com.winter.horobot.exceptions;

public class UpdateFailedException extends Exception {

	public UpdateFailedException() {
		super();
	}

	public UpdateFailedException(String m) {
		super(m);
	}

	public UpdateFailedException(String m, Throwable c) {
		super(m, c);
	}
}