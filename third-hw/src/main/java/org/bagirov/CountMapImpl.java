package org.bagirov;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CountMapImpl<E> implements CountMap<E>{
    private Map<E, Integer> map = new HashMap<>();


    @Override
    public void add(E o) {
        map.put(o, map.getOrDefault(o, 0) + 1);
    }

    @Override
    public int getCount(E o) {
        return map.getOrDefault(o, 0);
    }

    @Override
    public int remove(E o) {
        int count = map.getOrDefault(o, 0);
        map.remove(o);

        return count;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void addAll(CountMap<E> source) {
        for (var item : source.toMap().entrySet())
            map.put(item.getKey(), map.getOrDefault(item.getKey(), 0) + item.getValue());
    }

    @Override
    public Map<E, Integer> toMap() {
        return new HashMap<>(map);
    }

    @Override
    public void toMap(Map<E, Integer> destination) {
        destination.clear();
        destination.putAll(map);
    }
}
