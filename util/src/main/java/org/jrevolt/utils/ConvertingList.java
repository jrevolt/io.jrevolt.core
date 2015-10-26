package org.jrevolt.utils;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Juraj Burian
 *
 * @param <E>
 * @param <F>
 */
public class ConvertingList<E, F> extends ConvertingCollection<E, F>
		implements
		List<E>
{

	class LIt implements ListIterator<E> {

		private final ListIterator<F> it;

		LIt(ListIterator<F> it) {
			this.it = it;
		}

		public void add(E o) {
			throw new UnsupportedOperationException();

		}

		public boolean hasNext() {
			return it.hasNext();
		}

		public boolean hasPrevious() {
			return it.hasPrevious();
		}

		public E next() {
			return convertor.execute(it.next());
		}

		public int nextIndex() {
			return it.nextIndex();
		}

		public E previous() {
			return convertor.execute(it.previous());
		}

		public int previousIndex() {
			return it.previousIndex();
		}

		public void remove() {
			throw new UnsupportedOperationException();

		}

		public void set(E o) {
			throw new UnsupportedOperationException();

		}
	}

	public ConvertingList(List<F> list, Convertor<E, F> convertor) {
		super(list, convertor);
	}

	public void add(int index, E element) {
		throw new UnsupportedOperationException();

	}

	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	public E get(int index) {
		return convertor.execute(((List<F>) collection).get(index));
	}

	public int indexOf(Object o) {
		if (o == null) {
			return -1;
		}
		List<F> l = (List<F>) collection;
		for (int i = 0; i < l.size(); i++) {
			if (o.equals(convertor.execute(l.get(i)))) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(Object o) {
		if (o == null) {
			return -1;
		}
		List<F> l = (List<F>) collection;
		int j = -1;
		for (int i = 0; i < l.size(); i++) {
			if (o.equals(convertor.execute(l.get(i)))) {
				j = i;
			}
		}
		return j;
	}

	public ListIterator<E> listIterator() {
		return new LIt(((List<F>) collection).listIterator());
	}

	public ListIterator<E> listIterator(int index) {
		return new LIt(((List<F>) collection).listIterator(index));
	}

	public E remove(int index) {
		throw new UnsupportedOperationException();
	}

	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
