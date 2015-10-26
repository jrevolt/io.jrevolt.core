package io.jrevolt.core.event.impl;

import static io.jrevolt.core.util.JavaHelper.FALSE;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Formattable;
import java.util.Formatter;

/**
 * @author Patrik Beno
 */
public class MethodDef {

	Method method;

	public MethodDef(Method method) {
		this.method = method;
	}

	public Method getMethod() {
		return method;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MethodDef other = (MethodDef) o;

		if (FALSE(method.getName().equals(other.method.getName()))) { return false; }
		if (FALSE(Arrays.equals(method.getParameterTypes(), other.method.getParameterTypes()))) { return false; }

		return true;
	}

	public int hashCode() {
		return hashCode(method.getName(), method.getParameterTypes());
	}

	public String toString() {
		return String.format(
				"%s(%s)",
				method.getName(),
				new Formattable() {
					public void formatTo(Formatter f, int flags, int width, int precision) {
						int idx = 0;
						for (Class<?> cls : method.getParameterTypes()) {
							String pattern = (idx > 0) ? ", %s" : "%s";
							f.format(pattern, cls.getSimpleName());
						}
					}
				});
	}

	static public int hashCode(Object ... objects) {
		int result = 0;
		for (Object o : objects) {
			if (o.getClass().isArray()) {
				result = 31 * result + Arrays.hashCode((Object[]) o);
			} else {
				result = 31 * result + o.hashCode();
			}
		}
		return result;
	}

//	static private int arrayHashCode(Object array) {
//		array.getClass().getComponentType()
//		if ()
//	}
}
