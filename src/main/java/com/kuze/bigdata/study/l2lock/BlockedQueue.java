package com.kuze.bigdata.study.l2lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自实现阻塞队列
 * @param <T>
 */
public class BlockedQueue<T> {

    private final int size = 10;
    private final Lock lock = new ReentrantLock();
    private final Condition noEmpty = lock.newCondition();
    private final Condition noFull = lock.newCondition();
    private final List<T> list = new ArrayList<>(size);

    public void enterQueue(T t) {
        lock.lock();
        try {
            while (list.size() == size) {
                noFull.await();
            }
            list.add(t);
            noEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public T outQueue() {
        T t = null;
        lock.lock();
        try{
            while(list.isEmpty()){
                noEmpty.await();
            }
            t = list.get(0);
            list.remove(0);
            noFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return t;
    }

}
