package org.bagirov;

import org.bagirov.app.Task1;
import org.bagirov.app.Task2;

public class Main {
    public static void main(String[] args) {

        try{
        System.out.println("------------ Задача 1 ------------");
        Task1.run();

        Thread.sleep(4000);

        System.out.println("------------ Задача 2 ------------");
        Task2.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}