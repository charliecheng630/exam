package com.exam;

import java.util.concurrent.atomic.AtomicBoolean;

public class RootThread implements Runnable {
    private ThreadManager threadManager;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    public RootThread(String name, ThreadManager threadManager) {
        this.threadManager = threadManager;
    }

    @Override
    public void run() {
        isRunning.set(true);
        while (isRunning.get()) {
            System.out.println("RootThread - START "+ Thread.currentThread().getName());
            synchronized (threadManager) {
                for(int i = 0; i < 10; i++) {
                    Thread thread = new Thread(new SubThread( "sub" + String.valueOf(i), threadManager));
                    thread.start();
                    threadManager.setSubCount();
                }
            }

            interrupt();
        }
        System.out.println("RootThread - END "+ Thread.currentThread().getName());
    }

    public void interrupt() {
        isRunning.set(false);
    }
}
