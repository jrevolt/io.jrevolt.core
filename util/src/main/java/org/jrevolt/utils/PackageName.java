package org.jrevolt.utils;

import static org.jrevolt.utils.Assert.NOTNULL;

/**
 * @author Patrik Beno
 */
public class PackageName {

	static public PackageName DEFAULT = new PackageName("");

	String fqname;

	PackageName parent;
	String name;

	public PackageName(String fqname) {
		this.fqname = NOTNULL(fqname);
	}

	public String getFQName() {
		return fqname;
	}

	public String getName() {
		if (name != null) {
			return name;
		}

		int i = fqname.lastIndexOf('.');

		name = (i<0) ? fqname : fqname.substring(i+1);
		return name;
	}

	public PackageName getParent() {
		if (parent != null) {
			return parent;
		}

		int offset = fqname.length() - getName().length() - 1;
		parent = (offset > 0)
				? new PackageName(fqname.substring(0, offset))
				: PackageName.DEFAULT;

		return parent;
	}

	public boolean isDefault() {
		return getFQName().equals("");
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PackageName that = (PackageName) o;

		if (!fqname.equals(that.fqname)) return false;

		return true;
	}

	public int hashCode() {
		return fqname.hashCode();
	}

	public String toString() {
		return getFQName();
	}
}
