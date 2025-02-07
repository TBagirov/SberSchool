package org.bagirov.model;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ScalableThreadPool implements ThreadPool {
    private final int minThreads;
    private final int maxThreads;
    private final Queue<Runnable> taskQueue = new LinkedList<>();
    private final List<Thread> workers = new ArrayList<>();
    private volatile boolean isRunning = true;
    private final AtomicInteger idleWorkers = new AtomicInteger(0);

    public ScalableThreadPool(int minThreads, int maxThreads) {
        if (minThreads > maxThreads || minThreads < 1) {
            throw new IllegalArgumentException("Неверный диапазон потоков");
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
        if (runnable == null) {
            throw new NullPointerException("Задача не может быть null");
        }
        synchronized (taskQueue) {
            taskQueue.offer(runnable);
            taskQueue.notify();
        }
        // Если ни один поток не простаивает, а число рабочих потоков меньше максимального,
        // добавляем новый рабочий поток.
        synchronized (workers) {
            if (idleWorkers.get() == 0 && workers.size() < maxThreads) {
                addWorker();
            }
        }
    }

    private void addWorker() {
        Thread worker = new Thread(() -> {
            while (isRunning) {
                Runnable task = null;
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty() && isRunning) {
                        try {
                            idleWorkers.incrementAndGet();
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            // Если прервали ожидание (например, при shutdown), выходим из цикла
                            Thread.currentThread().interrupt();
                            break;
                        } finally {
                            idleWorkers.decrementAndGet();
                        }
                    }
                    if (!isRunning) {
                        break;
                    }
                    task = taskQueue.poll();
                    System.out.println("Поток " + Thread.currentThread()
                            + " взял задание, размер очереди теперь: " + taskQueue.size());
                }
                if (task != null) {
                    try {
                        task.run();
                    } catch (RuntimeException e) {
                        // Логирование исключения, чтобы исключение из задачи не остановило поток
                        e.printStackTrace();
                    }
                }
                synchronized (workers) {
                    // Если очередь пуста и число потоков превышает минимум, завершаем этот поток
                    if (taskQueue.isEmpty() && workers.size() > minThreads) {
                        workers.remove(Thread.currentThread());
                        break;
                    }
                }
            }
        });
        synchronized (workers) {
            workers.add(worker);
        }
        worker.start();
    }

    /**
     * Останавливает пул немедленно (shutdownNow) и возвращает список задач,
     * которые не были выполнены.
     */
    public List<Runnable> shutdown() {
        isRunning = false;
        List<Runnable> remainingTasks;
        synchronized (taskQueue) {
            remainingTasks = new ArrayList<>(taskQueue);
            taskQueue.clear();
            taskQueue.notifyAll();
        }
        synchronized (workers) {
            for (Thread worker : workers) {
                worker.interrupt();
            }
        }
        return remainingTasks;
    }
}
