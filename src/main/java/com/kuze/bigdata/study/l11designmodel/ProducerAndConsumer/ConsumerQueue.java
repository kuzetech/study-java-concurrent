package com.kuze.bigdata.study.l11designmodel.ProducerAndConsumer;

import javafx.concurrent.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsumerQueue {

    //任务队列
    BlockingQueue<Task> bq = new LinkedBlockingQueue<>(2000);

    //启动5个消费者线程
    //执行批量任务
    void start() {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                try {
                    while (true) {
                        Task t = bq.take();
                        t.run();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
