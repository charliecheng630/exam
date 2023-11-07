package com.exam;

import java.util.ArrayList;
import java.util.List;

public class MainApplication {

    public static void main(String[] args){
        ThreadManager threadManager = new ThreadManager();
        List<Thread> threadList = new ArrayList<>();
        System.out.println("Starting RootThreads");
        Thread mainthread = new Thread(new MainThread("main", threadManager));
        mainthread.start();


        for(int i = 0; i < 10; i++) {
            Thread thread = new Thread(new RootThread("root" + String.valueOf(i), threadManager));
            threadList.add(thread);
            synchronized (threadManager) {
                threadManager.setRootCount();
            }
            thread.start();
        }

        for(int i = 0; i < threadList.size(); i++) {
            try {
                threadList.get(i).join();
            }catch (Exception e) {
                System.out.println("Interrupted");
            }

        }
        System.out.println("RootThreads has been finished");

    }
}