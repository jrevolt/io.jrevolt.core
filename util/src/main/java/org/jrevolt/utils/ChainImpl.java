package org.jrevolt.utils;

import static org.jrevolt.utils.Assert.doAssert;
import org.jrevolt.utils.Chain.Internal;

import java.util.Iterator;

/**
 * @author Patrik Beno
 */
public class ChainImpl<T extends Chain<T>> implements Chain<T>, Internal {

	private T previous;
	private T next;

	public T previous() {
		return previous;
	}

	public T next() {
		return next;
	}

	public T head() {
		T element = $this();
		while (element.previous() != null) {
			element = element.previous();
		}
		return element;
	}

	public T tail() {
		T element = $this();
		while (element.next() != null) {
			element = element.next();
		}
		return element;
	}

	public T append(T element) {
		doAssert(next == null, "cannot append(). Use truncate()+append() or insert()");
		link($this(), element);
		return tail();
	}

	/**
	 * inserts element at current position (element becomes this' predecessor /this.previous/);
	 * its' effectivelly prepend(), maybe that would be the better name
	 * @param element
	 * @return always this, allowing to do successive chained inserts
	 */
	public T insert(T element) {
		link(previous, element);
		link(element, $this());
		return $this();
	}

	public T truncate() {
		link($this(), null);
		return $this();
	}

	public T delete() {
		link(previous, next);
		return next != null ? next : previous;
	}

	public int length() {
		int size = 0;
		T element = $this();
		while (element != null) {
			size++;
			element = element.next();
		}
		return size;
	}

	public int index() {
		int index = -1;
		T element = $this();
		while (element != null) {
			index++;
			element = element.previous();
		}
		return index;
	}

	public boolean isHead() {
		return previous == null;
	}

	public boolean isTail() {
		return next == null;
	}

	private void link(T previous, T next) {
		link((Internal) previous, (Internal) next);
	}

	private void link(Internal previous, Internal next) {
		if (previous != null) { previous.setNext(next); }
		if (next != null) { next.setPrevious(previous); }
	}

	private T $this() {
		//noinspection unchecked
		return (T) this;
	}

	public Iterator<T> iterator() {
		return new Iterator<T>() {

			T element = $this();

			public boolean hasNext() {
				return element != null;
			}

			public T next() {
				T next = element;
				element = element.next();
				return next;
			}

			public void remove() {
				element = element.delete();
			}
		};
	}

	public void setPrevious(Internal previous) {
		this.previous = (T) previous;
	}

	public void setNext(Internal next) {
		this.next = (T) next; 
	}
}
