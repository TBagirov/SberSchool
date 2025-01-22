package org.bagirov;

import org.bagirov.model.FixedThreadPool;
import org.bagirov.model.ScalableThreadPool;
import org.bagirov.model.ThreadPool;

import java.util.concurrent.TimeUnit;

class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Демонстрация FixedThreadPool");
        ThreadPool fixedThreadPool = new FixedThreadPool(3);
        fixedThreadPool.start();
        for (int i = 1; i <= 10; i++) {
            int taskNumber = i;
            fixedThreadPool.execute(() -> {
                System.out.println("FixedThreadPool Задача: " + taskNumber + ", выполняется: " + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(5000); // ждем завершения задач
        ((FixedThreadPool) fixedThreadPool).shutdown();

        System.out.println("\nДемонстрация ScalableThreadPool");
        ThreadPool scalableThreadPool = new ScalableThreadPool(2, 5);
        scalableThreadPool.start();
        for (int i = 1; i <= 10; i++) {
            int taskNumber = i;
            scalableThreadPool.execute(() -> {
                System.out.println("ScalableThreadPool Задача: " + taskNumber + ", выполняется: " + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(5000); // ждем завершения задач
        ((ScalableThreadPool) scalableThreadPool).shutdown();
    }
}
