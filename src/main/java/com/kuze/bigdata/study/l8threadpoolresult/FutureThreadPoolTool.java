package com.kuze.bigdata.study.l8threadpoolresult;

import java.util.concurrent.*;

/**
 * 获取线程池执行结果
 */
public class FutureThreadPoolTool {

    public static class Result{
        String response;
    }

    public static class Task implements Runnable{

        private final Result result;

        public Task(Result result) {
            this.result = result;
        }

        @Override
        public void run() {
            result.response = "test";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
            }
        };

        Future<?> future = executorService.submit(runnable);

        Future<Integer> callableFuture = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                //执行处理逻辑
                return 22;
            }
        });
        Integer o = callableFuture.get();

        Result result1 = new Result();
        Future<Result> resultFuture = executorService.submit(new Task(result1), result1);
        Result result2 = resultFuture.get();
        System.out.println(result1 == result2);


    }
}
