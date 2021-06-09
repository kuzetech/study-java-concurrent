package com.kuze.bigdata.study.l7threadpool;

import java.util.concurrent.*;

public class ThreadPoolTool {

    public static class MyThreadFactory implements ThreadFactory{
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(false);
            t.setPriority(Thread.NORM_PRIORITY);
            t.setName("test");
            return t;
        }
    }

    public static void main(String[] args) {

        /**
         * 需要注意 Executors 大部分的任务队列都是无界的 LinkedBlockingQueue
         */
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });


        /**
         * 任务在执行过程中遭遇异常，会导致执行任务的线程中止，却不会发出任何通知
         * 所以在任务中，应该尽可能的捕获异常并进行处理
         */
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                5,
                10,
                20,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new MyThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

    }


}
