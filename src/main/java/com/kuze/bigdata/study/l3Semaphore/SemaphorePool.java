package com.kuze.bigdata.study.l3Semaphore;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class SemaphorePool<T, R> {

    private final Semaphore semaphore;
    private final List<T> pool;

    // t 是 function 的参数
    public SemaphorePool(int poolSize, T t) {
        this.semaphore = new Semaphore(poolSize);
        this.pool = new Vector<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            pool.add(t);
        }
    }

    public R exec(Function<T,R> func) throws InterruptedException {
        T parameter = null;
        R result = null;
        semaphore.acquire();
        try{
            parameter = pool.remove(0);
            result = func.apply(parameter);
        }finally {
            pool.add(parameter);
            semaphore.release();
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        SemaphorePool pool = new SemaphorePool(10, 2);
        pool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }
}
