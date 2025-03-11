package org.bagirov.model;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public class Task<T> {
    private final Callable<? extends T> callable;
    private volatile T result;
    private volatile RuntimeException exception;
    private final AtomicBoolean started = new AtomicBoolean(false);

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() {
        if (result != null) return result; // Если результат уже посчитан, сразу возвращаем
        if (exception != null) throw exception; // Если ранее было исключение, выбрасываем его

        synchronized (this) {
            if (!started.get()) { // Проверяем, не начал ли уже другой поток выполнение
                started.set(true);
                try {
                    result = callable.call();
                } catch (Exception e) {
                    exception = new TaskExecutionException("Execution failed", e);
                    throw exception;
                }
            } else {
                while (result == null && exception == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new TaskExecutionException("Thread interrupted", e);
                    }
                }
            }
        }

        if (exception != null) throw exception;
        return result;
    }

    private static class TaskExecutionException extends RuntimeException {
        public TaskExecutionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
