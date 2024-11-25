package org.bagirov;

import java.util.*;

public class CollectionUtils<T>{

    public static<T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static<T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static<T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }

    public static<T> List<? extends T> limit(List<? extends T> source, int size) {
        return source.stream().limit(size).toList();
    }

    public static<T> void add(List<? super T> source, T o) {
        source.add(o);
    }

    public static<T> void removeAll(List<? super T> removeFrom, List<? extends T> c2) {
        removeFrom.removeAll(c2);
    }

    public static<T> boolean containsAll(List<? extends T> c1, List<? extends T> c2) {
        return c1.containsAll(c2);
    }

    public static<T> boolean containsAny(List<? extends T> c1, List<? extends T> c2) {
        return c1.contains(c2);

    }

    public static<T> List<? extends T> range(List<? extends T> list, T min, T max) {

        return new ArrayList<>(list);
    }

    public static<T> List<? extends T> range(List<? extends T> list, T min, T max, Comparator comparator) {
        return null;
    }


}