package org.bagirov;

import org.bagirov.app.Task1;
import org.bagirov.app.Task2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        try{
            do {
                // Вывод меню
                System.out.println("\n\nМеню:");
                System.out.println("1. Выполнить первую задачу");
                System.out.println("2. Выполнить вторую задачу");
                System.out.println("0. Выход");
                System.out.print("Введите ваш выбор: ");

                // Чтение выбора пользователя
                choice = scanner.nextInt();

                // Обработка выбора
                switch (choice) {
                    case 1:
                        Task1.run();
                        break;
                    case 2:
                        Task2.run();
                        break;
                    case 0:
                        System.out.println("Выход из программы.");
                        break;
                    default:
                        System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                }
            } while (choice != 0);
            scanner.close();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}