package io.jrevolt.core.util;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class Stack<T> implements Iterable<T> {

	protected int size;
	protected int limit;

	protected Entry<T> current;

	public void push(T object) {
		Assert.notNull(object);
		current = new Entry<T>(current, object);
		size++;
	}

	public T pop() {
		if (current == null) {
			throw new EmptyStackException();
		}
		T object = current.object;
		current = current.previous;
		size--;
		return object;
	}

	public T peek() {
		if (current == null) {
			throw new JackieException("Empty stack!");
		}
		return current.object;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		boolean empty = (size == 0);
		return empty;
	}

	public void clear() {
		current = null;
		size = 0;
	}

	public boolean contains(T item) {
		for (T t : this) {
			if (t.equals(item)) {
				return true;
			}
		}
		return false;
	}

	public Iterator<T> iterator() {
		return new EntryIterator<T>(current);
	}


	static class Entry<T> {
		Entry<T> previous;
		T object;

		Entry(Entry<T> previous, T object) {
			this.previous = previous;
			this.object = object;
		}
	}

	static class EntryIterator<T> implements Iterator<T> {

		Entry<T> current;

		public EntryIterator(Entry<T> current) {
			this.current = current;
		}

		public boolean hasNext() {
			return current != null;
		}

		public T next() {
			T o = current.object;
			current = current.previous;
			return o;
		}

		public void remove() {
			throw Assert.notYetImplemented(); /* todo implement method remove */
		}
	}

	@Override
	public String toString() {
		List<T> list = new ArrayList<T>(size());
		for (T t : this) {
			list.add(t);
		}
		return list.toString();
	}
}
