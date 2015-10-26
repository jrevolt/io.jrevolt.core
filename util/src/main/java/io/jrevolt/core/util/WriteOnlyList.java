package io.jrevolt.core.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import java.util.ListIterator;

/**
 * @author Patrik Beno
 */
public class WriteOnlyList<T> implements List<T> {

	List<T> list;

	public WriteOnlyList(List<T> list) {
		this.list = list;
	}

	public WriteOnlyList() {
		this(new ArrayList<T>());
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public Iterator<T> iterator() {
		final Iterator<T> iterator = list.iterator();
		return new Iterator<T>() {
			public boolean hasNext() {
				return iterator.hasNext();
			}
			public T next() {
				return iterator.next();
			}
			public void remove() {
				throw Assert.unsupported();
			}
		};
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	public boolean add(T t) {
		return list.add(t);
	}

	public boolean remove(Object o) {
		throw Assert.unsupported();
	}

	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	public boolean addAll(Collection<? extends T> c) {
		return list.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		return list.addAll(index, c);
	}

	public boolean removeAll(Collection<?> c) {
		throw Assert.unsupported();
	}

	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	public void clear() {
		throw Assert.unsupported();
	}

	public boolean equals(Object o) {
		return list.equals(o);
	}

	public int hashCode() {
		return list.hashCode();
	}

	public T get(int index) {
		return list.get(index);
	}

	public T set(int index, T element) {
		throw Assert.unsupported();
	}

	public void add(int index, T element) {
		list.add(index, element);
	}

	public T remove(int index) {
		throw Assert.unsupported();
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<T> listIterator() {
		final ListIterator<T> iterator = list.listIterator();
		return wrapListIterator(iterator);
	}

	public ListIterator<T> listIterator(int index) {
		return list.listIterator(index);
	}

	public List<T> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	ListIterator wrapListIterator(final ListIterator<T> iterator) {
		return new ListIterator<T>() {
			public boolean hasNext() {
				return iterator.hasNext();
			}
			public T next() {
				return iterator.next();
			}
			public boolean hasPrevious() {
				return iterator.hasPrevious();
			}
			public T previous() {
				return iterator.previous();
			}
			public int nextIndex() {
				return iterator.nextIndex();
			}
			public int previousIndex() {
				return iterator.previousIndex();
			}
			public void remove() {
				throw Assert.unsupported();
			}
			public void set(T t) {
				throw Assert.unsupported();
			}
			public void add(T t) {
				iterator.add(t);
			}
		};
	}
}
