package org.jrevolt.utils;

/**
 * @author Patrik Beno
 */
public class Countdown {

	private int initial;
	private int remaining;

	public Countdown(int initial) {
		this.initial = initial;
		this.remaining = initial;
	}

	public int initial() {
		return initial;
	}

	public int remaining() {
		return remaining;
	}

	public boolean next() {
		return (remaining > 0) && remaining-- > 0;
	}


}
