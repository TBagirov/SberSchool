package org.bagirov;

import org.bagirov.model.task5.CachedInvocationHandler;
import org.bagirov.model.task5.Calculator;
import org.bagirov.model.task5.CalculatorImpl;
import org.bagirov.task.*;

import java.lang.reflect.Proxy;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;
        try{
            do {
                System.out.println("Выберите задание для выполнения:");
                System.out.println("1. Task1");
                System.out.println("2. Task2");
                System.out.println("3. Task3");
                System.out.println("4. Task4");
                System.out.println("5. Task5");
                System.out.println("6. Task6");
                System.out.println("7. Task7");
                System.out.println("0. Выход");
                System.out.print("Ваш выбор: ");

                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        Task1 task1 = new Task1();
                        task1.run(5);
                        break;
                    case 2:
                        Task2.run();
                        break;
                    case 3:
                        Task3.run();
                        break;
                    case 4:
                        Task4.run();
                        break;
                    case 5:
                        Calculator delegate = new CalculatorImpl();

                        Calculator calculator = (Calculator) Proxy.newProxyInstance(
                                ClassLoader.getSystemClassLoader(),
                                delegate.getClass().getInterfaces(),
                                new CachedInvocationHandler(delegate)
                        );

                        Task5 task5 = new Task5(calculator);
                        task5.run();
                        break;
                    case 6:
                        Task6.run();
                        break;
                    case 7:
                        Task7.run();
                        break;
                    case 0:
                        System.out.println("Выход из программы.");
                        break;
                    default:
                        System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                }

                System.out.println(); // Печатаем пустую строку для разделения выводов

            } while (choice != 0);
        } catch (IllegalAccessException e){
            System.out.println("ERROR, программа завершает работу");
            System.out.println("Ошибка" + e.getMessage());
        } finally {
            scanner.close();
        }




    }
}