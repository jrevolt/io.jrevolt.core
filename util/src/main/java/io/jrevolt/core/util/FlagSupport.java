package io.jrevolt.core.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public abstract class FlagSupport<E extends Enum> {

	protected long flags;

	abstract
	protected Class<E> type();

	public Set<E> all() {
		if (flags == 0) {
			return Collections.emptySet();
		}
		Set<E> result = new HashSet<E>();
		for (E flag : type().getEnumConstants()) {
			if (isSet(flag)) {
				result.add(flag);
			}
		}
		return result;
	}

	public boolean isSet(E flag) {
		return (flags & mask(flag)) != 0;
	}

	public boolean isAllSet(E ... flags) {
		boolean result = false;
		for (E f : flags) {
			result |= isSet(f);
		}
		return result;
	}

	public FlagSupport<E> reset() {
		flags = 0;
		return this;
	}

	public FlagSupport<E> clear(E flag) {
		flags &= ~mask(flag);
		return this;
	}

	public FlagSupport<E> clearAll(E ... flags) {
		for (E flag : flags) {
			clear(flag);
		}
		return this;
	}

	public FlagSupport<E> set(E flag) {
		flags |= mask(flag);
		return this;
	}

	public FlagSupport<E> setAll(E ... flags) {
		for (E flag : flags) {
			set(flag);
		}
		return this;
	}

	protected int mask(E flag) {
		return 1 << flag.ordinal();
	}

}
