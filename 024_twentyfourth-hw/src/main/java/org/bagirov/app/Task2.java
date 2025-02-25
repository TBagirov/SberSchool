package org.bagirov.app;

import org.bagirov.model.Context;
import org.bagirov.model.ExecutionManager;
import org.bagirov.model.ExecutionManagerImpl;

public class Task2 {

    public static void run() {
        ExecutionManager executionManager = new ExecutionManagerImpl();

        Runnable task1 = () -> {
            try {
                Thread.sleep(1000);
                System.out.println("Task 1 completed");
            } catch (InterruptedException e) {
                System.out.println("Task 1 interrupted");
            }
        };

        Runnable task2 = () -> {
            try {
                Thread.sleep(1500);
                System.out.println("Task 2 completed");
            } catch (InterruptedException e) {
                System.out.println("Task 2 interrupted");
            }
        };

        Runnable task3 = () -> {
            throw new RuntimeException("Task 3 failed");
        };

        Runnable callback = () -> System.out.println("All tasks completed. Callback executed.");

        Context context = executionManager.execute(callback, task1, task2, task3);

        // Ожидание завершения работы
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вывод результатов
        System.out.println("Completed tasks: " + context.getCompletedTaskCount());
        System.out.println("Failed tasks: " + context.getFailedTaskCount());
        System.out.println("Interrupted tasks: " + context.getInterruptedTaskCount());
        System.out.println("All tasks finished: " + context.isFinished());
    }

}


