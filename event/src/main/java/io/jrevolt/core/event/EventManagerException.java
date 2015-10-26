package io.jrevolt.core.event;

/**
 * @author Patrik Beno
 */
public class EventManagerException extends RuntimeException {

	public EventManagerException(String message, Object ... args) {
		super(String.format(message, args));
	}
	public EventManagerException(Throwable cause, String message, Object ... args) {
		super(String.format(message, args), cause);
	}
}
