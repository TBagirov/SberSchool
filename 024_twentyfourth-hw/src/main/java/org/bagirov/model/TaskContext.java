package org.bagirov.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class TaskContext implements Context {
    private final AtomicInteger completedTasks = new AtomicInteger(0);
    private final AtomicInteger failedTasks = new AtomicInteger(0);
    private final AtomicInteger interruptedTasks = new AtomicInteger(0);
    private final AtomicInteger remainingTasks;
    private final AtomicBoolean interrupted = new AtomicBoolean(false);

    public TaskContext(int taskCount) {
        this.remainingTasks = new AtomicInteger(taskCount);
    }

    @Override
    public int getCompletedTaskCount() {
        return completedTasks.get();
    }

    @Override
    public int getFailedTaskCount() {
        return failedTasks.get();
    }

    @Override
    public int getInterruptedTaskCount() {
        return interruptedTasks.get();
    }

    @Override
    public void interrupt() {
        interrupted.set(true);
    }

    public boolean isInterrupted() {
        return interrupted.get();
    }

    public void incrementCompletedTaskCount() {
        completedTasks.incrementAndGet();
    }

    public void incrementFailedTaskCount() {
        failedTasks.incrementAndGet();
    }

    public void incrementInterruptedTaskCount() {
        interruptedTasks.incrementAndGet();
    }

    public void decrementRemainingTasks() {
        remainingTasks.decrementAndGet();
    }

    public void waitForCompletion() {
        while (remainingTasks.get() > 0) {
            Thread.yield();
        }
    }

    @Override
    public boolean isFinished() {
        return remainingTasks.get() == 0;
    }
}