package com.kuze.bigdata.study.l7threadpool;

import java.util.concurrent.*;

/**
 * FutureTask = Runnable + Future
 */
public class FutureTaskTool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> task = new FutureTask<>(()->{
            return 1 + 1;
        });

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //executorService.submit(task);
        executorService.execute(task);

        Integer result = task.get();

        System.out.println(result);

    }

}
