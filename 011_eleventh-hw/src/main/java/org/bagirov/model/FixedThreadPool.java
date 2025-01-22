package org.bagirov.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class FixedThreadPool implements ThreadPool {
    private final int threadCount;
    private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private final Thread[] workers;
    private boolean isRunning = true;

    public FixedThreadPool(int threadCount) {
        this.threadCount = threadCount;
        this.workers = new Thread[threadCount];
    }

    @Override
    public void start() {
        for (int i = 0; i < threadCount; i++) {
            workers[i] = new Thread(() -> {
                while (isRunning) {
                    try {
                        Runnable task = taskQueue.take();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            workers[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        taskQueue.offer(runnable);
    }

    public void shutdown() {
        isRunning = false;
        for (Thread worker : workers) {
            worker.interrupt();
        }
    }
}

