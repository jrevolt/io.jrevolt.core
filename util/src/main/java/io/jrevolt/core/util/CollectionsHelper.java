package io.jrevolt.core.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class CollectionsHelper {
	static public <T> Iterable<T> iterable(final T[] iterable) {
		class IterableIterator implements Iterable<T>, Iterator<T> {

			int offset;

			public Iterator<T> iterator() {
				return this;
			}

			public boolean hasNext() {
				return offset < iterable.length;
			}

			public T next() {
				return iterable[offset++];
			}

			public void remove() {
				throw Assert.unsupported();
			}
		}

		return (iterable != null && iterable.length>0) ? new IterableIterator() : Collections.<T>emptyList();
	}

	static public <T> Iterable<T> iterable(Iterable<T> iterable) {
		return (iterable != null) ? iterable : Collections.<T>emptyList();
	}

	static public <T> Collection<T> collection(Collection<T> col) {
		return (col != null) ? col : Collections.<T>emptyList();
	}

	static public <K,V> Map<K,V> map(Map<K,V> map) {
		return (map != null) ? map : Collections.<K,V>emptyMap();
	}

	static public boolean isEmpty(Collection<?> col) {
		return col == null || col.isEmpty();
	}

	static public int sizeof(Collection<?> col) {
		return col != null ? col.size() : 0;
	}
}
