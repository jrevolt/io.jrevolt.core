package org.jrevolt.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Patrik Beno
 */
public class Log {

	static final long START = System.currentTimeMillis();

	enum Level {
		ALL, DBG, TRC, INF, WRN, ERR, NOTHING
	}

	static final Level MINLEVEL = Level.valueOf(System.getProperty("Log.MINLEVEL", Level.INF.name()));
	static final boolean FILE = false;

	static public void enter() {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		log(Level.TRC, ">>> at %s.%s(%s:%s)", ste.getClassName(), ste.getMethodName(), ste.getFileName(), ste.getLineNumber());
	}

	static public void leave() {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		log(Level.TRC, "<<< at %s.%s(%s:%s)", ste.getClassName(), ste.getMethodName(), ste.getFileName(), ste.getLineNumber());
	}

	static public void debug(String msg, Object... args) {
		log(Level.DBG, msg, args);
	}

	static public void info(String msg, Object... args) {
		log(Level.INF, msg, args);
	}

	static public void warn(String msg, Object... args) {
		log(Level.WRN, msg, args);
	}

	static public void error(String msg, Object... args) {
		log(Level.ERR, msg, args);
	}

	static PrintStream out;

	static {
		try {
			out = FILE ? new PrintStream(new File(".jackie.log")) : null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static public void log(Level level, String msg, Object... args) {
		if (out != null) {
			out.printf("[%s] ", level);
			out.printf(msg, args);
			out.println();
		}

		if (level.ordinal() < MINLEVEL.ordinal()) {
			return;
		}

//		System.out.printf("%5d [%s] ", (System.currentTimeMillis()-START), level);
		System.out.printf("[%s] ", level);
		System.out.printf(msg, args);
		System.out.println();
	}
}
