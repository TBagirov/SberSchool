package org.bagirov.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutionManagerImpl implements ExecutionManager {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        TaskContext context = new TaskContext(tasks.length);
        for (Runnable task : tasks) {
            executor.submit(() -> {
                if (context.isInterrupted()) {
                    context.incrementInterruptedTaskCount();
                    return;
                }
                try {
                    task.run();
                    context.incrementCompletedTaskCount();
                } catch (Exception e) {
                    context.incrementFailedTaskCount();
                }
                context.decrementRemainingTasks();
            });
        }
        executor.submit(() -> {
            context.waitForCompletion();
            callback.run();
        });
        return context;
    }
}
