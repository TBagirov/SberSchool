package org.bagirov.model;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ScalableThreadPool implements ThreadPool {
    private final int minThreads;
    private final int maxThreads;
    private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private final LinkedList<Thread> workers = new LinkedList<>();
    private boolean isRunning = true;

    public ScalableThreadPool(int minThreads, int maxThreads) {
        if (minThreads > maxThreads || minThreads < 1) {
            throw new IllegalArgumentException("Invalid thread range");
        }
        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
    }

    @Override
    public void start() {
        for (int i = 0; i < minThreads; i++) {
            addWorker();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        taskQueue.offer(runnable);
        synchronized (workers) {
            if (taskQueue.size() > workers.size() && workers.size() < maxThreads) {
                addWorker();
            }
        }
    }

    private void addWorker() {
        Thread worker = new Thread(() -> {
            while (isRunning) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                synchronized (workers) {
                    // Если очередь пуста и количество потоков больше минимального, завершить поток
                    if (taskQueue.isEmpty() && workers.size() > minThreads) {
                        workers.remove(Thread.currentThread());
                        break;
                    }
                }
            }
        });
        workers.add(worker);
        worker.start();
    }

    public void shutdown() {
        isRunning = false;
        for (Thread worker : workers) {
            worker.interrupt();
        }
    }
}