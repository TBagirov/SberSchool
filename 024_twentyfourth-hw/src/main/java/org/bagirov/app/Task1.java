package org.bagirov.app;

import org.bagirov.model.Task;

public class Task1 {
    public static void run(){
        Task<Integer> task = new Task<>(() -> {
            Thread.sleep(2000);
            return 42;
        });

        Runnable taskRunner = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " result: " + task.get());
            } catch (RuntimeException e) {
                System.err.println(Thread.currentThread().getName() + " error: " + e.getMessage());
            }
        };

        Thread thread1 = new Thread(taskRunner, "Thread-1");
        Thread thread2 = new Thread(taskRunner, "Thread-2");

        thread1.start();
        thread2.start();
    }

}
