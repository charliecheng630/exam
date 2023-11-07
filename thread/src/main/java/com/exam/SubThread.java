package com.exam;

import java.util.concurrent.atomic.AtomicBoolean;

public class SubThread implements Runnable {
    private ThreadManager threadManager;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    public SubThread(String name, ThreadManager threadManager) {
        this.threadManager = threadManager;
    }

    @Override
    public void run() {
        System.out.println("SubThread - START "+ Thread.currentThread().getName());
        isRunning.set(true);
        while (isRunning.get()) {
            synchronized (threadManager) {
                if(threadManager.getTotalSubCount() == 100) {
                    interrupt();
                }
            }

        }
        System.out.println("SubThread - END "+ Thread.currentThread().getName());
    }

    public void interrupt() {
        isRunning.set(false);
    }
}
