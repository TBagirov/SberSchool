package org.bagirov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        String filePath = "numbers.txt"; // Путь к файлу с числами
        List<Integer> numbers = readNumbersFromFile(filePath);

        // Создаем пул потоков с фиксированным количеством потоков
        int numberOfThreads = Runtime.getRuntime().availableProcessors(); // Количество доступных ядер процессора
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
 
        for (Integer number : numbers) {
            executorService.submit(new FactorialThread(number));
        }

        executorService.shutdown();
    }

    // Метод для чтения чисел из файла
    private static List<Integer> readNumbersFromFile(String filePath) {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+"); // Разделяем по пробелам
                for (String part : parts) {
                    try {
                        int number = Integer.parseInt(part);
                        if (number >= 1 && number <= 50) {
                            numbers.add(number);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Некорректное число: " + part);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    // Класс потока для вычисления факториала
    static class FactorialThread extends Thread {
        private final int number;

        public FactorialThread(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            BigInteger factorial = calculateFactorial(number);
            System.out.printf("Факториал числа %d = %s%n", number, factorial);
        }

        private BigInteger calculateFactorial(int n) {
            BigInteger result = BigInteger.ONE;
            for (int i = 2; i <= n; i++) {
                result = result.multiply(BigInteger.valueOf(i));
            }
            return result;
        }
    }
}