package io.jrevolt.core.util;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class Mapping<L,R> {

	static public class Entry<L,R> {
		L left;
		R right;

		Entry(L left, R right) {
			this.left = left;
			this.right = right;
		}
	}

	List<Entry<L,R>> entries;

	{
		entries = new ArrayList<Entry<L,R>>();
	}

	public void add(L left, R right) {
		entries.add(new Entry<L, R>(left, right));
	}

	public L findLeft(R right) {
		for (Entry<L,R> e : entries) {
			if (e.right.equals(right)) {
				return e.left;
			}
		}
		return null;
	}

	public R findRight(L left) {
		for (Entry<L,R> e : entries) {
			if (e.left.equals(left)) {
				return e.right;
			}
		}
		return null;
	}

}
