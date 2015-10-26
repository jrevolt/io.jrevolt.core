package io.jrevolt.core.util;

/**
 * @author Patrik Beno
 */
public class ObjectWrapper<T> {

	private T object;

	public ObjectWrapper() {
	}

	public ObjectWrapper(T object) {
		this.object = object;
	}

	public T get() {
		return object;
	}

	public void set(T object) {
		this.object = object;
	}


}
