package org.bagirov.tasks;

import org.bagirov.model.PhoneDirectory;

public class Task2 {
    private final PhoneDirectory ph = new PhoneDirectory();

    public Task2() {
        ph.add("Иванов", "+7-123-45");
        ph.add("Морозов", "+7-156-45");
        ph.add("Иванов", "+7-123-62");
    }

    public void run(){
        System.out.println("Задача 2");

        System.out.println(ph.get("Морозов"));
        System.out.println(ph.get("Иванов"));
        System.out.println(ph.get("Пирагов"));

    }

}
