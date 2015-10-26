package org.jrevolt.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class Dependency<T> implements Comparable<Dependency> {

	static public class Comparator implements java.util.Comparator<Dependency> {
		public int compare(Dependency o1, Dependency o2) {
			return o1.compareTo(o2);
		}
	}

	protected T object;
	protected Set<Dependency> dependencies;

	public Dependency(T object) {
		this.object = object;
		this.dependencies = new HashSet<Dependency>();
	}

	public Dependency(T object, Collection<Dependency> dependencies) {
		this(object);
		this.dependencies.addAll(dependencies);
	}

	public Dependency(T object, Dependency... dependencies) {
		this(object, Arrays.asList(dependencies));
	}

	public List<Dependency> getSortedDependencies() {
		Set<Dependency> alldeps = gatherAllDependencies().keySet();
		List<Dependency> sorted = sort(alldeps);
		return sorted;
	}

	protected List<Dependency> sort(Collection<Dependency> deps) {
		Set<Dependency> source = new HashSet<Dependency>(deps);
		List<Dependency> sorted = new ArrayList<Dependency>(deps.size());
		while (!source.isEmpty()) {
			Iterator<Dependency> it = source.iterator();
			while (it.hasNext()) {
				Dependency d = it.next();
				if (sorted.containsAll(d.dependencies)) {
					sorted.add(d);
					it.remove();
				}
			}
		}
		return sorted;
	}

	protected Map<Dependency, Object> gatherAllDependencies() {
		return gatherAllDependencies(null, dependencies);
	}

	protected Map<Dependency, Object> gatherAllDependencies(Map<Dependency, Object> processed,
																			  Set<Dependency> dependencies) {
		if (processed == null) {
			processed = new IdentityHashMap<Dependency, Object>();
		}
		for (Dependency d : dependencies) {
			if (processed.containsKey(d)) {
				continue; // avoid duplicate processing
			}
			processed.put(d, d.object);
			gatherAllDependencies(processed, d.dependencies);
		}

		return processed;
	}

	public int compareTo(Dependency other) {
		if (dependencies.contains(other)) {
			return 1;
		} else if (other.dependencies.contains(this)) {
			return -1;
		} else if (object instanceof Comparable) {
			return ((Comparable) object).compareTo(other.object);
		} else {
			int diff = hashCode() - other.hashCode();
			return diff < 0 ? -1 : diff > 0 ? 1 : 0;
		}
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Dependency that = (Dependency) o;

		if (!dependencies.equals(that.dependencies)) return false;
		if (!object.equals(that.object)) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = object.hashCode();
		result = 31 * result + dependencies.hashCode();
		return result;
	}

	public String toString() {
		return object.toString();
	}
}
