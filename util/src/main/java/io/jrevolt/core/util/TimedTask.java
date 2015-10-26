package io.jrevolt.core.util;

/**
 * @author Patrik Beno
 */
public class TimedTask {

	long started;
	long finished;

	static public TimedTask started() {
		return new TimedTask().start();
	}

	public TimedTask() {
	}

	public long duration() {
		long end = (finished > 0L) ? finished : System.currentTimeMillis();
		return end - started;
	}

	public TimedTask start() {
		started = System.currentTimeMillis();
		return this;
	}

	public TimedTask stop() {
		finished = System.currentTimeMillis();
		return this;
	}
}
