package io.jrevolt.core.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class LightSet<T> implements Set<T> {

   protected Set<T> set;

   protected void init() {
      set = new HashSet<T>();
   }

   public int size() {
      return (set != null) ? set.size() : 0;
   }

   public boolean isEmpty() {
      return set.size() == 0;
   }

   public boolean contains(Object o) {
      return set != null && set.contains(o);
   }

   public Iterator<T> iterator() {
      return (set != null) ? set.iterator() : Collections.<T>emptySet().iterator();
   }

   public Object[] toArray() {
      return (set != null) ? set.toArray() : new Object[0];
   }

   public <T> T[] toArray(T[] a) {
      return (set != null) ? set.toArray(a) : (T[]) Array.newInstance(a.getClass().getComponentType(), 0);
   }

   public boolean add(T t) {
      if (set == null) { init(); }
      boolean added = set.add(t);
      return added;
   }

   public boolean remove(Object o) {
      return set != null && set.remove(o);
   }

   public boolean containsAll(Collection<?> c) {
      return set != null && set.containsAll(c);
   }

   public boolean addAll(Collection<? extends T> c) {
      if (c.isEmpty()) { return false; }
      if (set == null) { init(); }
      return set.addAll(c);
   }

   public boolean retainAll(Collection<?> c) {
      if (c.isEmpty()) { return false; }
      return set != null && set.retainAll(c);
   }

   public boolean removeAll(Collection<?> c) {
      return set != null && set.removeAll(c);
   }

   public void clear() {
      set = null;
   }

   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Set)) return false;

      Set s = (Set) o;

      if (this.size() != s.size()) { return false; }
      if (!this.containsAll(s)) { return false; }

      return true;
   }

   public int hashCode() {
      return set != null ? set.hashCode() : super.hashCode();
   }

   public String toString() {
      return (set != null) ? set.toString() : "[]";
   }

}
