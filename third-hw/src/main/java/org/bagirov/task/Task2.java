package org.bagirov.task;

import org.bagirov.model.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Task2 {

    public void run(){
        System.out.println("\n\033[35m---------------- Задача 2 ---------------- \033[0m\n");

        // Создаем тестовые списки
        List<Integer> sourceList = List.of(1, 2, 3, 4, 5, 6);
        List<Number> destinationList = new ArrayList<>();
        List<Integer> toRemoveList = List.of(2, 4);
        List<Integer> containsList = List.of(3, 5, 7);
        List<Integer> limitList = List.of(1, 2, 3, 4, 5);
        List<Integer> rangeList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // 1. addAll
        CollectionUtils.addAll(sourceList, destinationList);
        System.out.println("Destination List after addAll: " + destinationList);

        // 2. newArrayList
        List<String> newList = CollectionUtils.newArrayList();
        System.out.println("New Array List: " + newList);

        // 3. indexOf
        int index = CollectionUtils.indexOf(sourceList, 3);
        System.out.println("Index of 3 in sourceList: " + index);

        // 4. limit
        List<Integer> limitedList = CollectionUtils.limit(limitList, 3);
        System.out.println("Limited List (size 3): " + limitedList);

        // 5. add
        CollectionUtils.add(destinationList, 10);
        System.out.println("Destination List after adding 10: " + destinationList);

        // 6. removeAll
        CollectionUtils.removeAll(destinationList, toRemoveList);
        System.out.println("Destination List after removeAll: " + destinationList);

        // 7. containsAll
        boolean containsAll = CollectionUtils.containsAll(sourceList, containsList);
        System.out.println("Source List contains all elements of containsList: " + containsAll);

        // 8. containsAny
        boolean containsAny = CollectionUtils.containsAny(sourceList, containsList);
        System.out.println("Source List contains any elements of containsList: " + containsAny);

        // 9. range (without comparator)
        List<Integer> rangedList = CollectionUtils.range(rangeList, 3, 7);
        System.out.println("Ranged List (3 to 7): " + rangedList);

        // 10. range (with comparator)
        List<Integer> rangedListWithComparator = CollectionUtils.range(rangeList, 3, 7, Comparator.naturalOrder());
        System.out.println("Ranged List with Comparator (3 to 7): " + rangedListWithComparator);

    }

}
