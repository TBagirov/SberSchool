package org.bagirov;

import org.bagirov.model.task1.*;
import org.bagirov.model.task1.execption.InvalidAmountException;
import org.bagirov.task.Task1;
import org.bagirov.task.Task2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Task1 task1 = new Task1();
        Task2 task2 = new Task2();
        Scanner sc = new Scanner(System.in);


        while (true) {
            System.out.println("Выберите задачу для выполнения:");
            System.out.println("1. Задача 1");
            System.out.println("2. Задача 2");
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Запуск задачи 1...");
                    task1.run();
                    break;
                case "2":
                    System.out.println("Запуск задачи 2...");
                    task2.run();
                    break;
                case "0":
                    System.out.println("Выход из программы.");
                    sc.close();
                    return; // Завершаем выполнение программы
                default:
                    System.out.println("Некорректный ввод. Попробуйте снова.");
            }
        }
    }
}