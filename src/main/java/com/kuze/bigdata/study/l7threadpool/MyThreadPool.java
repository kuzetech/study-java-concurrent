package com.kuze.bigdata.study.l7threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class MyThreadPool {

    private final Queue<Runnable> workQueue;
    private final List<Thread> worker = new ArrayList<>();

    public MyThreadPool(Queue<Runnable> workQueue, int poolSize) {
        this.workQueue = workQueue;
        for (int i = 0; i < poolSize; i++) {
            WorkThread thread = new WorkThread();
            thread.start();
            this.worker.add(thread);
        }
    }

    public void exec(Runnable runnable){
        this.workQueue.add(runnable);
    }

    public class WorkThread extends Thread{
        @Override
        public void run() {
            while (true) {
                Runnable work = workQueue.poll();
                work.run();
            }
        }
    }

    public static void main(String[] args) {
        Queue<Runnable> queue = new ArrayBlockingQueue<>(10);
        MyThreadPool threadPool = new MyThreadPool(queue, 10);
        threadPool.exec(new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
            }
        });
    }
}
