package threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
public class ThreadPoolRunnable implements Runnable {
    private final BlockingQueue taskQueue;

    private Thread thread;
    private boolean isStopped;

    public ThreadPoolRunnable(BlockingQueue taskQueue) {
        this.taskQueue = taskQueue;
        this.isStopped = false;
    }

    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped()) {
            try {
                Runnable runnable = (Runnable) taskQueue.take();
                runnable.run();
            } catch (Exception e) {
                log.error("CAN'T RUN TASK", e);
            }
        }
    }

    public synchronized void stop() {
        isStopped = true;
        this.thread.interrupt();
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}
