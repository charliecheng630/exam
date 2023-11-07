package com.exam;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainThread implements Runnable {
    private ThreadManager threadManager;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    public MainThread(String name, ThreadManager threadManager) {
        this.threadManager = threadManager;
    }

    @Override
    public void run() {
        System.out.println("MainThread - START "+ Thread.currentThread().getName());
        isRunning.set(true);
        while (isRunning.get()) {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (threadManager) {
                    if(threadManager.getSubCount() == 100) {
                        interrupt();
                    }
                }
            }
            interrupt();
        }
        System.out.println("MainThread - END "+ Thread.currentThread().getName());
    }

    public void interrupt() {
        isRunning.set(false);
    }
}
