package org.jrevolt.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Juraj Burian
 */
public class ConvertingCollection<E, F> implements Collection<E> {

	protected final Collection<F> collection;

	protected Convertor<E, F> convertor = new Convertor<E, F>();

	public ConvertingCollection(
			Collection<F> collection,
			Convertor<E, F> convertor) {
		this.collection = collection;
		if (convertor != null) {
			this.convertor = convertor;
		}
	}

	public boolean add(E o) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean contains(Object o) {
		Iterator<E> e = iterator();
		if (o == null) {
			while (e.hasNext()) {
				if (e.next() == null) {
					return true;
				}
			}
		} else {
			while (e.hasNext()) {
				if (o.equals(e.next())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		Iterator<?> e = c.iterator();
		while (e.hasNext()) {
			if (!contains(e.next())) {
				return false;
			}
		}
		return true;
	}

	public boolean isEmpty() {
		return collection.isEmpty();
	}

	public Iterator<E> iterator() {
		return new Iterator<E>() {

			private final Iterator<F> it = collection.iterator();

			public boolean hasNext() {
				return it.hasNext();
			}

			public E next() {
				return convertor.execute(it.next());
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		return collection.size();
	}

	public Object[] toArray() {
		Object[] result = new Object[size()];
		Iterator<E> e = iterator();
		for (int i = 0; e.hasNext(); i++) {
			result[i] = e.next();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		int size = size();
		if (a.length < size) {
			a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
		}
		Iterator<E> it = iterator();
		Object[] result = a;
		for (int i = 0; i < size; i++) {
			result[i] = it.next();
		}
		if (a.length > size) {
			a[size] = null;
		}
		return a;
	}

}
