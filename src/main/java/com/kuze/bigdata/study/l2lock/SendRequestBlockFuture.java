package com.kuze.bigdata.study.l2lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模仿 Dubbo 发送请求时 TCP 走的异步
 * 内部实现同步机制
 * @param <T>
 */
public class SendRequestBlockFuture<T> {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition block = lock.newCondition();
    private T response;

    public T sendRequest(long timeout) throws TimeoutException, InterruptedException {
        long start = System.nanoTime();
        lock.lock();
        try{
            // 发送请求
            while(response == null){
                block.await(timeout, TimeUnit.SECONDS);
                long current = System.nanoTime();
                if(response != null || (start + timeout) > current){
                    break;
                }
            }
        }finally {
            lock.unlock();
        }
        if(response == null){
            throw new TimeoutException();
        }
        return response;
    }

    public void receiveResponse(T response){
        lock.lock();
        try{
            this.response = response;
            block.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
