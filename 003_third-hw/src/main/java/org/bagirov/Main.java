package org.bagirov;

import org.bagirov.task.Task1;
import org.bagirov.task.Task2;


public class Main {
    public static void main(String[] args) {
        Task1 task1 = new Task1();
        Task2 task2 = new Task2();

        task1.run();
        task2.run();
    }
}