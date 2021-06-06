package com.kuze.bigdata.study.l3Semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreLock {

    private static int count = 0;

    private static Semaphore semaphore = new Semaphore(1);

    public static void add() throws InterruptedException {
        semaphore.acquire();
        try{
           count = count + 1;
        }finally {
            semaphore.release();
        }
    }

}
