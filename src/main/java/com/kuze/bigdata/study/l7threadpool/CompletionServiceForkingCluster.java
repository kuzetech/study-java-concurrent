package com.kuze.bigdata.study.l7threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Dubbo 中有一种叫做Forking 的集群模式，
 * 这种集群模式下，支持并行地调用多个查询服务，只要有一个成功返回结果，整个服务就可以返回了。
 * 例如你需要提供一个地址转坐标的服务，为了保证该服务的高可用和性能，
 * 你可以并行地调用 3 个地图服务商的 API，然后只要有 1 个正确返回了结果 r，那么地址转坐标这个服务就可以直接返回 r 了。
 * 这种集群模式可以容忍 2 个地图服务商服务异常，但缺点是消耗的资源偏多
 */
public class CompletionServiceForkingCluster {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        // 创建 CompletionService
        CompletionService<String> cs =
                new ExecutorCompletionService<>(executor);

        // 用于保存 Future 对象
        List<Future<String>> futures =
                new ArrayList<>(3);

        // 提交异步任务，并保存 future 到 futures
        futures.add(
                cs.submit(()->{return "geocoderByS1";}));
        futures.add(
                cs.submit(()->{return "geocoderByS2";}));
        futures.add(
                cs.submit(()->{return "geocoderByS3";}));

        // 获取最快返回的任务执行结果
        String result;
        try {
            // 只要有一个成功返回，则 break
            for (int i = 0; i < 3; ++i) {
                result = cs.take().get();
                // 简单地通过判空来检查是否成功返回
                if (!result.isEmpty()) {
                    break;
                }
            }
        } finally {
            // 取消所有任务
            for(Future<String> f : futures)
                f.cancel(true);
        }
        // 返回结果
        //return result;
    }

}
