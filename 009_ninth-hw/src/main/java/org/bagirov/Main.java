package org.bagirov;


import org.bagirov.model.Item;
import org.bagirov.model.Streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {


        List<Item> someCollection = Arrays.asList(
                new Item("Чашка", 25, 23),
                new Item("Ложка", 18, 12),
                new Item("Тарелка", 30, 55)
        );

        Map<String, Item> result = Streams.of(someCollection)
                .filter(p -> p.getAmount() > 20 && p.getAmount() <= 25)
                .transform(p -> new Item(p.getName(), p.getPrice(),p.getAmount() + 30))
                .toMap(Item::getName, Function.identity());

        result.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}