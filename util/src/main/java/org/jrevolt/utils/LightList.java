package org.jrevolt.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Patrik Beno
 */
public class LightList<T> implements List<T> {

   List<T> list;

   protected void init() {
      list = new ArrayList<T>();
   }

   public int size() {
      int size = (list != null) ? list.size() : 0;
      return size;
   }

   public boolean isEmpty() {
      boolean empty = (size() == 0);
      return empty;
   }

   public boolean contains(Object o) {
      boolean contains = (list != null) && list.contains(o);
      return contains;
   }

   public Iterator<T> iterator() {
      Iterator<T> iterator = (list != null) ? list.iterator() : Collections.<T>emptyList().iterator();
      return iterator;
   }

   public Object[] toArray() {
      Object[] array = (list != null) ? list.toArray() : new Object[0];
      return array;
   }

   public <T> T[] toArray(T[] a) {
      T[] array = (list != null) ? list.toArray(a) : (T[]) Array.newInstance(a.getClass().getComponentType(), 0);
      return array;
   }

   public boolean add(T o) {
      if (list == null) { init(); }
      boolean added = list.add(o);
      return added;
   }

   public boolean remove(Object o) {
      boolean removed = (list != null) && list.remove(o);
      return removed;
   }

   public boolean containsAll(Collection<?> c) {
      boolean contains = (list != null) && list.containsAll(c);
      return contains;
   }

   public boolean addAll(Collection<? extends T> c) {
      if (c.isEmpty()) { return false; }
      if (list == null && !c.isEmpty()) { init(); }
      boolean result = list.addAll(c);
      return result;
   }

   public boolean addAll(int index, Collection<? extends T> c) {
      if (c.isEmpty()) { return false; }
      if (list == null && !c.isEmpty()) { init(); }
      boolean changed = list.addAll(index, c);
      return changed;
   }

   public boolean removeAll(Collection<?> c) {
      if (list == null || c.isEmpty()) { return false; }
      boolean changed = list.removeAll(c);
      return changed;
   }

   public boolean retainAll(Collection<?> c) {
      if (list == null || c.isEmpty()) { return false; }
      boolean changed = list.retainAll(c);
      return changed;
   }

   public void clear() {
      if (list == null) { return; }
      list.clear();
   }

   public T get(int index) {
      if (list == null) { throw new IndexOutOfBoundsException(Integer.toString(index)); }
      T object = list.get(index);
      return object;
   }

   public T set(int index, T element) {
      if (list == null) { throw new IndexOutOfBoundsException(Integer.toString(index)); }
      T previous = list.set(index, element);
      return previous;
   }

   public void add(int index, T element) {
      if (index < 0 || index > size()) { throw new IndexOutOfBoundsException(Integer.toString(index)); }
      if (list == null) { init(); }
      list.add(index, element);
   }

   public T remove(int index) {
      if (index < 0 || index >= size()) { throw new IndexOutOfBoundsException(Integer.toString(index)); }
      T removed = list.remove(index);
      return removed;
   }

   public int indexOf(Object o) {
      if (list == null) { return -1; }
      int idx = list.indexOf(o);
      return idx;
   }

   public int lastIndexOf(Object o) {
      if (list == null) { return -1; }
      int idx = list.lastIndexOf(o);
      return idx;
   }

   public ListIterator<T> listIterator() {
      ListIterator<T> iterator = (list != null) ? list.listIterator() : Collections.<T>emptyList().listIterator();
      return iterator;
   }

   public ListIterator<T> listIterator(int index) {
      ListIterator<T> iterator = (list != null) ? list.listIterator(index) : Collections.<T>emptyList().listIterator(index);
      return iterator;
   }

   public List<T> subList(int fromIndex, int toIndex) {
      if (fromIndex > toIndex || fromIndex < 0 || fromIndex >= size() || toIndex > size()) {
         throw new IndexOutOfBoundsException(String.format("Invalid range (%s-%s). Current size: %s", fromIndex, toIndex, size()));
      }
      if (list == null) {
         return Collections.emptyList();
      }
      List<T> sublist = list.subList(fromIndex, toIndex);
      return sublist;
   }


   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof List)) return false;

      List l = (List) o;

      if (this.size() != l.size()) { return false; }
      if (!this.containsAll(l)) { return false; }

      return true;
   }

   public int hashCode() {
      return (list != null ? list.hashCode() : 0);
   }


   public String toString() {
      return (list != null) ? list.toString() : "[]";
   }
}
