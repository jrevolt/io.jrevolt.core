package io.jrevolt.core.util;

/**
 * @author Patrik Beno
 */
public interface Chain<T extends Chain<T>> extends Iterable<T> {

	interface Internal {
		void setPrevious(Internal previous);
		void setNext(Internal next);
	}

	T previous();

	T next();

	T head();

	T tail();

	T append(T element);

	T insert(T element);

	T truncate();

	T delete();

	/**
	 * Length of this chain from this element to tail().
	 * To determine full chain length, navigate to head() (head().length()).
	 * @return
	 */
	int length();

	/**
	 * index of this element in the whole chain
	 * @return
	 */
	int index();

	boolean isHead();

	boolean isTail();
}
