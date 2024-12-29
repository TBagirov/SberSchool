package org.bagirov.tasks;

import org.bagirov.utill.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task1 {
    private static final int DEFAULT_COUNT_WORD = 20;

    private final List<String> words = new ArrayList<>();


    public Task1() {this(DEFAULT_COUNT_WORD);}

    public Task1(int n){
        words.addAll(Utils.generateWords(n));
    }


    public List<String> getUniqueWord(){
        return words
                .stream()
                .distinct()
                .toList();
    }

    public Map<String, Integer> getWordWithFrequency(){
        Map<String, Integer> dictWords = new HashMap<>();

        for(String word : words){
            if(dictWords.containsKey(word)){
                dictWords.put(word, dictWords.get(word) + 1);
            } else{
                dictWords.put(word, 1);
            }
        }

//        words.stream().map(word -> {
//            if(dictWords.containsKey(word)) dictWords.get(word) + 1;
//            else dictWords.put(word, 1);
//
//        });
        return dictWords;
    }


    public void run(){
        System.out.println("Задача 1");
        System.out.println("Начальный массив слов: ");
        System.out.print(Utils.showWords(words));

        System.out.println("\n\nСписок уникальных слов из который состоит массив: ");
        System.out.print(Utils.showWords(getUniqueWord()));

        System.out.println("\n\nСловарь, сколько раз встречается слово в начальном массиве");
        getWordWithFrequency()
                .forEach((key, value) -> System.out.println(
                        "Слово: \"" + key + "\" встречается " + value +
                                ((value % 2 == 0 || value % 3 == 0) ? " раза." : " раз."))
                );
    }
}
