package org.jrevolt.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Patrik Beno
 */
public class JavaHelper {

	/**
	 * Syntax cleanup: avoid <code>!condition</code> (exclamation mark can easily be overloooked)
	 * @param condition
	 * @return
	 */
	static public boolean FALSE(boolean condition) {
		return condition == false;
	}

	/**
	 * Syntax cleanup: complementary function for FALSE()
	 * @param condition
	 * @return
	 */
	static public boolean TRUE(boolean condition) {
		return condition == true;
	}

	static public boolean isSet(int flags, int test) {
		return (flags & test) == test;
	}


	static public Constructor getConstructor(Class cls, Class... args) {
		try {
			return cls.getConstructor(args);
		} catch (NoSuchMethodException e) {
			throw Assert.notYetHandled(e);
		}
	}

	static public Method getMethod(Class cls, String name, Class... args) {
		try {
			return cls.getMethod(name, args);
		} catch (NoSuchMethodException e) {
			throw Assert.notYetHandled(e);
		}
	}

}
