package org.bagirov.task;

import org.bagirov.model.task2.UrlContentReader;

import java.util.Scanner;


public class Task2 {


    public void run(){
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Введите URL (или 'exit' для выхода): ");
            String url = sc.nextLine();

            if ("exit".equalsIgnoreCase(url)) {
                break;
            }

            UrlContentReader.readContent(url);
        }

        sc.reset();
    }
}
