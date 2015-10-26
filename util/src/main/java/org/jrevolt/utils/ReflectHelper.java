package org.jrevolt.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Patrik Beno
 */
public class ReflectHelper<T> {

	static public <T> ReflectHelper<T> reflectHelper(Class<T> type) {
		return new ReflectHelper<T>(type);
	}

	Class<T> type;

	public ReflectHelper(Class<T> type) {
		this.type = type;
	}

	public ConstructorHelper<T> getConstructor(Class ... args) {
		return new ConstructorHelper<T>(type, args);
	}

	public class ConstructorHelper<T> {

		Constructor<T> constructor;

		public ConstructorHelper(Class<T> type, Class ... args) {
			try {
				constructor = type.getConstructor(args);
			} catch (NoSuchMethodException e) {
				throw Assert.assertFailed(e, "No such constructor");
			}
		}

		public T newInstance(Object ... args) {
			try {
				T instance = constructor.newInstance(args);
				return instance;
			} catch (InstantiationException e) {
				throw Assert.assertFailed(e, "Error in newInstance()");
			} catch (IllegalAccessException e) {
				throw Assert.assertFailed(e, "Error in newInstance()");
			} catch (InvocationTargetException e) {
				throw Assert.assertFailed(e, "Error in newInstance()"); // fixme handle/support user exceptions
			}
		}

	}
}
