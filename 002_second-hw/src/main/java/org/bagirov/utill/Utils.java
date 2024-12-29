package org.bagirov.utill;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    public static final Random rand = new Random();
    public static List<String> generateWords(int n) {
        String[] words = {"Привет", "Пока", "Слон", "собака", "Дизонавр", "собака", "Привет", "Пока",
                "корова", "динозавр", "бык", "лось", "гора", "стена", "вода", "огонь", "динозавр"};
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < n; i++) {
            list.add(words[rand.nextInt(words.length)]);
        }

        return list;
    }

    public static String showWords(List<String> words){
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < words.size(); i++) {
            sb.append(words.get(i)).append(", ");
            if ((i+1) % 8 == 0) sb.append("\n");
        }

        sb.delete(sb.length()-2, sb.length()).append(".");

        return sb.toString();
    }
}
