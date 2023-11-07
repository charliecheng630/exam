package com.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadManager {
    private int rootCount = 0;
    private int subCount = 0;
    private AtomicBoolean isConfirm = new AtomicBoolean(false);

    public List<Thread> threadList = new ArrayList<>();
    public synchronized void setRootCount(){
        this.rootCount++;
    }

    public synchronized void setSubCount(){
        try {
            while(subCount >= 20 && subCount % 20 == 0 && !this.isConfirm.get() && subCount <= 100) {
                System.out.println(subCount + " waiting.....");
                wait();
            }
        } catch(InterruptedException e) {
                e.printStackTrace();
        }
        subCount++;
        this.isConfirm.set(false);
        notify();
    }

    public synchronized int getSubCount(){
        try {
            while (subCount % 20 != 0 && this.isConfirm.get() && subCount < 100) {
                System.out.println(subCount + " Main Wait.....");
                wait();
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.isConfirm.set(true);
        System.out.println(subCount + " Main Confirm.....");
        notify();

        return subCount;
    }

    public synchronized int getTotalSubCount(){
        return subCount;
    }

}
