package io.jrevolt.core.util;

import static io.jrevolt.core.util.CollectionsHelper.iterable;

import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * @author Patrik Beno
 */
public class DependencyInfo implements Comparable<DependencyInfo> {

	private String name;

	private Set<DependencyInfo> dependencies;

	public DependencyInfo(String name) {
		this.name = name;
		this.dependencies = new TreeSet<DependencyInfo>();
	}

	public String name() {
		return name;
	}

	public Set<? extends DependencyInfo> dependencies() {
		return dependencies;
	}

	public <T extends DependencyInfo> void depend(T ... dependencies) {
		this.dependencies.addAll(Arrays.asList(dependencies));
	}

	public List<? extends DependencyInfo> sortDependencies() {
		List<DependencyInfo> sorted = new ArrayList<DependencyInfo>();
		Stack<DependencyInfo> inprogress = new Stack<DependencyInfo>();
		for (DependencyInfo m : dependencies) {
			process(m, inprogress, sorted);
		}
		return sorted;
	}

	/**
	 * Silly and slow but very simple topo-sort algorithm (usable for small graphs or where speed
	 * is not critical).
	 *
	 * @param dependency dependency to process
	 * @param inprogress dependencies currently processed (for cycle detection)
	 * @param sorted where induvidual accepted dependencies are added (in dependency order starting
	 * from independent items)
	 */
	protected void process(DependencyInfo dependency, Stack<DependencyInfo> inprogress, List<DependencyInfo> sorted) {
		if (inprogress.contains(dependency)) {
			throw new JackieException(
					"Cycle? Offending dependency: %s. Processing stack: %s. Already resolved/accepted/sorted: %s",
					dependency, inprogress, sorted);
		}

		// remember this (cycle detection) - recursion prevention
		inprogress.push(dependency);

		for (DependencyInfo d : iterable(dependency.dependencies)) {
			if (sorted.contains(d)) {
				continue;
			}
			process(d, inprogress, sorted);
		}
		if (!sorted.contains(dependency)) {
			sorted.add(dependency);
		}

		// done - leave this
		inprogress.pop();
	}

	public int compareTo(DependencyInfo o) {
		return name.compareTo(o.name);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DependencyInfo that = (DependencyInfo) o;

		return name.equals(that.name);
	}

	public int hashCode() {
		return name.hashCode();
	}

	public String toString() {
		return name;
	}
}
