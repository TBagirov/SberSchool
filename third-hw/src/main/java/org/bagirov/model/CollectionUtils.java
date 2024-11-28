package org.bagirov.model;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionUtils{

    public static<T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static<T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static<T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }

    public static <T> List<T> limit(List<? extends T> source, int size) {
        return source.stream().limit(size).collect(Collectors.toList());
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
        return c1.stream().anyMatch(c2::contains);

    }

    public static<T> List<T> range(List<? extends T> list, T min, T max) {
        List<T> res = new ArrayList<>();

        for(var item : list){
            if(item instanceof Comparable){
                Comparable comparableItem = (Comparable) item;
                if(comparableItem.compareTo(min) >= 0 && comparableItem.compareTo(max) <= 0) {
                    res.add(item);
                }
            }
        }

        return res;
    }

    public static<T> List<T> range(List<? extends T> list, T min, T max, Comparator<? super T> comparator) {

        List<T> res = new ArrayList<>();

        for(var item : list){
            if(comparator.compare(item, min) >= 0 && comparator.compare(item, max) <= 0) {
                res.add(item);
            }
        }

        return res;
    }


}