package io.jrevolt.core.util;

import java.util.List;
import java.util.AbstractList;

/**
 * @author Patrik Beno
 */
public class ArrayHelper {

	static public <T> List<T> asImmutableList(final T ... array) {
		return new AbstractList<T>() {
			public T get(int index) {
				return array[index];
			}
			public int size() {
				return array.length;
			}
		};
	}

}
