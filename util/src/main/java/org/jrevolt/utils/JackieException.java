package org.jrevolt.utils;

/**
 * @author Patrik Beno
 */
public class JackieException extends RuntimeException {

	public JackieException(Throwable thrown, String msg, Object... args) {
		super(String.format(msg, args), thrown);
	}

	public JackieException(Throwable cause) {
		super(cause);
	}

	public JackieException(String msg, Object... args) {
		super(String.format(msg, args));
	}
}
