package org.bagirov.task;

import org.bagirov.model.task1.*;
import org.bagirov.model.task1.execption.InvalidAmountException;

import java.lang.reflect.Field;
import java.util.Scanner;

public class Task1 {


    private Terminal terminal;


    public Task1(){
        this(new TerminalServer(10_000), new PinValidator("1234"), new ConsoleInterface());
    }

    public Task1(TerminalServer terminalServer, PinValidator pinValidator, UserInterface userInterface) {
        this.terminal = new TerminalImpl(terminalServer, pinValidator, userInterface);
    }

    public void run(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в терминал!");

        // Ввод пин-кода
        while (true) {
            System.out.print("Введите цифру пин-кода: ");
            String input = scanner.nextLine();
            if (input.length() == 1) { // Проверяем, что введен один символ
                char digit = input.charAt(0);
                terminal.enterPin(digit);
            } else {
                System.out.println("Ошибка: вводите по одной цифре.");
            }

            // Если пин-код введен успешно, продолжаем работу
            if (((TerminalImpl) terminal).isAuthenticated()) {
                break;
            }
        }

        // Основное меню терминала
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Проверить баланс");
            System.out.println("2. Снять деньги");
            System.out.println("3. Положить деньги");
            System.out.println("4. Выход");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        terminal.checkBalance();
                        break;
                    case "2":
                        System.out.println("Введите сумму для снятия: ");
                        int withdrawAmount = Integer.parseInt(scanner.nextLine());
                        terminal.withdraw(withdrawAmount);
                        break;
                    case "3":
                        System.out.println("Введите сумму для пополнения: ");
                        int depositAmount = Integer.parseInt(scanner.nextLine());
                        terminal.deposit(depositAmount);
                        break;
                    case "4":
                        System.out.println("Спасибо за использование терминала. До свидания!");
                        scanner.reset();

                        // было интересно рефлексию попробовать, не лучшее решение
                        Class<TerminalImpl> term = (Class<TerminalImpl>) terminal.getClass();
                        Field auth = term.getDeclaredField("authenticated");
                        auth.setAccessible(true);
                        auth.set(terminal, false);

                        return;
                    default:
                        System.out.println("Ошибка: неверный выбор. Попробуйте снова.");
                }
            } catch (InvalidAmountException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Неизвестная ошибка: " + e.getMessage());
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
