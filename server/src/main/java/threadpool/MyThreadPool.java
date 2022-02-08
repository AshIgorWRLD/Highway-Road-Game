package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyThreadPool {
    private final BlockingQueue taskQueue;
    private final List<ThreadPoolRunnable> runnableList = new ArrayList<>();

    private boolean isStopped = false;

    public MyThreadPool(int threadsNumber, int maxThreads) {
        taskQueue = new ArrayBlockingQueue(maxThreads);

        for (int i = 0; i < threadsNumber; i++) {
            runnableList.add(new ThreadPoolRunnable(taskQueue));
        }
        for (ThreadPoolRunnable runnable : runnableList) {
            new Thread(runnable).start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (this.isStopped) throw
                new IllegalStateException("THREAD POOL IS STOPPED");

        this.taskQueue.offer(task);
    }

    public synchronized void stop() {
        this.isStopped = true;
        for (ThreadPoolRunnable runnable : runnableList) {
            runnable.stop();
        }
    }
}