package org.bagirov.task;

import org.bagirov.model.CountMap;
import org.bagirov.model.CountMapImpl;

import java.util.Map;

public class Task1 implements TaskStrategy{

    private CountMap<Integer> cMap;

    public Task1() {
        this(new CountMapImpl());
    }

    public Task1(CountMap countMap){
        cMap = countMap;
    }


    @Override
    public void execute() {

        System.out.println("\n\033[35m---------------- Задача 1 ---------------- \033[0m\n");

        cMap.add(10);
        cMap.add(10);
        cMap.add(5);
        cMap.add(6);
        cMap.add(5);
        cMap.add(10);

        System.out.println("Количество элементов 5: " + cMap.getCount(5));
        System.out.println("Количество элементов 6: " + cMap.getCount(6));
        System.out.println("Количество элементов 10: " + cMap.getCount(10));

        System.out.println("Размер: " + cMap.size());

        System.out.println("Удаление(кол-во удаленных элементов): " + cMap.remove(5));

        System.out.println("-------------------------------");

        System.out.println("Размер Cmap: " + cMap.size());

        Map<Integer, Integer> map = cMap.toMap();

        map.put(1, 1);

        System.out.println("Размер Cmap: " + cMap.size());

        System.out.println("Размер Map: " + map.size());

        cMap.toMap(map);
        // map = cMap.toMap();

        System.out.println("Размер Cmap: " + cMap.size());
        System.out.println("Размер Map: " + map.size());


        CountMap<Integer> cMap2 = new CountMapImpl<>();
        System.out.println(cMap2.size());
        cMap2.addAll(cMap);
        System.out.println(cMap2.size());


        System.out.println("Количество элементов 6: " + cMap.getCount(6));
        cMap.addAll(cMap2);
        System.out.println("Количество элементов 6: " + cMap.getCount(6));
    }

}
