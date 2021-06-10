package com.kuze.bigdata.study.l7threadpool;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * 几个异步线程，先返回先处理
 */
public class CompletionServiceTool {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        // 创建 CompletionService
        CompletionService<Integer> cs = new
                ExecutorCompletionService<>(executor);

        // 异步向电商 S1 询价
        cs.submit(()-> {return 1;});
        // 异步向电商 S2 询价
        cs.submit(()-> {return 2;});
        // 异步向电商 S3 询价
        cs.submit(()-> {return 3;});

        // 看哪个询价结果优先返回
        for (int i=0; i<3; i++) {
            Integer r = cs.take().get();
            // 将询价结果异步保存到数据库
            executor.execute(()-> {System.out.println("保存结果" + r);});
        }
    }

    public Integer getMinPrice() throws InterruptedException, ExecutionException {
        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        // 创建 CompletionService
        CompletionService<Integer> cs = new
                ExecutorCompletionService<>(executor);

        // 异步向电商 S1 询价
        cs.submit(()-> {return 1;});
        // 异步向电商 S2 询价
        cs.submit(()-> {return 2;});
        // 异步向电商 S3 询价
        cs.submit(()-> {return 3;});

        // 看哪个询价结果优先返回
        CountDownLatch latch = new CountDownLatch(3);
        List<Integer> list = new Vector<>(3);
        for (int i=0; i<3; i++) {
            Integer r = cs.take().get();
            // 将询价结果异步保存到数据库
            executor.execute(()-> {
                System.out.println("保存结果" + r);
                list.add(r);
                latch.countDown();
            });
        }

        latch.await();

        int firstCompare = Integer.min(list.get(0), list.get(1));
        int result = Integer.min(firstCompare, list.get(2));
        return result;
    }
}
